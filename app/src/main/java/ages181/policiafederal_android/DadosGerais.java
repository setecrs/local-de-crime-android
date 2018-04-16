package ages181.policiafederal_android;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class DadosGerais extends AppCompatActivity {

    private EditText numeroOcorrencia;
    private EditText sedeOcorrencia;
    private EditText peritoOcorrencia;
    private EditText dataHoraAcionamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_gerais);
    }


}
