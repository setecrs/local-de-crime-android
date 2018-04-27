package ages181.policiafederal_android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DadosGerais extends Fragment {

    private EditText numeroOcorrencia;
    private EditText sedeOcorrencia;
    private EditText peritoOcorrencia;
    private EditText dataHoraAcionamento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.dados_gerais, container, false);
        return v;
    }


    public static DadosGerais newInstance() {

        DadosGerais f = new DadosGerais();
        return f;
    }

}
