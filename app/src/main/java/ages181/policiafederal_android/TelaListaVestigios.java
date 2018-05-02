package ages181.policiafederal_android;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TelaListaVestigios extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.lista_vestigios, container, false);
        configuraBotaoAdiciona(v);
        configuraBotaoManchaSangue(v);
        configuraBotaoCapsula(v);
        return v;
    }

    private void configuraBotaoAdiciona(View v) {
        FloatingActionButton fab = v.findViewById(R.id.floatingActionButtonAdicionarVestigio);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), TelaAdicionarVestigio.class));
                }
            });
        }
    }

    private void configuraBotaoManchaSangue(View v) {
        Button botaoGravar = (Button) v.findViewById(R.id.buttonManchaSangue);
        if(botaoGravar != null) {
            botaoGravar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), TelaVestigioManchaSangue.class));
                }
            });
        }

    }

    private void configuraBotaoCapsula(View v) {
        Button botaoGravar = (Button) v.findViewById(R.id.buttonCapsula);
        if(botaoGravar != null) {

            botaoGravar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), TelaVestigioCapsula.class));
                }
            });
        }

    }

    public static TelaListaVestigios newInstance() {

        TelaListaVestigios f = new TelaListaVestigios();
        return f;
    }
}
