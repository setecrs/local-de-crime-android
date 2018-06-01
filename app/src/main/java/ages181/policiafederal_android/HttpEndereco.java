package ages181.policiafederal_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class HttpEndereco extends AsyncTask<Void, Void, Void> {

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    protected Void doInBackground(Void... params) {
        try {

            Request request = new Request.Builder()
                    .url("https://jsonplaceholder.typicode.com/posts" )
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
