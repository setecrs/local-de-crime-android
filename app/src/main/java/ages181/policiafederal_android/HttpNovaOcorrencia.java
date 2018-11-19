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
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpNovaOcorrencia extends AsyncTask<Void, Void, Void> {


    public HttpNovaOcorrencia() {
    }


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
            //------------------------SSL

            OkHttpClient.Builder clientBuilder = client.newBuilder().readTimeout(60, TimeUnit.SECONDS);

            boolean allowUntrusted = true;

            if (  allowUntrusted) {
                final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] cArrr = new X509Certificate[0];
                        return cArrr;
                    }

                    @Override
                    public void checkServerTrusted(final X509Certificate[] chain,
                                                   final String authType) throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(final X509Certificate[] chain,
                                                   final String authType) throws CertificateException {
                    }
                }};

                SSLContext sslContext = SSLContext.getInstance("SSL");

                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                clientBuilder.sslSocketFactory(sslContext.getSocketFactory());

                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                clientBuilder.hostnameVerifier( hostnameVerifier);
            }

            //------------------------SSL

            Response response = clientBuilder.build().newCall(requestSignup).execute();

            //Capturando a resposta como String
            String body = response.body().string();

            //Quebrando a resposta em um JSONObject para poder manipular
            JSONObject responseObject = new JSONObject(body);

            //Setando o ID de uma nova ocorrência em um método static
            StaticProperties.setIdOcorrencia(responseObject.getString("_id"));
            CarregarOcorrencia.zeraTela(responseObject);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;

        }

    }

}