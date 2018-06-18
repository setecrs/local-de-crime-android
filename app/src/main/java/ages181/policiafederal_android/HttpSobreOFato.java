package ages181.policiafederal_android;

/**
 * Created by arthu on 28/04/2018.
 */
import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpSobreOFato extends AsyncTask<Void, Void, Void> {

    private String outroTipoDelito;

    public HttpSobreOFato(String outroTipoDelito){
        this.outroTipoDelito = outroTipoDelito;
     }

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {
            String json = "{\"outroTipoDelito\": \"" + outroTipoDelito + "\"}";

            // String json = "{\"informacoesAdicionais\": \"" + infoAdicional + "\", \"sede\": \""+ sedeOcorrencia + "\", \"dataHoraAcionamento\" : \"" + dataHoraAcionamento + "\"}";

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url(StaticProperties.getUrl() + "sobre_fato/" + StaticProperties.getId())
                    .patch(body)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getId());
            System.out.println("Response body Http sobre o fato: " + response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
