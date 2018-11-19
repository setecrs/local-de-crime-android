package ages181.policiafederal_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;

/**
 * Created by arthu on 09/06/2018.
 */

public class CarregarCidades {
    static String nomeCidade;
    static String idCidade;

    JSONArray array;

    OkHttpClient client = new OkHttpClient();


    public CarregarCidades(String nomeCidade, String idCidade) {
        CarregarCidades.nomeCidade = nomeCidade;
        CarregarCidades.idCidade = idCidade;
    }

    public void carregaCidades() throws IOException, JSONException {

        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .addHeader("x-access-token", StaticProperties.getToken())
                .url("https://ages-pf.herokuapp.com//obter_listas/")
                .build();

        try{
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


            String body = response.body().string();
            JSONObject responseObject = new JSONObject(body);
            array = responseObject.getJSONArray("Estados");
            JSONObject aux = null;
            for (int i = 0; i < array.length(); i++) {
                aux = array.getJSONObject(i);
                CarregarCidades cidade = new CarregarCidades(aux.get("nome").toString(), aux.get("_id").toString());
            }

        }catch (Exception e){
            e.printStackTrace();;
        }

    }


    public String getNomeCidade() {
        return nomeCidade;
    }

    public String getIdCidade() {
        return idCidade;
    }

}
