package ages181.policiafederal_android;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TelaLogin extends FragmentActivity {

    private EditText usuario;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario   =  (EditText)findViewById(R.id.editText);
        senha   =  (EditText)findViewById(R.id.editText2);
    }

    public void sendMessage(View view) {
        try {
            new HttpLogin().execute();
            Intent k = new Intent(TelaLogin.this, TelaEndereco.class);
            startActivity(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

