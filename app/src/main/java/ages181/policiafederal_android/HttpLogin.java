package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

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

public class HttpLogin extends AsyncTask<Void, Void, Void> {

    private Editable usuario;
    private Editable senha;

    public HttpLogin (Editable usuario, Editable senha){
        this.usuario = usuario;
        this.senha = senha;
    };

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            // GET /login
            String credentials = usuario + ":" + senha;
            String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);

            Request requestLogin = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("Authorization", basic)
                    .url(StaticProperties.getUrl() + "login")
                    .build();

            Response responseLogin = client.newCall(requestLogin).execute();

            String body = responseLogin.body().string();

            JSONObject responseObject = new JSONObject(body);

            if (responseObject.has("token")) {
                System.out.println("TOKEN: " + responseObject.get("token"));
                //System.out.println("ID: " + responseObject.get("_id"));
                StaticProperties.setToken(((String) responseObject.get("token")));

                String json = "";

                RequestBody body_ocorrencia = RequestBody.create(JSON, json);

                Request requestSignup = new Request.Builder()
                        .addHeader("content-type", "application/json")
                        .addHeader("x-access-token", token.getToken())
                        .post(body_ocorrencia)
                        .url(StaticProperties.getUrl() + "signup")
                        .build();

                Response responseSignup = client.newCall(requestSignup).execute();


                System.out.println(responseSignup.body().string());

            } else {
                StaticProperties.setToken(null);
                System.out.println("Login inválido");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }

}