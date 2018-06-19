package ages181.policiafederal_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.util.Base64;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpOcorrencias extends AsyncTask<Void, Void, List<Ocorrencia>> {

    //Construtor
    public HttpOcorrencias ( ){
    };

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected List<Ocorrencia> doInBackground(Void... params) {

        String aux;
        String json = "";


        try {
            //Requisição para listar as ocorrências
            Request requestSignup = new Request.Builder()
                        .addHeader("content-type", "application/json")
                        .addHeader("x-access-token", StaticProperties.getToken())
                        .url("https://ages-pf.herokuapp.com/ocorrencias")
                        .build();

            //Capturando resposta da requisição
            Response responseSignup = client.newCall(requestSignup).execute();

            //Quebrando a resposta em um JSONArray para manipula-la como um Array
            JSONArray ocorrenciaArray = new JSONArray(responseSignup.body().string());

            //Enviando o JSONArray para um método static para poder carregar os dados das ocorrências
            StaticProperties.setJsonArrayOcorrencias(ocorrenciaArray);

            //Criando uma lista que será listada na tela de ocorrências
            List<Ocorrencia> lista = new ArrayList<>();

            //Percorrendo todos elementos contidos no Array JSON recebido
            for (int i = 0; i < ocorrenciaArray.length(); i++) {
                if(ocorrenciaArray.getJSONObject(i).isNull("tipoLocal")){
                    aux = "";
                } else {
                    aux = ocorrenciaArray.getJSONObject(i).getJSONObject("tipoLocal").getString("tipoLocal");
                }
                lista.add(new Ocorrencia(
                        ocorrenciaArray.getJSONObject(i).getString("_id"),
                        ocorrenciaArray.getJSONObject(i).getString("numeroOcorrencia"),
                        ocorrenciaArray.getJSONObject(i).getString("dataHoraAcionamento"),
                        aux));

            }

            //Ordenando lista de acordo com a data
            Collections.sort(lista, new Comparator<Ocorrencia>() {
                public int compare(Ocorrencia p1, Ocorrencia p2) {
                    return p2.getDataHoraAcionamento().compareTo(p1.getDataHoraAcionamento());
                }
            });

            //Setando lista static de ocorrências
            StaticProperties.setListaOcorrencias(lista);

            //Retornando a lista para poder lista-la na tela de ocorrências
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }


}