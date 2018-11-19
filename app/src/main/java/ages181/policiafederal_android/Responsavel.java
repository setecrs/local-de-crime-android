package ages181.policiafederal_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Responsavel extends Fragment {
    private EditText nome, cargo, documento, entrevista;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.responsavel, container, false);

        nome = v.findViewById(R.id.nomeResponsavel);
        cargo = v.findViewById(R.id.cargoResponsavel);
        documento = v.findViewById(R.id.documentoResponsavel);
        entrevista = v.findViewById(R.id.entrevistaResponsavel);


        button = v.findViewById(R.id.buttonSaveResponsavel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaResponsavel();
            }
        });

        carregaResponsavel();


        return v;
    }

    public void salvaResponsavel() {
        if (!verificaAlteracao()) return;
        try {
            HttpResponsavel http_responsavel = new HttpResponsavel(nome.getText().toString(), cargo.getText().toString(), documento.getText().toString(), entrevista.getText().toString().replace("\n", ""));
            http_responsavel.execute().get();

            //VERIFICA RETORNO
            if (http_responsavel.getStatusCode() == 200) {
                atualizaOcorrenciaLocal();
                Toast toast = Toast.makeText(this.getContext(), "Dados salvos!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "Erro: " + http_responsavel.getStatusCode(), Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    public void atualizaOcorrenciaLocal() {
        CarregarOcorrencia.respNome = nome.getText().toString();
        CarregarOcorrencia.respCargo = cargo.getText().toString();
        CarregarOcorrencia.respDoc = documento.getText().toString();
        CarregarOcorrencia.respEntrevista = entrevista.getText().toString();
    }

    public boolean verificaAlteracao() {
        return !nome.getText().toString().equals(CarregarOcorrencia.getRespNome()) ||
                !cargo.getText().toString().equals(CarregarOcorrencia.getRespCargo()) ||
                !documento.getText().toString().equals(CarregarOcorrencia.getRespDoc()) ||
                !entrevista.getText().toString().equals(CarregarOcorrencia.getRespEntrevista());
    }

    public void carregaResponsavel() {
        cargo.setText(CarregarOcorrencia.getRespCargo());
        nome.setText(CarregarOcorrencia.getRespNome());
        documento.setText(CarregarOcorrencia.getRespDoc());
        entrevista.setText(CarregarOcorrencia.getRespEntrevista());

        verificaEncerrada();
    }

    public void verificaEncerrada() {
        if (CarregarOcorrencia.isEncerrada) {
            cargo.setEnabled(false);
            nome.setEnabled(false);
            documento.setEnabled(false);
            entrevista.setEnabled(false);
            button.setEnabled(false);
            button.setVisibility(View.GONE);
        }
    }

    public static Responsavel newInstance() {

        Responsavel f = new Responsavel();
        return f;
    }
}
