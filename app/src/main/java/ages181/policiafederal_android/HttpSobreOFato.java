package ages181.policiafederal_android;

/**
 * Created by arthu on 28/04/2018.
 */
import android.os.AsyncTask;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpSobreOFato extends AsyncTask<Void, Void, Void> {

    private String outroTipoDelito, tipoDelito;
    private Date dataOcorrencia;
    private int status;

    public HttpSobreOFato(String outroTipoDelito, String tipoDelito, Date dataOcorrencia){
        this.outroTipoDelito = outroTipoDelito;
        this.tipoDelito = tipoDelito;
        this.dataOcorrencia = dataOcorrencia;
     }

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {
            String json = "{\"outroTipoDelito\": \"" + outroTipoDelito + "\"," +
                    "\"tipoDelito\": \"" + tipoDelito + "\"," +
                    "\"dataOcorrencia\": \"" + dataOcorrencia + "\"}";

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url(StaticProperties.getUrl() + "sobre_fato/" + StaticProperties.getIdOcorrencia())
                    .patch(body)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getIdOcorrencia());
            System.out.println("Response body Http sobre o fato: " + response.body().string());
            status = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }

    public int getStatusCode(){
        return status;
    }
}
