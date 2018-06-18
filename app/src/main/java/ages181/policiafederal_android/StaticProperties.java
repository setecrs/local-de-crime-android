package ages181.policiafederal_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class StaticProperties {

    private static String url = "http://ages-pf.herokuapp.com/";
    private static String token;
    private static String id;
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
        StaticProperties.jsonArrayOcorrencias= jsinho;
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

    public static String getId(){
        return id;
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

    public static void setId(String idNovo){
        id = idNovo;
    }
}
