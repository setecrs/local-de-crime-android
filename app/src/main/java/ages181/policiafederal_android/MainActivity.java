package ages181.policiafederal_android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerLocal, spinnerEstado, spinnerCidade;
    private EditText editTextRua, editTextNumero, editTextComplemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPrefs();

        spinnerLocal = (Spinner) findViewById(R.id.spinnerLocal);
        spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);
        spinnerCidade = (Spinner) findViewById(R.id.spinnerCidade);
        editTextRua = (EditText) findViewById(R.id.editTextRua);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        editTextComplemento = (EditText) findViewById(R.id.editTextComplemento);

        spinnerSelectedListener(spinnerLocal);
        spinnerSelectedListener(spinnerEstado);
        spinnerSelectedListener(spinnerCidade);

    }

    private void saveSpinnerLocal(String chave, boolean valor){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(chave, valor);
        edit.apply();
    }

    private void saveSpinnerLocal(String chave, int posicao){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(chave, posicao);
        editor.apply();
    }

    private void loadPrefs(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean valorSpinner = sp.getBoolean("spinner", false);
        int valorSpinner1 = sp.getInt("spinner", 0);
        if(valorSpinner){
            spinnerLocal.setSelection(valorSpinner1);
        }
    }

    private void spinnerSelectedListener(Spinner spinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(pos);
                if(pos == 0){
                    // manda o que pro banco de dados... null?
                    //Toast.makeText(getApplicationContext(), "Selected(NULL): " + selectedItemText, Toast.LENGTH_SHORT)
                    //       .show();
                } else {
                    // salvar no BD
                    //Toast.makeText(getApplicationContext(), "Selected: " + selectedItemText, Toast.LENGTH_SHORT)
                    //      .show();

                    saveSpinnerLocal("spinnerLocal", true);
                    saveSpinnerLocal("spinnerLocalPos", pos);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
