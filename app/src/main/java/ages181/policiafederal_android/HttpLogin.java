package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

<<<<<<< 9d62d170715e6d5289ab9ae5ee414eaa06860b0d
import junit.framework.Test;

=======
>>>>>>> feature: Login apenas com usuário válido
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
                    .url("https://ages-pf.herokuapp.com/login")
                    .build();

            Response responseLogin = client.newCall(requestLogin).execute();

            String body = responseLogin.body().string();

            JSONObject responseObject = new JSONObject(body);

            if (responseObject.has("token")) {
                System.out.println("TOKEN: " + responseObject.get("token"));
                token.setToken((String) responseObject.get("token"));
            } else {
                token.setToken(null);
                System.out.println("Login inválido");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }

}