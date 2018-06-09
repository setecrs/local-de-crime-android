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

public class HttpTestemunha extends AsyncTask<Void, Void, Void> {

    private String nome, documento, funcao,  entrevista;

    public HttpTestemunha(String nome, String documento, String funcao, String entrevista){
        this.nome = nome;
        this.documento = documento;
        this.funcao = funcao;
        this.entrevista = entrevista;
    }

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"nomeTestemunha\": \"" + nome + "\", \"documentoTestemunha\": \""+ documento + "\", \"funcaoTestemunha\": \"" + funcao + "\", \"entrevistaTestemunha\": \"" + entrevista + "\"}";

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url(StaticProperties.getUrl() + "testemunhas/" + StaticProperties.getId())
                    .patch(body)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getId());
            System.out.println("Responde body Testemunhas: " + response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
