package ages181.policiafederal_android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

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

        criaFuncionalideSalvarSpinner(spinnerLocal, "spinnerLocal");
        criaFuncionalideSalvarSpinner(spinnerEstado, "spinnerEstado");
        criaFuncionalideSalvarSpinner(spinnerCidade, "spinnerCidade");

        criaFuncionalidadeSalvarTexto(editTextRua, "editTextRua");
        criaFuncionalidadeSalvarTexto(editTextNumero, "editTextNumero");
        criaFuncionalidadeSalvarTexto(editTextComplemento, "editTextComplemento");

        carregaConteudoEditText( "editTextRua", editTextRua);
        carregaConteudoEditText( "editTextNumero", editTextNumero);
        carregaConteudoEditText( "editTextComplemento", editTextComplemento);

        carregaInfromacaoSpinners("spinnerLocal", spinnerLocal);
        carregaInfromacaoSpinners("spinnerEstado", spinnerEstado);
        carregaInfromacaoSpinners("spinnerCidade", spinnerCidade);
    }

    private void salvaPosicaoSpinner(String nomeSpinner, int posicao){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(nomeSpinner, posicao);
        editor.apply();
    }

    private void salvaConteudoEditText(String nomeEditText, String texto){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(nomeEditText, texto);
        editor.apply();
    }

    private void carregaInfromacaoSpinners(String nomeSpinner, Spinner spinner){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int posicaoSpinner = sp.getInt(nomeSpinner, 0);
        spinner.setSelection(posicaoSpinner);
    }

    private void carregaConteudoEditText(String nomeEditText, EditText editText){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String texto = sp.getString(nomeEditText, null);
        editText.setText(texto);
    }

    private void criaFuncionalideSalvarSpinner(Spinner spinner, final String nomeSpinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                if(posicao == 0){
                    // manda o que pro banco de dados... null?
                    salvaPosicaoSpinner(nomeSpinner, posicao);
                }else {
                    // salvar no BD
                    salvaPosicaoSpinner(nomeSpinner, posicao);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }
        });
    }

    private void criaFuncionalidadeSalvarTexto(final EditText editText, final String nomeEditText){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void afterTextChanged(Editable editable) {
                salvaConteudoEditText(nomeEditText, editable.toString());
            }
        });
    }
}
