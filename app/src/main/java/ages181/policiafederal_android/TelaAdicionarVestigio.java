package ages181.policiafederal_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TelaAdicionarVestigio extends AppCompatActivity {
    Switch switchColetado;
    boolean coletado = false;
    EditText etEtiqueta, etInfoAdd;
    Button gravar;
    Spinner spTipo, spNome;
    String itemSelecionado, nomeSelecionado;
    ImageButton botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_vestigio);


        //Buscando Elementos do layout
        spTipo = findViewById(R.id.spinnerTipoVestigios);
        spNome = findViewById(R.id.spinnerNomeVestigios);
        botaoVoltar = findViewById(R.id.imageButtonVoltar);
        etEtiqueta = findViewById(R.id.editTextNumeroEtiqueta);
        etInfoAdd = findViewById(R.id.editTextInformacoes);
        gravar = findViewById(R.id.buttonGravar);
        switchColetado = findViewById(R.id.switchVestigioColetado);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        if (getIntent().getExtras() != null) {
            gravar.setVisibility(View.INVISIBLE);
            TextView tvTitle = findViewById(R.id.toolbar_title);
            tvTitle.setText("Visualizar Vestígio");
            String colExtra = getIntent().getStringExtra("coletado");
            String etiquetaExtra = getIntent().getStringExtra("etiqueta");
            String tipoExtra = getIntent().getStringExtra("tipo");
            String nomeExtra = getIntent().getStringExtra("nome");
            String infoExtra = getIntent().getStringExtra("infoAdicional");
            if (colExtra.equalsIgnoreCase("false")) {
                switchColetado.setChecked(false);
                switchColetado.setEnabled(false);
            } else {
                switchColetado.setChecked(true);
                switchColetado.setEnabled(false);
            }

            etEtiqueta.setText(etiquetaExtra);
            etEtiqueta.setFocusable(false);

            ArrayList<String> ad = new ArrayList<>();
            ad.add(tipoExtra);
            ArrayAdapter<String> spinnerAdapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ad);
            spTipo.setAdapter(spinnerAdapterTipo);

            ArrayList<String> an = new ArrayList<>();
            an.add(nomeExtra);
            ArrayAdapter<String> spinnerAdapterNome = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, an);
            spNome.setAdapter(spinnerAdapterNome);

            etInfoAdd.setText(infoExtra);
            etInfoAdd.setFocusable(false);


        } else {


            //Declarando ArrayLists para popular os Spinners
            final ArrayList<String> listaSpinnerNomes = new ArrayList<>();
            ArrayList<String> listaSpinnerTipo = new ArrayList<>();

            //Adicionando os tipos de vestígios padrão
            listaSpinnerTipo.add("Físico");
            listaSpinnerTipo.add("Químico");
            listaSpinnerTipo.add("Biológico");

            //Setando o ArrayList de tipos no Spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaSpinnerTipo);
            spTipo.setAdapter(spinnerAdapter);

            //Evento para capturar a troca de elemento no spinner de tipo e popular o spinner de nomes
            spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    itemSelecionado = spTipo.getSelectedItem().toString();
                    listaSpinnerNomes.clear();
                    try {
                        JSONArray jsonVestigios = StaticProperties.getJsonListas().getJSONArray("tipoVestigios");
                        int compJson = jsonVestigios.length();
                        for (int j = 0; j < compJson; j++) {
                            if (itemSelecionado.equalsIgnoreCase(jsonVestigios.getJSONObject(j).getString("tipoVestigio"))) {
                                listaSpinnerNomes.add(jsonVestigios.getJSONObject(j).getString("nomeVestigio"));
                            }
                        }
                        ArrayAdapter<String> spinnerAdapterNomes = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, listaSpinnerNomes);
                        spNome.setAdapter(spinnerAdapterNomes);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    itemSelecionado = spTipo.getItemAtPosition(0).toString();
                }
            });

            //Capturando o nome selecionado para realizar a busca do id do tipoVestigio no banco
            spNome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    nomeSelecionado = spNome.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    nomeSelecionado = spNome.getItemAtPosition(0).toString();
                }
            });

            //Evento para verificar o estado do ToogleButton "coletado"
            switchColetado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        coletado = true;
                }
            });
        }
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaAdicionarVestigio.super.onBackPressed();
            }
        });

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void sendMessage(View view) {
        try {
            //Instanciando a classe HTTP que faz a integração da API com o Android
            HttpNovoVestigio t = new HttpNovoVestigio(switchColetado.isChecked(), etEtiqueta.getText().toString(),
                    etInfoAdd.getText().toString().replace("\n", ""), spTipo.getSelectedItem().toString(),
                    spNome.getSelectedItem().toString());
            //Executando o método que faz a integração
            t.execute();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                HttpVestigios t = new HttpVestigios();
                t.execute().get();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*private void configuraBotaoAdiciona() {
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAdicionarVestigio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaAdicionarVestigio.this,TelaAdicionarVestigio.class));
            }
        });
    }*/

}
