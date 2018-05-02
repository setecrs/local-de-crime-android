package ages181.policiafederal_android;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class SobreOFato extends Fragment {
    /*
    Calendar horarioAtual = Calendar.getInstance();
    EditText horaChegada = findViewById(R.id.etHoraChegada);
    EditText dataChegada = findViewById(R.id.etDataChegada);
    EditText infoAdicionais = findViewById(R.id.infoAd);
    Spinner codicoesLocal = findViewById(R.id.condicoesLocal);
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.sobre_fato, container, false);
        return v;
        /*
        dataChegada.setFocusable(false);
        horaChegada.setFocusable(false);
        showTimePickerDialog();
        showDatePickerDialog(); */
    }
    /*
    public void showDatePickerDialog(){
        dataChegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SobreFato.this, captarData, horarioAtual.get(Calendar.YEAR),
                        horarioAtual.get(Calendar.MONTH), horarioAtual.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }//Fim showDatePickerDialog
    //Captar data selecionada
    protected DatePickerDialog.OnDateSetListener captarData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dataChegada.setText(day+"/"+(month+1)+"/"+year);
        }
    };



    //Exibir TimePicker
    public void showTimePickerDialog(){

        horaChegada.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new TimePickerDialog(SobreFato.this, captarHorario, horarioAtual.get(Calendar.HOUR_OF_DAY),
                        horarioAtual.get(Calendar.MINUTE),true).show();
            }

        });
    } //Fim showTimePickerDialog

    //Captar horario selecionada
    protected TimePickerDialog.OnTimeSetListener captarHorario = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            horaChegada.setText(hora+":"+minuto);
        }
    };

    */

    public static SobreOFato newInstance() {

        SobreOFato f = new SobreOFato();
        return f;
    }
}
