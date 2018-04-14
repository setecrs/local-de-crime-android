package ages181.policiafederal_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 15104313 on 13/04/18.
 */

public class Teste extends AsyncTask<Void, Void, Void> {

    private Exception exception;

    protected Void doInBackground(Void... params) {
        try {

            StringBuilder result = new StringBuilder();
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-Type", "application/" + "POST");
            conn.setRequestProperty("title", "foo");
            conn.setRequestProperty("body", "bar");
            conn.setRequestProperty("userId", "1");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(result.append(line));
            }
            rd.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }
    }

}