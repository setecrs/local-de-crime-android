package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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

    public HttpLogin(Editable usuario, Editable senha) {
        this.usuario = usuario;
        this.senha = senha;

    }


    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            // GET /login
            String credentials = (usuario + ":" + senha).replace("\"", "\\\"");
            String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Request requestLogin = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("Authorization", basic)
                    .url(StaticProperties.getUrl() + "login")
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

            Response responseLogin = clientBuilder.build().newCall(requestLogin).execute();

            String body = responseLogin.body().string();

            JSONObject responseObject = new JSONObject(body);
            StaticProperties.setToken(((String) responseObject.get("token")));
            if (responseObject.has("token") && StaticProperties.getToken() != null) {
                System.out.println("TOKEN: " + responseObject.get("token"));
                //System.out.println("ID: " + responseObject.get("_id"));

                String json = "";

                RequestBody body_ocorrencia = RequestBody.create(JSON, json);

                Request requestListas = new Request.Builder()
                        .addHeader("content-type", "application/json")
                        .addHeader("x-access-token", StaticProperties.getToken())
                        .url(StaticProperties.getUrl() + "obter_listas")
                        .build();
                //------------------------SSL

                clientBuilder = client.newBuilder().readTimeout(60, TimeUnit.SECONDS);

                allowUntrusted = true;

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

                Response response = clientBuilder.build().newCall(requestListas).execute();

                JSONObject listas = new JSONObject(response.body().string());

                StaticProperties.setJsonListas(listas);

            } else {
                StaticProperties.setToken(null);
                System.out.println("Login inválido");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;

        }
    }
}
