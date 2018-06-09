package ages181.policiafederal_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Responsavel extends Fragment {

    private EditText editTextNomeResponsavel, editTextCargoResponsavel, editTextDocResponsavel, editTextEntrevistaResponsavel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.responsavel, container, false);

        editTextCargoResponsavel = v.findViewById(R.id.editTextCargoResponsavel);
        editTextNomeResponsavel = v.findViewById(R.id.editTextNomeResponsavel);
        editTextDocResponsavel = v.findViewById(R.id.editTextDocResponsavel);
        editTextEntrevistaResponsavel = v.findViewById(R.id.editTextEntrevistaResponsavel);

        carregaResponsavel();

        return v;
    }

    public void carregaResponsavel(){
        System.out.println("Cargoteste " + CarregarOcorrencia.respCargo);
        editTextCargoResponsavel.setText(CarregarOcorrencia.getRespCargo());
        System.out.println("Nometeste " + CarregarOcorrencia.respNome);
        editTextNomeResponsavel.setText(CarregarOcorrencia.getRespNome());
        editTextDocResponsavel.setText(CarregarOcorrencia.getRespDoc());
        editTextEntrevistaResponsavel.setText(CarregarOcorrencia.getRespEntrevista());
    }

    public static Responsavel newInstance() {

        Responsavel f = new Responsavel();
        return f;
    }
}
