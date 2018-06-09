package ages181.policiafederal_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TelaTestemunhas extends Fragment {

    private EditText editTextNomeTestemunha, editTextFuncaoTestemunha, editTextDocTestemunha, editTextEntrevistaTestemunha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.testemunhas, container, false);

        editTextNomeTestemunha = v.findViewById(R.id.etNome);
        editTextFuncaoTestemunha = v.findViewById(R.id.etFuncao);
        editTextDocTestemunha = v.findViewById(R.id.etDocumento);
        editTextEntrevistaTestemunha = v.findViewById(R.id.etEntrevista);

        carregaTestemunha();


        return v;
    }


    public void carregaTestemunha(){

        editTextNomeTestemunha.setText(CarregarOcorrencia.getTestNome());
        editTextFuncaoTestemunha.setText(CarregarOcorrencia.getTestFuncao());
        editTextDocTestemunha.setText(CarregarOcorrencia.getTestDoc());
        editTextEntrevistaTestemunha.setText(CarregarOcorrencia.getTestEntrevista());
    }



    public static TelaTestemunhas newInstance() {
        TelaTestemunhas f = new TelaTestemunhas();
        return f;
    }
}
