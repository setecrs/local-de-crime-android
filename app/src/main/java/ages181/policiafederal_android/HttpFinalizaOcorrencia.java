package ages181.policiafederal_android;

import android.os.AsyncTask;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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

public class HttpFinalizaOcorrencia extends AsyncTask<Void, Void, Void> {

    private String idOcorrencia;
    private int status;

    public HttpFinalizaOcorrencia(String id) {
        this.idOcorrencia = id;
    }

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {
            String json = "{\"idOcorrencia\": \"" + idOcorrencia + "\"}";

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url(StaticProperties.getUrl() + "ocorrencias/finalizar/" + StaticProperties.getIdOcorrencia())
                    .patch(body)
                    .build();

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

            Response response = clientBuilder.build().newCall(request).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getIdOcorrencia());
            System.out.println("Response body http finalizar ocorrencia: " + response.body().string());
            status = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    public int getStatusCode() {
        return status;
    }
}
