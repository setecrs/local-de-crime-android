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

    // Vestígios

    static JSONArray vestigios;

    public static void carregaOcorrencia(JSONObject ocorrencia){
        JSONObject auxJson;
        JSONArray auxArrayJson;
        StringBuffer sb = new StringBuffer();

        try {
            dgNumeroOcorrencia = ocorrencia.getString("numeroOcorrencia");

            dgSedeOcorrencia = ocorrencia.getString("sede");

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
                if(!auxJson.getString("tipoLocal").equals("Outro")) {
                    endLocal = auxJson.getString("tipoLocal");
                } else {
                    endLocal = ocorrencia.getString("outroTipoLocal");
                }
            } else {
                endLocal = "";
            }

            endEstado = ocorrencia.getString("estado");
            endCidade = ocorrencia.getString("municipio");
            endRua = ocorrencia.getString("logradouro");
            endComplemento = ocorrencia.getString("complemento");
            endNumero = ocorrencia.getString("numero");
            respNome = ocorrencia.getString("nomeResponsavel");
            respCargo = ocorrencia.getString("cargoResponsavel");
            respDoc = ocorrencia.getString("documentoResponsavel");
            respEntrevista = ocorrencia.getString("entrevistaResponsavel");
            testNome = ocorrencia.getString("nomeTestemunha");
            testFuncao = ocorrencia.getString("funcaoTestemunha");
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
            sbInfo = ocorrencia.getString("informacoesAdicionais");

            if(ocorrencia.isNull("dataOcorrencia")){
                sfDataProvavel= "";
                sfHoraProvavel = "";
            }else{
                String[] dataHora = parseData(ocorrencia.getString("dataOcorrencia"));
                sfDataProvavel = dataHora[0];
                sfHoraProvavel = dataHora[1];
            }
            if (!ocorrencia.isNull("tipoDelito")){
                auxJson = ocorrencia.getJSONObject("tipoDelito");
                if(!auxJson.getString("tipoDelito").equals("Outro")) {
                    sfTipoDelito = auxJson.getString("tipoDelito");
                } else {
                    sfTipoDelito = ocorrencia.getString("outroTipoDelito");
                }
            } else {
                sfTipoDelito = "";
            }

            // VESTIGIOS ARRAY
            vestigios = ocorrencia.getJSONArray("vestigios");

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

    public static JSONArray getVestigios(){
        return vestigios;
    }

    
}





