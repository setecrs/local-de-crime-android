package ages181.policiafederal_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class Responsavel extends Fragment {
    private EditText nome, cargo, documento, entrevista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.responsavel, container, false);

        nome = v.findViewById(R.id.nomeResponsavel);
        cargo = v.findViewById(R.id.cargoResponsavel);
        documento = v.findViewById(R.id.documentoResponsavel);
        entrevista = v.findViewById(R.id.entrevistaResponsavel);


        Button button = (Button) v.findViewById(R.id.buttonSaveResponsavel);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                salvaResponsavel();
            }
        });

        carregaResponsavel();


        return v;
    }

    public void salvaResponsavel(){
        if(!verificaAlteracao()) return;

        try{
            HttpResponsavel http_responsavel = new HttpResponsavel(nome.getText().toString(), cargo.getText().toString(), documento.getText().toString(), entrevista.getText().toString());
            http_responsavel.execute().get();

            //VERIFICA RETORNO
            if(http_responsavel.getStatusCode() == 200){
                atualizaOcorrenciaLocal();
                Toast toast = Toast.makeText(this.getContext(), "Dados salvos!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "Erro: "+http_responsavel.getStatusCode(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }catch (Exception e){
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    public void atualizaOcorrenciaLocal(){
        CarregarOcorrencia.respNome = nome.getText().toString();
        CarregarOcorrencia.respCargo = cargo.getText().toString();
        CarregarOcorrencia.respDoc = documento.getText().toString();
        CarregarOcorrencia.respEntrevista = entrevista.getText().toString();
    }

    public boolean verificaAlteracao(){
        if(!nome.getText().toString().equals(CarregarOcorrencia.getRespNome()) ||
                !cargo.getText().toString().equals(CarregarOcorrencia.getRespCargo()) ||
                !documento.getText().toString().equals(CarregarOcorrencia.getRespDoc())||
                !entrevista.getText().toString().equals(CarregarOcorrencia.getRespEntrevista())){
            return true;
        } else
            return false;
    }

    public void carregaResponsavel(){
        cargo.setText(CarregarOcorrencia.getRespCargo());
        nome.setText(CarregarOcorrencia.getRespNome());
        documento.setText(CarregarOcorrencia.getRespDoc());
        entrevista.setText(CarregarOcorrencia.getRespEntrevista());
    }

    public static Responsavel newInstance() {

        Responsavel f = new Responsavel();
        return f;
    }
}
