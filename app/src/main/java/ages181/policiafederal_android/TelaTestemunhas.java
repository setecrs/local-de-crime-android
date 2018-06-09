package ages181.policiafederal_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TelaTestemunhas extends Fragment {

    private EditText nome, documento, funcao,  entrevista;
    private EditText cargo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.testemunhas, container, false);


        nome = v.findViewById(R.id.etNome);
        documento = v.findViewById(R.id.etDocumento);
        funcao = v.findViewById(R.id.etFuncao);
        entrevista = v.findViewById(R.id.etEntrevista);
        //cargo = v.findViewById(R.id.etCargo);
        // TODO: Criar campo cargo

        Button button = (Button) v.findViewById(R.id.buttonSaveTestemunhas);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HttpTestemunha http_testemunha = new HttpTestemunha(nome.getText().toString(), documento.getText().toString(), funcao.getText().toString(), entrevista.getText().toString());
                http_testemunha.execute();
            }
        });

        carregaTestemunha();

        return v;
    }


    public void carregaTestemunha(){

        nome.setText(CarregarOcorrencia.getTestNome());
        funcao.setText(CarregarOcorrencia.getTestFuncao());
        documento.setText(CarregarOcorrencia.getTestDoc());
        entrevista.setText(CarregarOcorrencia.getTestEntrevista());
    }



    public static TelaTestemunhas newInstance() {
        TelaTestemunhas f = new TelaTestemunhas();
        return f;
    }
}
