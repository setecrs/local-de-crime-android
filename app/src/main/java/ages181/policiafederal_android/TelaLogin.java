package ages181.policiafederal_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TelaLogin extends AppCompatActivity {

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

    }
}
