package ages181.policiafederal_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TelaListarOcorrencias extends AppCompatActivity {
    RecyclerView rv;
    OcorrenciaAdapter oa;
    static int posicaoClicada;
    FloatingActionButton fabCriarOcorrencia;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar_ocorrencias);
        fabCriarOcorrencia = findViewById(R.id.buttonCriarOcorrencia);
        rv = findViewById(R.id.recyclerView);

        atualizarLista();

        fabCriarOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpNovaOcorrencia hno = new HttpNovaOcorrencia();
                try {
                    hno.execute().get();
                    carregaVestigios();
                    Intent i = new Intent(TelaListarOcorrencias.this, MainActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        oa.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) throws JSONException {
                System.out.println("Posição item clicado: " + position);
                posicaoClicada = position;
                pegarObjeto();
                carregaVestigios();
                Intent i = new Intent(TelaListarOcorrencias.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void carregaVestigios() {
        HttpVestigios t = new HttpVestigios();
        try {
            t.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarLista() {
        sendMessage();
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if (StaticProperties.getListaOcorrencias() != null) {
            oa = new OcorrenciaAdapter(this, StaticProperties.getListaOcorrencias());
            rv.setAdapter(oa);
        } else {
            StaticProperties.setListaOcorrencias(new ArrayList<Ocorrencia>());
            oa = new OcorrenciaAdapter(this, StaticProperties.getListaOcorrencias());
            rv.setAdapter(oa);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    public void sair() {
        StaticProperties.setToken(null);
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        //CAIXA DE CONFIRMACAO
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        sair();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        //ALERTA
        AlertDialog.Builder builder = new AlertDialog.Builder(rv.getContext());
        builder.setMessage("Deseja sair?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

    public void pegarObjeto() throws JSONException {
        for (int i = 0; i < StaticProperties.getJsonArrayOcorrencias().length(); i++) {
            if (StaticProperties.getListaOcorrencias().get(posicaoClicada).getId() == StaticProperties.getJsonArrayOcorrencias().getJSONObject(i).getString("_id")) {
                StaticProperties.setIdOcorrencia(StaticProperties.getJsonArrayOcorrencias().getJSONObject(i).getString("_id"));
                CarregarOcorrencia.carregaOcorrencia(StaticProperties.getJsonArrayOcorrencias().getJSONObject(i));
            }
        }
    }

    public void sendMessage() {
        try {
            HttpOcorrencias t = new HttpOcorrencias();
            t.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
