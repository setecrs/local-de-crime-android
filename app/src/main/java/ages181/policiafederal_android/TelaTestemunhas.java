package ages181.policiafederal_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaTestemunhas extends Fragment {

    private EditText nome, documento, funcao, entrevista;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.testemunhas, container, false);


        nome = v.findViewById(R.id.etNome);
        documento = v.findViewById(R.id.etDocumento);
        funcao = v.findViewById(R.id.etFuncao);
        entrevista = v.findViewById(R.id.etEntrevista);

        button = v.findViewById(R.id.buttonSaveTestemunhas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaTestemunha();
            }
        });

        carregaTestemunha();

        return v;
    }

    public void salvaTestemunha() {
        if (!verificaAlteracao()) return;

        try {
            HttpTestemunha htp = new HttpTestemunha(nome.getText().toString(), documento.getText().toString(), funcao.getText().toString(), entrevista.getText().toString().replace("\n", ""));
            htp.execute().get();

            //VERIFICA RETORNO
            if (htp.getStatusCode() == 200) {
                atualizaOcorrenciaLocal();
                Toast toast = Toast.makeText(this.getContext(), "Dados salvos!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "Erro: " + htp.getStatusCode(), Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }

    public void atualizaOcorrenciaLocal() {
        CarregarOcorrencia.testNome = nome.getText().toString();
        CarregarOcorrencia.testDoc = documento.getText().toString();
        CarregarOcorrencia.testEntrevista = entrevista.getText().toString();
        CarregarOcorrencia.testFuncao = funcao.getText().toString();
    }

    public boolean verificaAlteracao() {
        return !nome.getText().toString().equals(CarregarOcorrencia.getTestNome()) ||
                !documento.getText().toString().equals(CarregarOcorrencia.getTestDoc()) ||
                !funcao.getText().toString().equals(CarregarOcorrencia.getTestFuncao()) ||
                !entrevista.getText().toString().equals(CarregarOcorrencia.getTestEntrevista());
    }


    public void carregaTestemunha() {

        nome.setText(CarregarOcorrencia.getTestNome());
        funcao.setText(CarregarOcorrencia.getTestFuncao());
        documento.setText(CarregarOcorrencia.getTestDoc());
        entrevista.setText(CarregarOcorrencia.getTestEntrevista());
        verificaEncerrada();
    }

    public void verificaEncerrada() {
        if (CarregarOcorrencia.isEncerrada) {
            nome.setEnabled(false);
            funcao.setEnabled(false);
            documento.setEnabled(false);
            entrevista.setEnabled(false);

            button.setEnabled(false);
            button.setVisibility(View.GONE);
        }
    }


    public static TelaTestemunhas newInstance() {
        TelaTestemunhas f = new TelaTestemunhas();
        return f;
    }
}
