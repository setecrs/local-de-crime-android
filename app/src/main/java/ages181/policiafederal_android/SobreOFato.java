package ages181.policiafederal_android;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SobreOFato extends Fragment {
    Calendar calendarHoraAtual;
    EditText editTextHoraProvavel, editTextDataProvavel, editTextOutroTipoDelito, editTextOutroModusOperandis;
    Spinner spinnerTipoDeDelito;
    CheckBox checkBoxExplosivo, checkBoxCorreiosCortaAlarme, checkBoxViolencia, checkBoxNaoHouveDano,
            checkBoxForcarPortaJanela, checkBoxMaoArmada, checkBoxLevarCofre, checkBoxOutroModusOperandi,
            checkBoxOrganizacaoCriminosa, checkBoxPeDeCabra, checkBoxMacarico, checkBoxQuebrouVidro,
            checkBoxBuracoNaParede, checkBoxChaveMixa, checkBoxCorreiosArma, checkBoxChupaCabra,
            checkBoxNomeDadosDivergentes, checkBoxFurtoDescuido, checkBoxMoedaFalsa, checkBoxFurtoPequenoValor,
            checkBoxFurtoCamera, checkBoxCorreiosVeiculoComEnd, checkBoxCorreiosVeiculoZonaNorte,
            checkBoxCorreiosVeiculoZonaSul, checkBoxCorreiosDoisDeMoto, checkBoxCorreiosSuperbonder;

    String dataProvavel, horaProvavel, outroTipoDelito, outroTipoModusOperandi, itemSpinner;
    JSONArray modusOperandi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.sobre_fato, container, false);
        calendarHoraAtual = Calendar.getInstance();
        editTextHoraProvavel = v.findViewById(R.id.editTextHoraProvavel);
        editTextDataProvavel = v.findViewById(R.id.editTextDataProvavel);
        editTextOutroTipoDelito = v.findViewById(R.id.editTextOutroTipoDelito);
        editTextOutroModusOperandis = v.findViewById(R.id.editTextOutroModusOperandi);
        spinnerTipoDeDelito = v.findViewById(R.id.editTextTipoDeDelito);

        checkBoxExplosivo = v.findViewById(R.id.checkBoxExplosivo);
        checkBoxCorreiosCortaAlarme = v.findViewById(R.id.checkBoxCorreiosCortaAlarme);
        checkBoxViolencia = v.findViewById(R.id.checkBoxViolencia);
        checkBoxNaoHouveDano = v.findViewById(R.id.checkBoxNaoHouveDano);
        checkBoxForcarPortaJanela = v.findViewById(R.id.checkBoxForcarPortaJanela);
        checkBoxMaoArmada = v.findViewById(R.id.checkBoxMaoArmada);
        checkBoxLevarCofre = v.findViewById(R.id.checkBoxLevarCofre);
        checkBoxOutroModusOperandi = v.findViewById(R.id.checkBoxOutroModusOperandi);
        checkBoxOrganizacaoCriminosa = v.findViewById(R.id.checkBoxOrganizacaoCriminosa);
        checkBoxPeDeCabra = v.findViewById(R.id.checkBoxPeDeCabra);
        checkBoxMacarico = v.findViewById(R.id.checkBoxMacarico);
        checkBoxQuebrouVidro = v.findViewById(R.id.checkBoxQuebrouVidro);
        checkBoxBuracoNaParede = v.findViewById(R.id.checkBoxBuracoNaParede);
        checkBoxChaveMixa = v.findViewById(R.id.checkBoxChaveMixa);
        checkBoxCorreiosArma = v.findViewById(R.id.checkBoxCorreiosArma);
        checkBoxChupaCabra = v.findViewById(R.id.checkBoxChupaCabra);
        checkBoxNomeDadosDivergentes = v.findViewById(R.id.checkBoxNomeDadosDivergentes);
        checkBoxFurtoDescuido = v.findViewById(R.id.checkBoxFurtoDescuido);
        checkBoxMoedaFalsa = v.findViewById(R.id.checkBoxMoedaFalsa);
        checkBoxFurtoPequenoValor = v.findViewById(R.id.checkBoxFurtoPequenoValor);
        checkBoxFurtoCamera = v.findViewById(R.id.checkBoxFurtoCamera);
        checkBoxCorreiosVeiculoComEnd = v.findViewById(R.id.checkBoxCorreiosVeiculoEnd);
        checkBoxCorreiosVeiculoZonaNorte = v.findViewById(R.id.checkBoxCorreiosVeiculoZN);
        checkBoxCorreiosVeiculoZonaSul = v.findViewById(R.id.checkBoxCorreiosVeiculoZS);
        checkBoxCorreiosDoisDeMoto = v.findViewById(R.id.checkBoxCorreiosDoisDeMoto);
        checkBoxCorreiosSuperbonder = v.findViewById(R.id.checkBoxCorreiosSuperbonder);

        editTextDataProvavel.setFocusable(false);
        editTextHoraProvavel.setFocusable(false);
        editTextOutroModusOperandis.setVisibility(View.INVISIBLE);
        showTimePickerDialog();
        showDatePickerDialog();


        onClickSpinner(spinnerTipoDeDelito);

        onClickCheckboxListener(checkBoxExplosivo );
        onClickCheckboxListener(checkBoxCorreiosCortaAlarme);
        onClickCheckboxListener(checkBoxViolencia);
        onClickCheckboxListener(checkBoxNaoHouveDano);
        onClickCheckboxListener(checkBoxForcarPortaJanela);
        onClickCheckboxListener(checkBoxMaoArmada);
        onClickCheckboxListener(checkBoxLevarCofre);
        onClickCheckboxListener(checkBoxOutroModusOperandi);
        onClickCheckboxListener(checkBoxOrganizacaoCriminosa);
        onClickCheckboxListener(checkBoxPeDeCabra);
        onClickCheckboxListener(checkBoxMacarico);
        onClickCheckboxListener(checkBoxQuebrouVidro);
        onClickCheckboxListener(checkBoxBuracoNaParede);
        onClickCheckboxListener(checkBoxChaveMixa);
        onClickCheckboxListener(checkBoxCorreiosArma);
        onClickCheckboxListener(checkBoxChupaCabra);
        onClickCheckboxListener(checkBoxNomeDadosDivergentes);
        onClickCheckboxListener(checkBoxFurtoDescuido);
        onClickCheckboxListener(checkBoxMoedaFalsa);
        onClickCheckboxListener(checkBoxFurtoPequenoValor);
        onClickCheckboxListener(checkBoxFurtoCamera);
        onClickCheckboxListener(checkBoxCorreiosVeiculoComEnd);
        onClickCheckboxListener(checkBoxCorreiosVeiculoZonaNorte);
        onClickCheckboxListener(checkBoxCorreiosVeiculoZonaSul);
        onClickCheckboxListener(checkBoxCorreiosDoisDeMoto);
        onClickCheckboxListener(checkBoxCorreiosSuperbonder);

        onClickEditText(editTextOutroModusOperandis);
        onClickEditText(editTextOutroTipoDelito);


        carregaSobreOFato();

        Button button = (Button) v.findViewById(R.id.buttonSobreOFato);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Add params
                HttpSobreOFato http_sobre_o_fato = new HttpSobreOFato("a");
                http_sobre_o_fato.execute();
            }
        });


        return v;
    }

    public void onClickSpinner(View v) {
        ((Spinner)v).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
                itemSpinner = parent.getSelectedItem().toString();
                if (parent.getSelectedItem().toString().equals("Outro")) {
                    editTextOutroTipoDelito.setVisibility(View.VISIBLE);
                } else {
                    editTextOutroTipoDelito.setVisibility(View.GONE);
                    editTextOutroTipoDelito.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onClickCheckboxListener(View v) {
        ((CheckBox)v).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.checkBoxOutroModusOperandi && buttonView.isChecked()) {

                    editTextOutroModusOperandis.setVisibility(View.VISIBLE);

                }else if (buttonView.getId() == R.id.checkBoxOutroModusOperandi && !(buttonView.isChecked())) {

                    editTextOutroModusOperandis.setVisibility(View.INVISIBLE);
                    editTextOutroModusOperandis.setText("");
                }

            }
        });
    }

    public void onClickEditText(View v){
        final EditText aux = (EditText)v;
        ((EditText)v).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void afterTextChanged(Editable editable) {
                if(aux.getId() == R.id.checkBoxOutroModusOperandi){
                    outroTipoModusOperandi = editable.toString();
                } else {
                    outroTipoDelito = editable.toString();
                }
            }
        });

    }

    public void showDatePickerDialog(){
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
            editTextDataProvavel.setText(day+"/"+(month+1)+"/"+year);
            dataProvavel = editTextDataProvavel.getText().toString();
        }
    };



    public void showTimePickerDialog(){

        editTextHoraProvavel.setOnClickListener(new View.OnClickListener(){
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
                editTextHoraProvavel.setText(hora+":0"+minuto);
            } else {
                editTextHoraProvavel.setText(hora+":"+minuto);
            }
            horaProvavel = editTextHoraProvavel.getText().toString();
        }
    };

    public void carregaSobreOFato(){

        boolean aux = false;
        JSONObject auxJson;
        String auxCheckBoxtexto;

        editTextHoraProvavel.setText(CarregarOcorrencia.getSfHoraProvavel());
        editTextDataProvavel.setText(CarregarOcorrencia.getSfDataProvavel());

        itemSpinner = CarregarOcorrencia.getSfTipoDelito();

        if(itemSpinner == null){
            spinnerTipoDeDelito.setSelection(0);
        } else {
            for(int i = 1; i < spinnerTipoDeDelito.getAdapter().getCount(); i++){
                if(itemSpinner.equalsIgnoreCase(spinnerTipoDeDelito.getItemAtPosition(i).toString())){
                    spinnerTipoDeDelito.setSelection(i);
                    aux = true;
                    break;
                }
            }
            if(!aux){
                spinnerTipoDeDelito.setSelection(spinnerTipoDeDelito.getAdapter().getCount()-1);
                editTextOutroTipoDelito.setVisibility(View.VISIBLE);
                editTextOutroTipoDelito.setText(itemSpinner);
            }
        }

        modusOperandi = CarregarOcorrencia.getSfModusOperandi();

        try {
            if (modusOperandi != null) {
                for (int i = 0; i < modusOperandi.length(); i++) {
                    auxJson = modusOperandi.getJSONObject(i);
                    aux = (boolean) auxJson.get("ativado");
                    auxCheckBoxtexto = (String) auxJson.get("texto");

                    if (auxCheckBoxtexto.equals(checkBoxExplosivo.getText().toString())) {
                        checkBoxExplosivo.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosCortaAlarme.getText().toString())) {
                        checkBoxCorreiosCortaAlarme.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxViolencia.getText().toString())) {
                        checkBoxViolencia.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxNaoHouveDano.getText().toString())) {
                        checkBoxNaoHouveDano.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxForcarPortaJanela.getText().toString())) {
                        checkBoxForcarPortaJanela.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxMaoArmada.getText().toString())) {
                        checkBoxMaoArmada.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxLevarCofre.getText().toString())) {
                        checkBoxLevarCofre.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxOrganizacaoCriminosa.getText().toString())) {
                        checkBoxOrganizacaoCriminosa.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxPeDeCabra.getText().toString())) {
                        checkBoxPeDeCabra.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxMacarico.getText().toString())) {
                        checkBoxMacarico.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxQuebrouVidro.getText().toString())) {
                        checkBoxQuebrouVidro.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxBuracoNaParede.getText().toString())) {
                        checkBoxBuracoNaParede.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxChaveMixa.getText().toString())) {
                        checkBoxChaveMixa.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosArma.getText().toString())) {
                        checkBoxCorreiosArma.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxChupaCabra.getText().toString())) {
                        checkBoxChupaCabra.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxNomeDadosDivergentes.getText().toString())) {
                        checkBoxNomeDadosDivergentes.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxFurtoDescuido.getText().toString())) {
                        checkBoxFurtoDescuido.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxMoedaFalsa.getText().toString())) {
                        checkBoxMoedaFalsa.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxFurtoPequenoValor.getText().toString())) {
                        checkBoxFurtoPequenoValor.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxFurtoCamera.getText().toString())) {
                        checkBoxFurtoCamera.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosVeiculoComEnd.getText().toString())) {
                        checkBoxCorreiosVeiculoComEnd.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosVeiculoZonaNorte.getText().toString())) {
                        checkBoxCorreiosVeiculoZonaNorte.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosVeiculoZonaSul.getText().toString())) {
                        checkBoxCorreiosVeiculoZonaSul.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosDoisDeMoto.getText().toString())) {
                        checkBoxCorreiosDoisDeMoto.setChecked(aux);
                    } else if (auxCheckBoxtexto.equals(checkBoxCorreiosSuperbonder.getText().toString())) {
                        checkBoxCorreiosSuperbonder.setChecked(aux);
                    } else {
                        if (aux) {
                            checkBoxOutroModusOperandi.setChecked(aux);
                            editTextOutroModusOperandis.setVisibility(View.VISIBLE);
                            editTextOutroModusOperandis.setText(auxCheckBoxtexto);
                        } else {
                            checkBoxOutroModusOperandi.setChecked(aux);
                            editTextOutroModusOperandis.setVisibility(View.INVISIBLE);
                            editTextOutroModusOperandis.setText("");
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static SobreOFato newInstance() {

        SobreOFato f = new SobreOFato();
        return f;
    }

}
