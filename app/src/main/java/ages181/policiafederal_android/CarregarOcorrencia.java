package ages181.policiafederal_android;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class CarregarOcorrencia {

    // Dados Gerais

    static String dgNumeroOcorrencia, dgSedeOcorrencia, dgHoraAcionamento, dgDataAcionamento, dgPeritosOcorrencia;
    // LISTA DE MAIS DE UM PERITO, ARRUMAR CLASSE DADOSGERAIS COM AUTOCOMPLETE ===>>


    // Reponsavel

    static String respNome, respCargo, respDoc, respEntrevista;

    // Sobre o Fato

    static String sfHoraProvavel, sfDataProvavel, sfTipoDelito, outroTipodeDelito, possiveisSuspeitos,
            valoresSubtraidos;
    static JSONArray sfModusOperandi;
    // ====>>> sfCheckBox

    // Sobre o Local

    static String sbDatachegada, sbHoraChegada, sbCondicoesLocal, sbInfo;

    // Tela Endereco

    static String endEstado, endCidade, endLocal, endOutroLocal, endRua, endNumero, endComplemento;

    // Tela Testemunha

    static String testNome, testDoc, testFuncao, testEntrevista;

    static List<String> listaPeritos, listaDelitos;

    // Vestígios

    static JSONArray vestigios;

    static boolean isEncerrada;

    public static void zeraTela(JSONObject ob) {
        try {
            if (ob.getString("dataHoraAcionamento") == null) {
                dgDataAcionamento = "";
                dgHoraAcionamento = "";
            } else {
                String[] dataHora = parseData(ob.getString("dataHoraAcionamento"));
                dgDataAcionamento = dataHora[0];
                dgHoraAcionamento = dataHora[1];
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listaPeritos = new LinkedList<String>();
        listaDelitos = new LinkedList<String>();
        dgNumeroOcorrencia = "";
        dgSedeOcorrencia = "";
        dgPeritosOcorrencia = "";
        endLocal = "";
        endEstado = "";
        endCidade = "";
        endRua = "";
        endComplemento = "";
        endNumero = "";
        endOutroLocal = "";
        outroTipodeDelito = "";
        respNome = "";
        respCargo = "";
        respDoc = "";
        respEntrevista = "";
        testNome = "";
        testFuncao = "";
        testDoc = "";
        testEntrevista = "";
        sbDatachegada = "";
        sbHoraChegada = "";
        sbCondicoesLocal = "";
        sbInfo = "";
        sfDataProvavel = "";
        sfHoraProvavel = "";
        sfTipoDelito = "";
        outroTipodeDelito = "";
        possiveisSuspeitos = "";
        vestigios = new JSONArray();
        isEncerrada = false;
    }

    public static String getOutroTipodeDelito() {
        return outroTipodeDelito;
    }

    public static void carregaOcorrencia(JSONObject ocorrencia) {
        JSONObject auxJson;
        JSONArray auxArrayJson;

        try {
            dgNumeroOcorrencia = ocorrencia.getString("numeroOcorrencia");

            dgSedeOcorrencia = ocorrencia.getString("sede");
            dgPeritosOcorrencia = "";
            auxArrayJson = ocorrencia.getJSONArray("policiaisAcionados");
            listaPeritos = new LinkedList<String>();
            if (auxArrayJson.length() != 0) {
                for (int i = 0; i < auxArrayJson.length(); i++) {
                    listaPeritos.add(auxArrayJson.get(i).toString().replace("\\\"", "\""));
                }
            }

            if (!ocorrencia.isNull("tipoLocal")) {
                auxJson = ocorrencia.getJSONObject("tipoLocal");
                if (!auxJson.getString("tipoLocal").equals("Outro")) {
                    endLocal = auxJson.getString("tipoLocal");
                    endOutroLocal = "";
                } else {
                    endLocal = auxJson.getString("tipoLocal");
                    endOutroLocal = ocorrencia.getString("outroTipoLocal");
                }
            } else {
                endLocal = "";
                endOutroLocal = "";
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

            valoresSubtraidos = ocorrencia.getString("valoresSubtraidos");
            possiveisSuspeitos = ocorrencia.getString("possiveisSuspeitos");

            testFuncao = ocorrencia.getString("funcaoTestemunha");
            testDoc = ocorrencia.getString("documentoTestemunha");
            testEntrevista = ocorrencia.getString("entrevistaTestemunha");
            if (ocorrencia.isNull("dataHoraChegada")) {
                sbDatachegada = "";
                sbHoraChegada = "";
            } else {
                String[] dataHora = parseData(ocorrencia.getString("dataHoraChegada"));
                sbDatachegada = dataHora[0];
                sbHoraChegada = dataHora[1];
            }
            sbCondicoesLocal = ocorrencia.getString("condicaoLocal");
            sbInfo = ocorrencia.getString("informacoesAdicionais");

            if (ocorrencia.isNull("dataOcorrencia")) {
                sfDataProvavel = "";
                sfHoraProvavel = "";
            } else {
                String[] dataHora = parseData(ocorrencia.getString("dataOcorrencia"));
                sfDataProvavel = dataHora[0];
                sfHoraProvavel = dataHora[1];
            }

            auxArrayJson = ocorrencia.getJSONArray("tipoDelito");
            listaDelitos = new LinkedList<String>();
            if (auxArrayJson.length() != 0) {
                for (int i = 0; i < auxArrayJson.length(); i++) {
                    listaDelitos.add(auxArrayJson.getJSONObject(i).get("tipoDelito").toString());
                }
            }

            outroTipodeDelito = ocorrencia.getString("outroTipoDelito");

            // VESTIGIOS ARRAY
            vestigios = ocorrencia.getJSONArray("vestigios");

            if (ocorrencia.isNull("dataHoraAcionamento")) {
                dgDataAcionamento = "";
                dgHoraAcionamento = "";
            } else {
                String[] dataHora = parseData(ocorrencia.getString("dataHoraAcionamento"));
                dgDataAcionamento = dataHora[0];
                dgHoraAcionamento = dataHora[1];
            }

            isEncerrada = ocorrencia.getString("ocorrenciaEncerrada").equals("true");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] parseData(String dataOriginal) {
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

    public static List<String> getListaDelitos() {
        return listaDelitos;
    }

    public static void setListaDelitos(List<String> listaDelitos) {
        CarregarOcorrencia.listaDelitos = listaDelitos;
    }

    public static String getPossiveisSuspeitos() {
        return possiveisSuspeitos;
    }

    public static String getValoresSubtraidos() {
        return valoresSubtraidos;
    }

    public static List<String> getListaPeritos() {
        return listaPeritos;
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

    public static JSONArray getVestigios() {
        return vestigios;
    }


}





