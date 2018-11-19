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


public class HttpEndereco extends AsyncTask<Void, Void, Void> {

    private Exception exception;
    private String tipoLocal, outroTipoLocal, estado, cidade, rua, numero, complemento;
    private int status;

    public HttpEndereco(String tipoLocal, String outroTipoLocal, String estado, String cidade, String rua, String numero, String complemento) {

        this.tipoLocal = tipoLocal;
        this.outroTipoLocal = outroTipoLocal.replace("\"", "\\\"");
        this.estado = estado.replace("\"", "\\\"");
        this.cidade = cidade.replace("\"", "\\\"");
        this.rua = rua.replace("\"", "\\\"");
        this.numero = numero.replace("\"", "\\\"");
        this.complemento = complemento.replace("\"", "\\\"");
    }

    public int getStatusCode() {
        return status;
    }

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    protected Void doInBackground(Void... params) {
        try {
            String json = "{\"tipoLocal\": \"" + tipoLocal + "\", \"estado\": \"" + estado +
                    "\", \"logradouro\": \"" + rua + "\", \"complemento\": \"" + complemento +
                    "\", \"numero\": \"" + numero + "\", \"municipio\": \"" + cidade + "\"," +
                    " \"outroTipoLocal\": \"" + outroTipoLocal + "\"}";

            RequestBody body_endereco = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .patch(body_endereco)
                    .url(StaticProperties.getUrl() + "endereco/" + StaticProperties.getIdOcorrencia())
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
            System.out.println(response.body().string());
            status = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}


//route: /endereco/{idOcorrencia} | method: PATCH | params:
//        {
//        "tipoLocal": String,
//        "estado": String,
//        "municipio": String,
//        "logradouro": String,
//        "complemento": String,
//        "_id": Ocorrencia._id
//        }
//        | Headers: {"x-access-token": [JWT TOKEN]) } [DEVE ESTAR AUTENTICADO]