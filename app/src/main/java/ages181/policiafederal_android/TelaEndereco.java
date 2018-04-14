package ages181.policiafederal_android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TelaEndereco extends AppCompatActivity {

    private Spinner spinnerLocal, spinnerEstado, spinnerCidade;
    private EditText editTextRua, editTextNumero, editTextComplemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endereco);
        getSupportActionBar().hide();


        spinnerLocal = findViewById(R.id.spinnerLocal);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        spinnerCidade = findViewById(R.id.spinnerCidade);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextComplemento = findViewById(R.id.editTextComplemento);

        spinnerSelectedListener(spinnerLocal, "spinnerLocal");
        spinnerSelectedListener(spinnerEstado, "spinnerEstado");
        spinnerSelectedListener(spinnerCidade, "spinnerCidade");

        carregaInfromacoesSpinners("spinnerLocal", spinnerLocal);
        carregaInfromacoesSpinners("spinnerEstado", spinnerEstado);
        carregaInfromacoesSpinners("spinnerCidade", spinnerCidade);
    }

    private void salvaPosicaoSpinner(String spinner, int posicao){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(spinner, posicao);
        editor.commit();
    }

    private void carregaInfromacoesSpinners(String nomeSpinner, Spinner spinner){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int posicaoSpinner = sp.getInt(nomeSpinner, 0);
        spinner.setSelection(posicaoSpinner);
    }

    private void spinnerSelectedListener(Spinner spinner, final String nomeSpinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(posicao);
                if(posicao == 0){
                    // manda o que pro banco de dados... null?
                    Toast.makeText(getApplicationContext(), "Selected(NULL): " + selectedItemText, Toast.LENGTH_SHORT)
                          .show();
                    salvaPosicaoSpinner(nomeSpinner, posicao);
                }else {
                    // salvar no BD
                    //Toast.makeText(getApplicationContext(), "Selected: " + selectedItemText, Toast.LENGTH_SHORT)
                    //      .show();
                    salvaPosicaoSpinner(nomeSpinner, posicao);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
