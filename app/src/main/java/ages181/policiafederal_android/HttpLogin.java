package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

import junit.framework.Test;

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
/*
            // POST /SIGNUP
            String json = "{\"username\": \"" +  usuario + "\", \"password\": \"" + senha +"\", \"name\": \"test\"}";
            System.out.println("JSON" + json);

            RequestBody body = RequestBody.create(JSON, json);

            Request requestSignup = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .post(body)
                    .url("https://ages-pf.herokuapp.com/signup")
                    .build();

            Response responseSignup = client.newCall(requestSignup).execute();

            System.out.println(responseSignup.body().string());
*/

            // GET /login
            String credentials = usuario + ":" + senha;
            String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);

            Request requestLogin = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("Authorization", basic)
                    .url("https://ages-pf.herokuapp.com/login")
                    .build();

            Response responseLogin = client.newCall(requestLogin).execute();

            String body = responseLogin.body().string();

            JSONObject responseObject = new JSONObject(body);

            if (responseObject.has("token")) {
                System.out.println("TOKEN: " + responseObject.get("token"));
            } else {
                System.out.println("Login inválido");
            }

            /*
            // GET /profile
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdGF0dXMiOjIwMCwibWVzc2FnZSI6InZhbW8yQGNjYyIsImlhdCI6MTUyNTY1NTk5MCwiZXhwIjoxNTI1NjU3NDMwfQ.gyHAxKWBLCHkZeUt95hVl9iJukmKpABmlvn5wrDgsFI";

            Request requestProfile = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", token)
                    .url("https://ages-pf.herokuapp.com/profile")
                    .build();

            Response responseProfile = client.newCall(requestProfile).execute();

            System.out.println(responseProfile.body().string());
            */

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }

}