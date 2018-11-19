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


public class HttpResponsavel extends AsyncTask<Void, Void, Void> {

    private Exception exception;
    private String nome, cargo, documento, entrevista;
    private int status;

    public HttpResponsavel(String nome, String cargo, String documento, String entrevista) {

        this.nome = nome.replace("\"", "\\\"");
        this.cargo = cargo.replace("\"", "\\\"");
        this.documento = documento.replace("\"", "\\\"");
        this.entrevista = entrevista.replace("\"", "\\\"");
    }

    public int getStatusCode() {
        return status;
    }


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"nomeResponsavel\": \"" + nome + "\", \"cargoResponsavel\": \"" + cargo +
                    "\", \"documentoResponsavel\": \"" + documento + "\", \"entrevistaResponsavel\": \"" + entrevista +
                    "\"}";

            RequestBody body_responsavel = RequestBody.create(JSON, json);

            Request requestResponsavel = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .patch(body_responsavel)
                    .url(StaticProperties.getUrl() + "responsavel_local/" + StaticProperties.getIdOcorrencia())
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

            Response response = clientBuilder.build().newCall(requestResponsavel).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getIdOcorrencia());
            System.out.println(response.body().string());
            status = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}