package ages181.policiafederal_android;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpOcorrencias extends AsyncTask<Void, Void, List<Ocorrencia>> {

    //Construtor
    public HttpOcorrencias() {
    }

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected List<Ocorrencia> doInBackground(Void... params) {

        String aux;
        String json = "";


        try {
            //Requisição para listar as ocorrências
            Request requestSignup = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url(StaticProperties.getUrl() + "ocorrencias")
                    .build();

            //Capturando resposta da requisição
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

            Response responseSignup = clientBuilder.build().newCall(requestSignup).execute();

            //Quebrando a resposta em um JSONArray para manipula-la como um Array
            JSONArray ocorrenciaArray;
            try {
                ocorrenciaArray = new JSONArray(responseSignup.body().string());
            } catch (Exception e) {
                ocorrenciaArray = new JSONArray();
                e.printStackTrace();
            }

            //Enviando o JSONArray para um método static para poder carregar os dados das ocorrências
            StaticProperties.setJsonArrayOcorrencias(ocorrenciaArray);

            //Criando uma lista que será listada na tela de ocorrências
            List<Ocorrencia> lista = new ArrayList<>();

            //Percorrendo todos elementos contidos no Array JSON recebido
            for (int i = 0; i < ocorrenciaArray.length(); i++) {
                if (ocorrenciaArray.getJSONObject(i).isNull("tipoLocal")) {
                    aux = "";
                } else {
                    aux = ocorrenciaArray.getJSONObject(i).getJSONObject("tipoLocal").getString("tipoLocal");
                }
                lista.add(new Ocorrencia(
                        ocorrenciaArray.getJSONObject(i).getString("_id"),
                        ocorrenciaArray.getJSONObject(i).getString("numeroOcorrencia"),
                        ocorrenciaArray.getJSONObject(i).getString("dataHoraAcionamento"),
                        aux));

            }

            //Ordenando lista de acordo com a data
            Collections.sort(lista, new Comparator<Ocorrencia>() {
                public int compare(Ocorrencia p1, Ocorrencia p2) {
                    return p2.getDataHoraAcionamento().compareTo(p1.getDataHoraAcionamento());
                }
            });

            //Setando lista static de ocorrências
            StaticProperties.setListaOcorrencias(lista);

            //Retornando a lista para poder lista-la na tela de ocorrências
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;

        }

    }


}