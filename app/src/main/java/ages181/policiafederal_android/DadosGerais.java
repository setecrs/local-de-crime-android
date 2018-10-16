package ages181.policiafederal_android;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DadosGerais extends Fragment {

    Calendar calendarHoraAtual;
    private EditText numOcorrencia;
    private EditText sedeOcorrencia;
    private EditText peritoOcorrencia;
    private EditText dataOcorrencia;
    private EditText horaOcorrencia;
    private Date dataHoraAcionamento;
    private List<String> listaPeritos;
    private ArrayAdapter<String> adapter;
    private ListView listaViewPeritos;


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

        carregaDadosGerais();

        dataOcorrencia.setFocusable(false);
        horaOcorrencia.setFocusable(false);
        showTimePickerDialog();
        showDatePickerDialog();

        listaViewPeritos = (ListView) v.findViewById(R.id.lista);

        listaViewPeritos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String peritoSelecionado = (String) listaViewPeritos.getItemAtPosition(position);

                //CAIXA DE CONFIRMACAO
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                listaPeritos.remove(position);
                                adapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                //ALERTA
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setMessage("Deseja remover '"+peritoSelecionado+"'?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();
            }
        });

        adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, listaPeritos);

        listaViewPeritos.setAdapter(adapter);

        //Botao salvar dados
        Button button = (Button) v.findViewById(R.id.buttonSaveDadosGerais);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                salvarDadosGerais();
            }
        });

        //Botao add perito
        Button buttonAddPerito = (Button) v.findViewById(R.id.buttonAddPerito);
        buttonAddPerito.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!peritoOcorrencia.getText().toString().trim().equals("")) {
                    listaPeritos.add(peritoOcorrencia.getText().toString());
                    peritoOcorrencia.setText("");
                    adapter.notifyDataSetChanged();
                    listaViewPeritos.setSelection(listaViewPeritos.getCount() - 1);
                }
            }
        });


        return v;
    }


    public static DadosGerais newInstance() {
        DadosGerais f = new DadosGerais();
        return f;
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

    public void carregaDadosGerais(){
        numOcorrencia.setText(CarregarOcorrencia.getDgNumeroOcorrencia());
        sedeOcorrencia.setText(CarregarOcorrencia.getDgSedeOcorrencia());
        dataOcorrencia.setText(CarregarOcorrencia.getDgDataAcionamento());
        horaOcorrencia.setText(CarregarOcorrencia.getDgHoraAcionamento());
        listaPeritos = new LinkedList<String>(CarregarOcorrencia.getListaPeritos());
    }


    public boolean verificaAlteracao(){
        if(!numOcorrencia.getText().toString().equals(CarregarOcorrencia.getDgNumeroOcorrencia()) ||
                !sedeOcorrencia.getText().toString().equals(CarregarOcorrencia.getDgSedeOcorrencia()) ||
                !dataOcorrencia.getText().toString().equals(CarregarOcorrencia.getDgDataAcionamento())||
                !horaOcorrencia.getText().toString().equals(CarregarOcorrencia.getDgHoraAcionamento()) ||
                !listaPeritos.equals(CarregarOcorrencia.getListaPeritos())){
            return true;
        } else
            return false;
    }

    public void atualizaOcorrenciaLocal(){
        CarregarOcorrencia.dgNumeroOcorrencia = numOcorrencia.getText().toString();
        CarregarOcorrencia.dgSedeOcorrencia = sedeOcorrencia.getText().toString();
        CarregarOcorrencia.dgDataAcionamento = dataOcorrencia.getText().toString();
        CarregarOcorrencia.dgHoraAcionamento = horaOcorrencia.getText().toString();
        CarregarOcorrencia.listaPeritos = new LinkedList<String>(listaPeritos);
    }

    public void salvarDadosGerais(){
        if(!verificaAlteracao()) return;

        try{
            if(!dataOcorrencia.getText().toString().equals("") &&
                    !horaOcorrencia.getText().toString().equals("")) {
                dataHoraAcionamento = StaticProperties.formataDataHora(dataOcorrencia.getText().toString(),
                        horaOcorrencia.getText().toString());
            }else{
                dataHoraAcionamento = null;
            }

            //FAZ A REQUISICAO
            HttpDadosGerais htp = new HttpDadosGerais(numOcorrencia.getText().toString(),
                                sedeOcorrencia.getText().toString(),
                    new JSONArray(listaPeritos),
                                dataHoraAcionamento);
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
        } catch (Exception e){
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }
}
