package ages181.policiafederal_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Responsavel extends Fragment {

    private EditText editTextNomeResponsavel, editTextCargoResponsavel, editTextDocResponsavel, editTextEntrevistaResponsavel;
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
                HttpResponsavel http_responsavel = new HttpResponsavel(nome.getText().toString(), cargo.getText().toString(), documento.getText().toString(), entrevista.getText().toString());
                http_responsavel.execute();
            }
        });

        carregaResponsavel();


        return v;
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
