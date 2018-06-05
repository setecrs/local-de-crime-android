package ages181.policiafederal_android;


import org.json.JSONObject;

public class CarregarOcorrencia {

    // Dados Gerais

    static String dgNumeroOcorrencia, dgSedeOcorrencia,  dgHoraAcionamento, dgDataAcionamento;
    // LISTA DE MAIS DE UM PERITO, ARRUMAR CLASSE DADOSGERAIS COM AUTOCOMPLETE ===>> String[] dgPeritoOcorrencia

    // Reponsavel

    static String respNome, respCargo, respDoc, respEntrevista;

    // Sobre o Fato

    static String sfHoraProvavel, sbDataProvavel, sfTipoDelito, sfModusOperandi;
    // ====>>> sfCheckBox

    // Sobre o Local

    static String sbDatachegada, sbHoraChegada, sbCondicoesLocal, sbInfo;

    // Tela Endereco

    static String endEstado, endCidade, endLocal, endOutroLocal, endRua, endNumero, endComplemento;

    // Tela Testemunha

    static String testNome, testDoc, testFuncao, testEntrevista;

    public void carregaOcorrencia(JSONObject ocorrencia){
        try {
            dgNumeroOcorrencia = (String)ocorrencia.get("numeroOcorrencia");
            dgSedeOcorrencia = (String)ocorrencia.get("sede");
            //peritos acionados
            endLocal = (String)ocorrencia.get("tipoLocal");
            endEstado = (String)ocorrencia.get("estado");
            endCidade = (String)ocorrencia.get("municipio");
            endRua = (String)ocorrencia.get("logradouro");
            endComplemento = (String)ocorrencia.get("complemento");
            // ==== >>> ATUALIZAR API COM NUMERO ---- endNumero = (String)ocorrencia.get("numero");
            respNome = (String)ocorrencia.get("nomeResponsavel");
            respCargo = (String)ocorrencia.get("cargoResponsavel");
            respDoc = (String)ocorrencia.get("documentoResponsavel");
            respEntrevista = (String)ocorrencia.get("entrevistaResponsavel");
            testNome = (String)ocorrencia.get("nomeTesteminha");
            testFuncao = (String)ocorrencia.get("cargoTestemunha");
            testDoc = (String)ocorrencia.get("documentoTestemunha");
            testEntrevista = (String)ocorrencia.get("entrevistaTestemunha");
            // receber date e separar em data e hora (CHEGADA)
            sbCondicoesLocal = (String)ocorrencia.get("condicaoLocal");
            sbInfo = (String)ocorrencia.get("informacoesAdicionais");
            // receber date e separar em data e hota (OCORRENCIA)
            sfTipoDelito = (String)ocorrencia.get("tipoDelito");
            // ARRAY de modus operandi ---- sfModusOperandi =
            // VALOES SUBTRAIDOS ???
            // VESTIGIOS ARRAY
            // DATA HORA ACIONAMENTO

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getDgNumeroOcorrencia() {
        return dgNumeroOcorrencia;
    }

    public static String getDgSedeOcorrencia() {
        return dgSedeOcorrencia;
    }

    /*public static String getDgPeritoOcorrencia() {
        return dgPeritoOcorrencia;
    }*/

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

    public static String getSbDataProvavel() {
        return sbDataProvavel;
    }

    public static String getSfTipoDelito() {
        return sfTipoDelito;
    }

    public static String getSfTipoModusOperandi() {
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
}
