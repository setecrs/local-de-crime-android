package ages181.policiafederal_android;

/**
 * Created by arthu on 28/04/2018.
 */
import android.os.AsyncTask;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpDadosGerais extends AsyncTask<Void, Void, Void> {

    private String numOcorrencia;
    private String sedeOcorrencia;
    private String peritosAcionados;
    private Date dataHoraAcionamento;

    public HttpDadosGerais(String numOcorrencia, String sedeOcorrencia, String peritosAcionados, Date dataHoraAcionamento){
        this.numOcorrencia = numOcorrencia;
        this.sedeOcorrencia = sedeOcorrencia;
        this.peritosAcionados = peritosAcionados;
        this.dataHoraAcionamento = dataHoraAcionamento;
    }

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"numeroOcorrencia\": \"" + numOcorrencia + "\", \"sede\": \""+ sedeOcorrencia + "\", \"peritosAcionados\" : \"" + peritosAcionados + "\", \"dataHoraAcionamento\" : \"" + dataHoraAcionamento + "\"}";
            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .url("https://ages-pf.herokuapp.com//dados_gerais/"+numOcorrencia)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("Responde body: " + response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
