package ages181.policiafederal_android;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CarregarOcorrencia {

    // Dados Gerais

    static String dgNumeroOcorrencia, dgSedeOcorrencia,  dgHoraAcionamento, dgDataAcionamento, dgPeritosOcorrencia;
    // LISTA DE MAIS DE UM PERITO, ARRUMAR CLASSE DADOSGERAIS COM AUTOCOMPLETE ===>>


    // Reponsavel

    static String respNome, respCargo, respDoc, respEntrevista;

    // Sobre o Fato

    static String sfHoraProvavel, sfDataProvavel, sfTipoDelito;
    static JSONArray sfModusOperandi;
    // ====>>> sfCheckBox

    // Sobre o Local

    static String sbDatachegada, sbHoraChegada, sbCondicoesLocal, sbInfo;

    // Tela Endereco

    static String endEstado, endCidade, endLocal, endOutroLocal, endRua, endNumero, endComplemento;

    // Tela Testemunha

    static String testNome, testDoc, testFuncao, testEntrevista;

    public static void carregaOcorrencia(JSONObject ocorrencia){
        JSONObject auxJson;
        JSONArray auxArrayJson;
        StringBuffer sb = new StringBuffer();

        try {
            dgNumeroOcorrencia = ocorrencia.getString("numeroOcorrencia");
            if (!ocorrencia.isNull("sede")){
                auxJson = ocorrencia.getJSONObject("sede");
                dgSedeOcorrencia = auxJson.getString("nome");
            } else {
                dgSedeOcorrencia = "";
            }
            auxArrayJson = ocorrencia.getJSONArray("policiaisAcionados");
            if (auxArrayJson.length() == 0) {
                dgPeritosOcorrencia = "";
            } else {
                for (int i = 0; i < auxArrayJson.length(); i++) {
                    auxJson = auxArrayJson.getJSONObject(i);
                    sb.append(auxJson.getString("name"));
                    sb.append(", ");
                }
                dgPeritosOcorrencia = sb.toString();
            }
            if (!ocorrencia.isNull("tipoLocal")){
                auxJson = ocorrencia.getJSONObject("tipoLocal");
                endLocal = auxJson.getString("tipoLocal");
            } else {
                endLocal = "";
            }

            if (!ocorrencia.isNull("estado")){
                auxJson = ocorrencia.getJSONObject("estado");
                endEstado = auxJson.getString("nome");
            } else {
                endEstado = "";
            }
            if (!ocorrencia.isNull("municipio")){
                auxJson = ocorrencia.getJSONObject("municipio");
                endCidade = auxJson.getString("nome");
            } else {
                endCidade = "";
            }
            endRua = ocorrencia.getString("logradouro");
            endComplemento = ocorrencia.getString("complemento");
            endNumero = ocorrencia.getString("numero");
            respNome = ocorrencia.getString("nomeResponsavel");
            respCargo = ocorrencia.getString("cargoResponsavel");
            respDoc = ocorrencia.getString("documentoResponsavel");
            respEntrevista = ocorrencia.getString("entrevistaResponsavel");
            testNome = ocorrencia.getString("nomeTestemunha");
            testFuncao = ocorrencia.getString("cargoTestemunha");
            testDoc = ocorrencia.getString("documentoTestemunha");
            testEntrevista = ocorrencia.getString("entrevistaTestemunha");

            if(ocorrencia.isNull("dataHoraChegada")) {
                    sbDatachegada = "";
                    sbHoraChegada = "";
            }else{
                String[] dataHora = parseData(ocorrencia.getString("dataHoraChegada"));
                sbDatachegada = dataHora[0];
                sbHoraChegada = dataHora[1];
            }
            sbCondicoesLocal = ocorrencia.getString("condicaoLocal");
            sbInfo = ocorrencia.getString("InformacoesAdicionais");

            if(ocorrencia.isNull("dataOcorrencia")){
                sfDataProvavel= "";
                sfHoraProvavel = "";
            }else{
                String[] dataHora = parseData(ocorrencia.getString("dataOcorrencia"));
                sfDataProvavel = dataHora[0];
                sfHoraProvavel = dataHora[1];
            }

            sfTipoDelito = ocorrencia.getString("tipoDelito");

            // VESTIGIOS ARRAY

            if(ocorrencia.isNull("dataHoraAcionamento")) {
                dgDataAcionamento = "";
                dgHoraAcionamento = "";
            }else{
                String[] dataHora = parseData(ocorrencia.getString("dataHoraAcionamento"));
                dgDataAcionamento = dataHora[0];
                dgHoraAcionamento = dataHora[1];
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] parseData(String dataOriginal){
        try {
            Date objetoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataOriginal);
            String data = new SimpleDateFormat("dd/MM/yyyy").format(objetoDate);
            String hora = new SimpleDateFormat("HH:mm").format(objetoDate);
            return new String[]{data, hora};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDgNumeroOcorrencia() {
        return dgNumeroOcorrencia;
    }

    public static String getDgSedeOcorrencia() {
        return dgSedeOcorrencia;
    }

    public static String getDgPeritosOcorrencia() {
        return dgPeritosOcorrencia;
    }

    public static String getDgHoraAcionamento() {
        return dgHoraAcionamento;
    }

    public static String getDgDataAcionamento() {
        return dgDataAcionamento;
    }

    public static String getRespNome() {
        return respNome;
    }

    public static String getRespCargo() {
        return respCargo;
    }

    public static String getRespDoc() {
        return respDoc;
    }

    public static String getRespEntrevista() {
        return respEntrevista;
    }

    public static String getSfHoraProvavel() {
        return sfHoraProvavel;
    }

    public static String getSfDataProvavel() {
        return sfDataProvavel;
    }

    public static String getSfTipoDelito() {
        return sfTipoDelito;
    }

    public static JSONArray getSfModusOperandi() {
        return sfModusOperandi;
    }

    public static String getSbDatachegada() {
        return sbDatachegada;
    }

    public static String getSbHoraChegada() {
        return sbHoraChegada;
    }

    public static String getSbCondicoesLocal() {
        return sbCondicoesLocal;
    }

    public static String getSbInfo() {
        return sbInfo;
    }

    public static String getEndEstado() {
        return endEstado;
    }

    public static String getEndCidade() {
        return endCidade;
    }

    public static String getEndLocal() {
        return endLocal;
    }

    public static String getEndOutroLocal() {
        return endOutroLocal;
    }

    public static String getEndRua() {
        return endRua;
    }

    public static String getEndNumero() {
        return endNumero;
    }

    public static String getEndComplemento() {
        return endComplemento;
    }

    public static String getTestNome() {
        return testNome;
    }

    public static String getTestDoc() {
        return testDoc;
    }

    public static String getTestFuncao() {
        return testFuncao;
    }

    public static String getTestEntrevista() {
        return testEntrevista;
    }

    // ------- PARA TESTE -------


    public static void testeData(){

        Date dateAux = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataOriginal = "2018-06-06T21:24:55.100Z";

        try {
            Date objetoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataOriginal);
            String data = new SimpleDateFormat("dd/MM/yyyy").format(objetoDate);
            String hora = new SimpleDateFormat("HH:mm").format(objetoDate);

            sfHoraProvavel = hora;
            sfDataProvavel = data;
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        try {
//            dateAux = sdf.parse("2018-06-06T21:24:55.100Z");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        calendar.setTime(dateAux);
//
//        sfHoraProvavel = String.valueOf(calendar.HOUR) + ":" + String.valueOf(calendar.MINUTE);
//        sfDataProvavel = String.valueOf(calendar.DAY_OF_MONTH + "/" + calendar.MONTH + "/" + calendar.YEAR);
    }

    public static void testeArray(JSONArray array){
        sfModusOperandi = array;
        sfTipoDelito = "Moeda falsa";
        //sfHoraProvavel = "12:30";
        //sfDataProvavel = "10/4/2001";
    }


}





