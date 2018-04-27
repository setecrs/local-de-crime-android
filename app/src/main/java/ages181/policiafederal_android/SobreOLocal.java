package ages181.policiafederal_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SobreOLocal extends Fragment {
    private static final String[] condicoesLocal = { "Condições do Local",
            "Preservado", "Pouco preservado", "Não preservado" };
    ArrayAdapter<String> listaCondicoesLocal;
    Spinner spinnerCondicoesLocal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_sobre_o_local, container, false);

        final CalendarView cvDataChegada = (CalendarView) v.findViewById(R.id.calendarioDataChegada);
        cvDataChegada.setVisibility(cvDataChegada.INVISIBLE);

        final EditText tvDataChegada = (EditText) v.findViewById(R.id.campoDataChegada);
        tvDataChegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvDataChegada.setVisibility(cvDataChegada.VISIBLE);
                cvDataChegada.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        tvDataChegada.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        cvDataChegada.setVisibility(cvDataChegada.INVISIBLE);
                        tvDataChegada.setFocusable(false);
                    }
                });

            }
        });

        EditText etHoraChegada = (EditText) v.findViewById(R.id.horaChegadaPerito);
        etHoraChegada.addTextChangedListener(Mask.mascara(etHoraChegada, Mask.FORMAT_HOUR));

        listaCondicoesLocal = new ArrayAdapter<String>(getActivity().getApplicationContext() , android.R.layout.simple_list_item_1, condicoesLocal);
        spinnerCondicoesLocal = (Spinner) v.findViewById(R.id.condicoesLocal);
        spinnerCondicoesLocal.setAdapter(listaCondicoesLocal);


        return v;
    }

    public static SobreOLocal newInstance() {

        SobreOLocal f = new SobreOLocal();
        return f;
    }
}
