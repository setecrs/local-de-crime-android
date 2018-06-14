package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    private Editable usuario;
    private Editable senha;

    public HttpOcorrencias (){
    }


    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected List<Ocorrencia> doInBackground(Void... params) {
        try {


            String json = "";

            RequestBody body_ocorrencia = RequestBody.create(JSON, json);

            Request requestSignup = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url("https://ages-pf.herokuapp.com/ocorrencias")
                    .build();

            Response responseSignup = client.newCall(requestSignup).execute();

            JSONArray ocorrenciaArray = new JSONArray(responseSignup.body().string());
            StaticJson.setJsinho(ocorrenciaArray);

            List<Ocorrencia> lista = new ArrayList<>();
            for (int i = 0; i < ocorrenciaArray.length(); i++) {
                System.out.println(ocorrenciaArray.getJSONObject(i).toString());
                lista.add(new Ocorrencia(
                        ocorrenciaArray.getJSONObject(i).getString("_id"),
                        ocorrenciaArray.getJSONObject(i).getString("numeroOcorrencia"),
                        ocorrenciaArray.getJSONObject(i).getString("dataHoraAcionamento"),
                        ocorrenciaArray.getJSONObject(i).getString("tipoLocal")));
            }

            Collections.sort(lista, new Comparator<Ocorrencia>()
            {
                public int compare(Ocorrencia p1, Ocorrencia p2)
                {
                    return p2.getDataHoraAcionamento().compareTo(p1.getDataHoraAcionamento());
                }
            });


            ListaOcorrencia.setLista(lista);

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }

}
