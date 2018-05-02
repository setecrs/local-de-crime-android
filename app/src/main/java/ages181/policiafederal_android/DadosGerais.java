package ages181.policiafederal_android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

public class DadosGerais extends Fragment {

    private EditText numOcorrencia;
    private EditText sedeOcorrencia;
    private EditText peritoOcorrencia;
    private Date dataHoraAcionamento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.dados_gerais, container, false);
        numOcorrencia = v.findViewById(R.id.numOcorrenciaId);
        sedeOcorrencia = v.findViewById(R.id.sedeOcorrenciaId);
        peritoOcorrencia = v.findViewById(R.id.peritosEnvolvidosId);
        dataHoraAcionamento = null;

        return v;
    }


    public static DadosGerais newInstance() {

        DadosGerais f = new DadosGerais();
        return f;
    }

    public void sendMessege(View view){
        try{
            new HttpDadosGerais(numOcorrencia.toString(), sedeOcorrencia.toString(), peritoOcorrencia.toString(), dataHoraAcionamento).execute();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
