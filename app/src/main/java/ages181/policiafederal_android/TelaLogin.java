package ages181.policiafederal_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TelaLogin extends AppCompatActivity {
    private EditText senha;
    ImageView imagem;
    private AutoCompleteTextView actvUsuario;
    private String[] listaLogin = {"admin"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        imagem = findViewById(R.id.imageView);
        imagem.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_foreground));

        senha = findViewById(R.id.editText2);
        actvUsuario = findViewById(R.id.autoCompleteTextViewUsuario);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listaLogin);
        actvUsuario.setAdapter(adapter);

        carregaConteudoAutoComplete("autoCompleteTextViewUsuario", actvUsuario);

    }

    private void carregaConteudoAutoComplete(String nomeAutoCompleteTextView, AutoCompleteTextView autoCompleteTextView) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String texto = sp.getString(nomeAutoCompleteTextView, null);
        autoCompleteTextView.setText(texto);
    }

    public void sendMessage(View view) {
        try {

            HttpLogin t = new HttpLogin(actvUsuario.getText(), senha.getText());
            t.execute().get();

            if (StaticProperties.getToken() != null) {
                actvUsuario.setText("");
                senha.setText("");
                Intent k = new Intent(TelaLogin.this, TelaListarOcorrencias.class);
                startActivity(k);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos", Toast.LENGTH_SHORT);
                toast.show();
            }

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Verifique sua conexão", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }


}
