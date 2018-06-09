package ages181.policiafederal_android;

import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpResponsavel extends AsyncTask<Void, Void, Void> {

    private Exception exception;
    private String nome, cargo, documento, entrevista;
    public HttpResponsavel(String nome, String cargo, String documento, String entrevista) {

        this.nome = nome;
        this.cargo = cargo;
        this.documento = documento;
        this.entrevista = entrevista;
    }


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"nomeResponsavel\": \""+ nome +"\", \"cargoResponsavel\": \""+ cargo +
            "\", \"documentoResponsavel\": \""+ documento + "\", \"entrevistaResponsavel\": \""+ entrevista +
            "\"}";

            RequestBody body_responsavel = RequestBody.create(JSON, json);

            Request requestResponsavel = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .patch(body_responsavel)
                    .url(StaticProperties.getUrl()+ "responsavel_local/" + StaticProperties.getId())
                    .build();

            Response responseResponsavel = client.newCall(requestResponsavel ).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getId());
            System.out.println(responseResponsavel.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}