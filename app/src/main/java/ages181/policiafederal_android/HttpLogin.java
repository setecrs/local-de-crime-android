package ages181.policiafederal_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpLogin extends AsyncTask<Void, Void, Void> {

    private Exception exception;
    private String teste;

    OkHttpClient client = new OkHttpClient.Builder().followRedirects(false).build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"usuario\": \"bbb\", \"senha\": \"bbb\"}";

            RequestBody body = RequestBody.create(JSON, json);

            RequestBody formBody = new FormBody.Builder()
                    .add("usuario", "cassio")
                    .add("senha", "cassio")
                    .build();

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")

                    .url("https://ages-pf.herokuapp.com/login")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            teste = response.body().string();
            System.out.println(teste);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;

        }
    }


}