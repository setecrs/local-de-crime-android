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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpNovaOcorrencia extends AsyncTask<Void, Void, Void> {


    public HttpNovaOcorrencia (){
    };


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {
            //Definindo o JSON para criar uma ocorrência em branco
            String json = "";

            //Setando o JSON que será enviado ao banco
            RequestBody body_ocorrencia = RequestBody.create(JSON, json);

            //Criando a requisição POST para criar uma ocorrência
            Request requestSignup = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .post(body_ocorrencia)
                    .url(StaticProperties.getUrl() + "ocorrencias")
                    .build();

            //Capturando a resposta da requisição
            Response responseSignup = client.newCall(requestSignup).execute();

            //Capturando a resposta como String
            String body = responseSignup.body().string();

            //Quebrando a resposta em um JSONObject para poder manipular
            JSONObject responseObject = new JSONObject(body);

            //Setando o ID de uma nova ocorrência em um método static
            StaticProperties.setIdOcorrencia(responseObject.getString("_id"));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }

}