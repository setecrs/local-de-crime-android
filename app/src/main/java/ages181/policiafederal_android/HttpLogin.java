package ages181.policiafederal_android;

import android.os.AsyncTask;

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

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"usuario\": \"bbb\", \"senha\": \"bbb\"}";

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .url("https://ages-pf.herokuapp.com/login")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;

        }
    }


}