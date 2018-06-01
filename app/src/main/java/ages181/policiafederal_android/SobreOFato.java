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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    Map<Integer,ModusOperandiCheckbox> listaCheckboxMO = new HashMap<>();

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

        iniciaListaModusOperandi();

        onClickSpinner(spinnerTipoDeDelito);
        onClickCheckboxListener(checkBoxChupaCabra);

        onClickEditText(editTextOutroModusOperandis);
        onClickEditText(editTextOutroTipoDelito);


        //     android checkbox dynamically



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

        if (v.getId() == R.id.checkBoxOutroModusOperandi && ((CheckBox) v).isChecked()) {

            editTextOutroModusOperandis.setVisibility(View.VISIBLE);

        } else if (v.getId() == R.id.checkBoxOutroModusOperandi && !(((CheckBox) v).isChecked())) {

            editTextOutroModusOperandis.setVisibility(View.INVISIBLE);
            editTextOutroModusOperandis.setText("");
            atualizaListaCheckbox(v.getId(), ((CheckBox) v).isChecked(), "");

        } else {

            atualizaListaCheckbox(v.getId(), ((CheckBox) v).isChecked(), "");
        }

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
                    atualizaListaCheckbox(R.id.checkBoxOutroModusOperandi,true,outroTipoModusOperandi);
                } else {
                    outroTipoDelito = editable.toString();
                }
                //salvaConteudoEditText(nomeEditText, editable.toString());
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
            editTextHoraProvavel.setText(hora+":"+minuto);
            horaProvavel = editTextHoraProvavel.getText().toString();
        }
    };


    private class ModusOperandiCheckbox{
        String nomeCheckBox, outro;
        boolean selecionado;

        public ModusOperandiCheckbox(String nomeCheckBox, String outro, boolean selecionado){

            this.nomeCheckBox = nomeCheckBox;
            this.outro = outro;
            this.selecionado = selecionado;
        }

        public String getNomeCheckBox(){
            return nomeCheckBox;
        }

        public boolean getSelecionado(){
            return selecionado;
        }

        public String getOutro(){
            return outro;
        }

        public void setSelecionado(boolean selecionado){
            this.selecionado = selecionado;
        }

        public void setOutro(String outro){
            this.outro = outro;
        }

    }


    public void iniciaListaModusOperandi(){

        listaCheckboxMO.put(R.id.checkBoxExplosivo, new ModusOperandiCheckbox("Explosivo", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosCortaAlarme, new ModusOperandiCheckbox("Correios - Corta alarme - Leva DVR - Usa luvas", "", false));
        listaCheckboxMO.put(R.id.checkBoxViolencia, new ModusOperandiCheckbox("Com violência", "", false));
        listaCheckboxMO.put(R.id.checkBoxNaoHouveDano, new ModusOperandiCheckbox("Não houve dano ou dano mínimo", "", false));
        listaCheckboxMO.put(R.id.checkBoxForcarPortaJanela, new ModusOperandiCheckbox("Forçar porta ou janela (sem quebrar vidro)", "", false));
        listaCheckboxMO.put(R.id.checkBoxMaoArmada, new ModusOperandiCheckbox("À mão armada", "", false));
        listaCheckboxMO.put(R.id.checkBoxLevarCofre, new ModusOperandiCheckbox("Levar o cofre inteiro", "", false));
        listaCheckboxMO.put(R.id.checkBoxOutroModusOperandi, new ModusOperandiCheckbox("Outro", "", false));
        listaCheckboxMO.put(R.id.checkBoxOrganizacaoCriminosa, new ModusOperandiCheckbox("Provável: organização criminosa", "", false));
        listaCheckboxMO.put(R.id.checkBoxPeDeCabra, new ModusOperandiCheckbox("Pé de cabra", "", false));
        listaCheckboxMO.put(R.id.checkBoxMacarico, new ModusOperandiCheckbox("Maçarico", "", false));
        listaCheckboxMO.put(R.id.checkBoxQuebrouVidro, new ModusOperandiCheckbox("Quebrou vidro", "", false));
        listaCheckboxMO.put(R.id.checkBoxBuracoNaParede, new ModusOperandiCheckbox("Buraco na parede", "", false));
        listaCheckboxMO.put(R.id.checkBoxChaveMixa, new ModusOperandiCheckbox("Chave mixa", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosArma, new ModusOperandiCheckbox("Correios - Arma - Pela porta giratória", "", false));
        listaCheckboxMO.put(R.id.checkBoxChupaCabra, new ModusOperandiCheckbox("Chupa-cabra", "", false));
        listaCheckboxMO.put(R.id.checkBoxNomeDadosDivergentes, new ModusOperandiCheckbox("Nomes e dados divergentes no AFIS-PF", "", false));
        listaCheckboxMO.put(R.id.checkBoxFurtoDescuido, new ModusOperandiCheckbox("Furto por descuido", "", false));
        listaCheckboxMO.put(R.id.checkBoxMoedaFalsa, new ModusOperandiCheckbox("Moeda falsa", "", false));
        listaCheckboxMO.put(R.id.checkBoxFurtoPequenoValor, new ModusOperandiCheckbox("Furto de pequeno valor", "", false));
        listaCheckboxMO.put(R.id.checkBoxFurtoCamera, new ModusOperandiCheckbox("Furto de câmera ou monitor", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosVeiculoEnd, new ModusOperandiCheckbox("Correios - Veículo - Com endereço da abordagem", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosVeiculoZN, new ModusOperandiCheckbox("Correios - Veículo - Zona norte Alvorada - Com endereço da abordagem", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosVeiculoZS, new ModusOperandiCheckbox("Correios - Veículo - Zona Sul - Com endereço da abordagem", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosDoisDeMoto, new ModusOperandiCheckbox("Correios - Dois de moto", "", false));
        listaCheckboxMO.put(R.id.checkBoxCorreiosSuperbonder, new ModusOperandiCheckbox("Correios - Superbonder", "", false));


    }

    public void atualizaListaCheckbox(Integer id, boolean selecionado, String outro) {

        ModusOperandiCheckbox aux = listaCheckboxMO.get(id);

        aux.setSelecionado(selecionado);
        aux.setOutro(outro);
        }

    public static SobreOFato newInstance() {

        SobreOFato f = new SobreOFato();
        return f;
    }
}
