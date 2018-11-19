package ages181.policiafederal_android;

import android.os.AsyncTask;

import org.json.JSONArray;

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

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpNovoVestigio extends AsyncTask<Void, Void, Void> {

    //Variáveis que o usuário informou na tela de adicionar vestígios
    private String etiqueta, informacoesAdicionais;
    private boolean coletado;
    private String tipoVestigio, nomeVestigio;

    //Construtor
    public HttpNovoVestigio(boolean coletadoN, String etiquetaN, String informacoesAdicionaisN, String tipo, String nomeVest) {
        this.coletado = coletadoN;
        this.etiqueta = etiquetaN.replace("\"", "\\\"");
        this.informacoesAdicionais = informacoesAdicionaisN.replace("\"", "\\\"");
        this.tipoVestigio = tipo.replace("\"", "\\\"");
        this.nomeVestigio = nomeVest.replace("\"", "\\\"");
    }

    //Variáveis locais
    private String idTipoVestigio;


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {
            //Realizando a verificação para encontrar o ID do tipoVestigio cadastrado no banco de acordo com o que o usuário informou
            JSONArray jsonVestigios = StaticProperties.getJsonListas().getJSONArray("tipoVestigios");
            //Percorrendo todos os tiposVestigios
            for (int i = 0; i < jsonVestigios.length(); i++) {
                //Comparando cada vestigio com o nome informado pelo usuário
                if (nomeVestigio.equals(jsonVestigios.getJSONObject(i).getString("nomeVestigio")) &&
                        tipoVestigio.equals(jsonVestigios.getJSONObject(i).getString("tipoVestigio"))) {
                    //Setando o ID do tipoVestigio na variável local idTipoVestigio
                    idTipoVestigio = jsonVestigios.getJSONObject(i).getString("_id");
                    break;
                }
            }


            //Criando o JSON que será enviado ao banco para criar um novo vestígio
            String json = "{\"tipo\":\"" + idTipoVestigio + "\"," +
                    "\"coletado\":\"" + coletado + "\"," +
                    "\"etiqueta\":\"" + etiqueta + "\"," +
                    "\"informacoesAdicionais\":\"" + informacoesAdicionais + "\"}";

            //Setando a requisição JSON criada acima
            RequestBody body_vestigio = RequestBody.create(JSON, json);

            //Criando a requisição com a rota para criar um novo vestígio
            Request requestNovoVestigio = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .post(body_vestigio)
                    .url(StaticProperties.getUrl() + "vestigios/" + StaticProperties.getIdOcorrencia())
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

            Response response = clientBuilder.build().newCall(requestNovoVestigio).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getIdOcorrencia());
            System.out.println(response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;

        }
    }

}