package ages181.policiafederal_android;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class DadosGerais extends Fragment {

    Calendar calendarHoraAtual;
    private EditText numOcorrencia;
    private EditText sedeOcorrencia;
    private EditText peritoOcorrencia;
    private EditText dataOcorrencia;
    private EditText horaOcorrencia;
    private Date dataHoraAcionamento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.dados_gerais, container, false);
        numOcorrencia = v.findViewById(R.id.numOcorrenciaId);
        sedeOcorrencia = v.findViewById(R.id.sedeOcorrenciaId);
        peritoOcorrencia = v.findViewById(R.id.peritosEnvolvidosId);
        dataOcorrencia = v.findViewById(R.id.dataOcorrenciaId);
        horaOcorrencia = v.findViewById(R.id.horaOcorrenciaId);
        calendarHoraAtual = Calendar.getInstance();

        dataOcorrencia.setFocusable(false);
        horaOcorrencia.setFocusable(false);
        showTimePickerDialog();
        showDatePickerDialog();


        showTimePickerDialog();
        showDatePickerDialog();

        Button button = (Button) v.findViewById(R.id.buttonSaveDadosGerais);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Data de acionamento e Peritos acionados
                HttpDadosGerais http_dados_gerais = new HttpDadosGerais(numOcorrencia.getText().toString(), "5b218f029bbe6c0014537081", "a", new Date());
                http_dados_gerais.execute();

            }
        });

        carregaDadosGerais();
        return v;
    }


    public static DadosGerais newInstance() {
        DadosGerais f = new DadosGerais();
        return f;
    }

    public void carregaDadosGerais(){


        numOcorrencia.setText(CarregarOcorrencia.getDgNumeroOcorrencia());
        sedeOcorrencia.setText(CarregarOcorrencia.getDgSedeOcorrencia());
        peritoOcorrencia.setText(CarregarOcorrencia.getDgPeritosOcorrencia());
        dataOcorrencia.setText(CarregarOcorrencia.getDgDataAcionamento());
        horaOcorrencia.setText(CarregarOcorrencia.getDgHoraAcionamento());
    }

    public void showDatePickerDialog(){
        dataOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), captarData, calendarHoraAtual.get(Calendar.YEAR),
                        calendarHoraAtual.get(Calendar.MONTH), calendarHoraAtual.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    protected DatePickerDialog.OnDateSetListener captarData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dataOcorrencia.setText(day+"/"+(month+1)+"/"+year);
        }
    };

    public void showTimePickerDialog(){

        horaOcorrencia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new TimePickerDialog(getActivity(), captarHorario, calendarHoraAtual.get(Calendar.HOUR_OF_DAY),
                        calendarHoraAtual.get(Calendar.MINUTE),true).show();
            }
        });
    }

    //Captar horario selecionado
    protected TimePickerDialog.OnTimeSetListener captarHorario = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            if(minuto < 10){
                horaOcorrencia.setText(hora+":0"+minuto);
            } else {
                horaOcorrencia.setText(hora+":"+minuto);
            }

        }
    };


    public void sendMessege(View view){
        try{
            new HttpDadosGerais(numOcorrencia.toString(), sedeOcorrencia.toString(), peritoOcorrencia.toString(), dataHoraAcionamento).execute();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
