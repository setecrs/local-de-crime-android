package ages181.policiafederal_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class TelaEndereco extends Fragment{

    private Spinner spinnerLocal, spinnerEstado, spinnerCidade;
    private EditText editTextRua, editTextNumero, editTextComplemento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.endereco, container, false);


        spinnerLocal = v.findViewById(R.id.spinnerLocal);
        spinnerEstado = v.findViewById(R.id.spinnerEstado);
        spinnerCidade = v.findViewById(R.id.spinnerCidade);
        editTextRua = v.findViewById(R.id.editTextRua);
        editTextNumero = v.findViewById(R.id.editTextNumero);
        editTextComplemento = v.findViewById(R.id.editTextComplemento);

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

        return v;
    }

    private void salvaPosicaoSpinner(String nomeSpinner, int posicao){
        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(nomeSpinner, posicao);
        editor.apply();
    }

    private void salvaConteudoEditText(String nomeEditText, String texto){
        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(nomeEditText, texto);
        editor.apply();
    }

    private void carregaInfromacaoSpinners(String nomeSpinner, Spinner spinner){
        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        int posicaoSpinner = sp.getInt(nomeSpinner, 0);
        spinner.setSelection(posicaoSpinner);
    }

    private void carregaConteudoEditText(String nomeEditText, EditText editText){
        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
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

    public static TelaEndereco newInstance() {

        TelaEndereco f = new TelaEndereco();
        return f;
    }
}
