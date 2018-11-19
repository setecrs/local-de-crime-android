package ages181.policiafederal_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class StaticProperties {

    private static String url = "https://192.168.0.103:3009/";
    // private static String url = "http://www.hml.ages.pucrs.br:4601/";
    private static String token;
    private static String idOcorrencia;
    private static JSONObject jsonListas;
    private static List<Vestigio> listaVestigios;
    private static List<Ocorrencia> listaOcorrencias;
    private static JSONArray jsonArrayOcorrencias;
    private static JSONArray jsonArrayVestigios;
    private static JSONObject vestigioClicado;

    public static JSONObject getVestigioClicado() {
        return vestigioClicado;
    }

    public static void setVestigioClicado(JSONObject vestigioClicado) {
        StaticProperties.vestigioClicado = vestigioClicado;
    }

    public static JSONArray getJsonArrayVestigios() {
        return jsonArrayVestigios;
    }

    public static void setJsonArrayVestigios(JSONArray jsonArrayVestigios) {
        StaticProperties.jsonArrayVestigios = jsonArrayVestigios;
    }

    public static JSONArray getJsonArrayOcorrencias() {
        return jsonArrayOcorrencias;
    }

    public static void setJsonArrayOcorrencias(JSONArray jsinho) {
        StaticProperties.jsonArrayOcorrencias = jsinho;
    }

    public static List<Ocorrencia> getListaOcorrencias() {
        return listaOcorrencias;
    }

    public static void setListaOcorrencias(List<Ocorrencia> lista) {
        StaticProperties.listaOcorrencias = lista;
    }

    public static List<Vestigio> getListaVestigios() {
        return listaVestigios;
    }

    public static void setListaVestigios(List<Vestigio> lista) {
        StaticProperties.listaVestigios = lista;
    }

    public static JSONObject getJsonListas() {
        return jsonListas;
    }

    public static void setJsonListas(JSONObject jsonListas) {
        StaticProperties.jsonListas = jsonListas;
    }

    public static String getIdOcorrencia() {
        return idOcorrencia;
    }

    public static void setIdOcorrencia(String idOcorrencia) {
        StaticProperties.idOcorrencia = idOcorrencia;
    }

    public static String getUrl() {
        return url;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenNovo) {
        token = tokenNovo;
    }

    /**
     * Recebe dois parametros nos formatos dd/MM/yyyy e HH:MM e passa para
     * o formato Date.
     *
     * @param data recebe string com formato da data dd/MM/yyyy
     * @param hora recebe string com formato da hora HH:mm
     * @return Date formatado
     */
    public static Long formataDataHora(String data, String hora) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        String[] dataSplit = data.split("/");
        String[] horaSplit = hora.split(":");
        cal.set(Calendar.YEAR, Integer.parseInt(dataSplit[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(dataSplit[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataSplit[0]));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaSplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(horaSplit[1]));
        cal.getTimeInMillis();
        return cal.getTimeInMillis();
    }
}
