package ages181.policiafederal_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class SobreOLocal extends Fragment {
    private EditText horaChegada, dataChegada, infoAdicional;
    private Spinner spCondicoesLocal;
    private Calendar horarioAtual = Calendar.getInstance();
    private Context context;
    private String itemSpinner;
    private Date dataHoraChegada;


    private static final String[] condicoesLocal = { "Condições do Local",
            "Preservado", "Não preservado" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_sobre_o_local, container, false);

        horaChegada = v.findViewById(R.id.etHoraChegada);
        dataChegada = v.findViewById(R.id.etDataChegada);
        spCondicoesLocal = v.findViewById(R.id.spCondicoesLocal);
        infoAdicional = v.findViewById(R.id.etInfoAdicional);
        dataChegada.setFocusable(false);
        horaChegada.setFocusable(false);
        showTimePickerDialog();
        showDatePickerDialog();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, condicoesLocal);
        spCondicoesLocal.setAdapter(adapter);

        carregaSobreOLocal();

        Button button = (Button) v.findViewById(R.id.buttonSobreOLocal);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                salvaSobreOLocal();
            }
        });

        return v;
    }

    public void salvaSobreOLocal(){
        if(!verificaAlteracao()) return;
        try {
            dataHoraChegada = null;
            if (!dataChegada.getText().toString().equals("") &&
                    !horaChegada.getText().toString().equals("")) {
                dataHoraChegada = StaticProperties.formataDataHora(dataChegada.getText().toString(),
                        horaChegada.getText().toString());
            }
            HttpSobreOLocal htp = new HttpSobreOLocal(infoAdicional.getText().toString(), dataHoraChegada,
                    spCondicoesLocal.getSelectedItem().toString());
            htp.execute().get();

            //VERIFICA RETORNO
            if(htp.getStatusCode() == 200){
                atualizaOcorrenciaLocal();
                Toast toast = Toast.makeText(this.getContext(), "Dados salvos!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "Erro: "+htp.getStatusCode(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }catch (Exception e){
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    public boolean verificaAlteracao(){
        String txtCondLocal;
        if(spCondicoesLocal.getSelectedItemPosition() == 0)
            txtCondLocal = "";
        else
            txtCondLocal = spCondicoesLocal.getSelectedItem().toString();
        if(!dataChegada.getText().toString().equals(CarregarOcorrencia.getSbDatachegada()) ||
                !horaChegada.getText().toString().equals(CarregarOcorrencia.getSbHoraChegada()) ||
                !infoAdicional.getText().toString().equals(CarregarOcorrencia.getSbInfo())||
                !txtCondLocal.equals(CarregarOcorrencia.getSbCondicoesLocal())){
            return true;
        } else
            return false;
    }

    public void atualizaOcorrenciaLocal(){
        CarregarOcorrencia.sbDatachegada= dataChegada.getText().toString();
        CarregarOcorrencia.sbHoraChegada = horaChegada.getText().toString();
        CarregarOcorrencia.sbInfo = infoAdicional.getText().toString();
        CarregarOcorrencia.sbCondicoesLocal = spCondicoesLocal.getSelectedItem().toString();
    }

    public void showTimePickerDialog(){

        horaChegada.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new TimePickerDialog(context, captarHorario, horarioAtual.get(Calendar.HOUR_OF_DAY),
                        horarioAtual.get(Calendar.MINUTE),true).show();
            }

        });
    }

    protected TimePickerDialog.OnTimeSetListener captarHorario = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            if(minuto < 10){
                horaChegada.setText(hora+":0"+minuto);
            } else {
                horaChegada.setText(hora+":"+minuto);
            }
        }
    };

    public void showDatePickerDialog(){
        dataChegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, captarData, horarioAtual.get(Calendar.YEAR),
                        horarioAtual.get(Calendar.MONTH), horarioAtual.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    protected DatePickerDialog.OnDateSetListener captarData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dataChegada.setText(day+"/"+(month+1)+"/"+year);
        }
    };

    public void carregaSobreOLocal(){

        itemSpinner = CarregarOcorrencia.getSbCondicoesLocal();

        if(itemSpinner == null){
            spCondicoesLocal.setSelection(0);
        } else {
            for(int i = 1; i < spCondicoesLocal.getAdapter().getCount(); i++){
                if(itemSpinner.equalsIgnoreCase(spCondicoesLocal.getItemAtPosition(i).toString())) {
                    spCondicoesLocal.setSelection(i);
                    break;
                }
            }
        }
        dataChegada.setText(CarregarOcorrencia.getSbDatachegada());
        horaChegada.setText(CarregarOcorrencia.getSbHoraChegada());
        infoAdicional.setText(CarregarOcorrencia.getSbInfo());
    }

    public static SobreOLocal newInstance() {

        SobreOLocal f = new SobreOLocal();
        return f;
    }



    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
