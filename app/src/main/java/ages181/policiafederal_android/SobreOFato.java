package ages181.policiafederal_android;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SobreOFato extends Fragment {
    Calendar calendarHoraAtual;
    EditText editTextHoraProvavel, editTextDataProvavel, editTextOutroTipoDelito,
            editTextPossiveisSuspeitos, editTextValoresSub;
    Spinner spinnerTipoDeDelito;

    String dataProvavel, horaProvavel, outroTipoDelito, itemSpinner;

    private List<String> listaDelitos;
    private ArrayAdapter<String> adapter;
    private ListView listaViewDelitos;

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.sobre_fato, container, false);
        calendarHoraAtual = Calendar.getInstance();
        editTextHoraProvavel = v.findViewById(R.id.editTextHoraProvavel);
        editTextDataProvavel = v.findViewById(R.id.editTextDataProvavel);
        editTextOutroTipoDelito = v.findViewById(R.id.editTextOutroTipoDelito);
        spinnerTipoDeDelito = v.findViewById(R.id.editTextTipoDeDelito);
        editTextPossiveisSuspeitos = v.findViewById(R.id.possiveisSuspeitos);
        editTextValoresSub = v.findViewById(R.id.valoresSubtraidos);
        button = v.findViewById(R.id.buttonSobreOFato);
        editTextDataProvavel.setFocusable(false);
        editTextHoraProvavel.setFocusable(false);
        showTimePickerDialog();
        showDatePickerDialog();

        try {
            JSONArray js = StaticProperties.getJsonListas().getJSONArray("tipoDelitos");

            String[] arraySpinner = new String[js.length() + 1];

            arraySpinner[0] = "Selecione os delitos";

            for (int i = 0; i < js.length(); i++) {
                arraySpinner[i + 1] = js.getJSONObject(i).get("tipoDelito").toString();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, arraySpinner);

            spinnerTipoDeDelito.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        onClickSpinner(spinnerTipoDeDelito);


        carregaSobreOFato();

        //Lista de delitos
        listaViewDelitos = v.findViewById(R.id.listaDelitos);

        listaViewDelitos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String delitoSelecionado = (String) listaViewDelitos.getItemAtPosition(position);

                //CAIXA DE CONFIRMACAO
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                listaDelitos.remove(position);
                                adapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                //ALERTA
                if (!CarregarOcorrencia.isEncerrada) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                    builder.setMessage("Deseja remover '" + delitoSelecionado + "'?").setPositiveButton("Sim", dialogClickListener)
                            .setNegativeButton("Não", dialogClickListener).show();
                }
            }
        });

        adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, listaDelitos);

        listaViewDelitos.setAdapter(adapter);

        //Fim lista de delitos


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaSobreOFato();
            }
        });


        return v;
    }

    public void salvaSobreOFato() {
        if (!verificaAlteracao()) return;

        try {
            Long dataOcorrencia = null;

            if (!editTextHoraProvavel.getText().toString().trim().equals("") &&
                    !editTextDataProvavel.getText().toString().trim().equals("")) {
                dataOcorrencia = StaticProperties.formataDataHora(editTextDataProvavel.getText().toString(),
                        editTextHoraProvavel.getText().toString());
            }

            HttpSobreOFato htp = new HttpSobreOFato(editTextOutroTipoDelito.getText().toString(),
                    new JSONArray(listaDelitos), dataOcorrencia, editTextValoresSub.getText().toString(),
                    editTextPossiveisSuspeitos.getText().toString());
            htp.execute().get();

            //VERIFICA RETORNO
            if (htp.getStatusCode() == 200) {
                atualizaOcorrenciaLocal();
                Toast toast = Toast.makeText(this.getContext(), "Dados salvos!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "Erro: " + htp.getStatusCode(), Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this.getContext(), "Erro interno", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    public boolean verificaAlteracao() {
        return !editTextDataProvavel.getText().toString().equals(CarregarOcorrencia.getSfDataProvavel()) ||
                !editTextHoraProvavel.getText().toString().equals(CarregarOcorrencia.getSfHoraProvavel()) ||
                !listaDelitos.equals(CarregarOcorrencia.getListaDelitos()) ||
                !editTextOutroTipoDelito.getText().toString().equals(CarregarOcorrencia.getOutroTipodeDelito()) ||
                !editTextValoresSub.getText().toString().equals(CarregarOcorrencia.getValoresSubtraidos()) ||
                !editTextPossiveisSuspeitos.getText().toString().equals(CarregarOcorrencia.getPossiveisSuspeitos());
    }

    public void atualizaOcorrenciaLocal() {
        CarregarOcorrencia.sfDataProvavel = editTextDataProvavel.getText().toString();
        CarregarOcorrencia.sfHoraProvavel = editTextHoraProvavel.getText().toString();

        String tipoDelito = spinnerTipoDeDelito.getSelectedItem().toString();
        if (spinnerTipoDeDelito.getSelectedItemPosition() == 0) {
            tipoDelito = "";
        }
        CarregarOcorrencia.listaDelitos = new LinkedList<String>(listaDelitos);
        CarregarOcorrencia.outroTipodeDelito = editTextOutroTipoDelito.getText().toString();
        CarregarOcorrencia.valoresSubtraidos = editTextValoresSub.getText().toString();
        CarregarOcorrencia.possiveisSuspeitos = editTextPossiveisSuspeitos.getText().toString();
    }

    public void onClickSpinner(View v) {
        ((Spinner) v).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                itemSpinner = parent.getSelectedItem().toString();
                if (parent.getSelectedItemPosition() != 0) {
                    if (!listaDelitos.contains(parent.getSelectedItem().toString())) {
                        listaDelitos.add(parent.getSelectedItem().toString());
                        parent.setSelection(0);
                        adapter.notifyDataSetChanged();
                        listaViewDelitos.setSelection(listaViewDelitos.getCount() - 1);
                    } else {
                        parent.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void showDatePickerDialog() {
        editTextDataProvavel.setOnClickListener(new View.OnClickListener() {
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
            editTextDataProvavel.setText(day + "/" + (month + 1) + "/" + year);
            dataProvavel = editTextDataProvavel.getText().toString();
        }
    };


    public void showTimePickerDialog() {

        editTextHoraProvavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), captarHorario, calendarHoraAtual.get(Calendar.HOUR_OF_DAY),
                        calendarHoraAtual.get(Calendar.MINUTE), true).show();
            }

        });
    }

    //Captar horario selecionado
    protected TimePickerDialog.OnTimeSetListener captarHorario = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            if (minuto < 10) {
                editTextHoraProvavel.setText(hora + ":0" + minuto);
            } else {
                editTextHoraProvavel.setText(hora + ":" + minuto);
            }
            horaProvavel = editTextHoraProvavel.getText().toString();
        }
    };

    public void carregaSobreOFato() {

        boolean aux = false;
        JSONObject auxJson;
        String auxCheckBoxtexto;

        editTextHoraProvavel.setText(CarregarOcorrencia.getSfHoraProvavel());
        editTextDataProvavel.setText(CarregarOcorrencia.getSfDataProvavel());

        listaDelitos = new LinkedList<String>(CarregarOcorrencia.getListaDelitos());

        editTextOutroTipoDelito.setText(CarregarOcorrencia.getOutroTipodeDelito());

        editTextPossiveisSuspeitos.setText(CarregarOcorrencia.getPossiveisSuspeitos());
        editTextValoresSub.setText(CarregarOcorrencia.getValoresSubtraidos());

        verificaEncerrada();
    }

    public void verificaEncerrada() {
        if (CarregarOcorrencia.isEncerrada) {
            editTextDataProvavel.setEnabled(false);
            editTextHoraProvavel.setEnabled(false);
            spinnerTipoDeDelito.setEnabled(false);
            editTextOutroTipoDelito.setEnabled(false);
            editTextPossiveisSuspeitos.setEnabled(false);
            editTextValoresSub.setEnabled(false);

            button.setEnabled(false);
            button.setVisibility(View.GONE);
        }
    }

    public static SobreOFato newInstance() {

        SobreOFato f = new SobreOFato();
        return f;
    }

}
