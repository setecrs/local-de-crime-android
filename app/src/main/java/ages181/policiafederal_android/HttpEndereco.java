package ages181.policiafederal_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class HttpEndereco extends AsyncTask<Void, Void, Void> {

    private Exception exception;
    private String local, estado, cidade, rua, numero, complemento;
    public HttpEndereco(String local, String estado, String cidade, String rua, String numero, String complemento) {

        this.local = local;
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    protected Void doInBackground(Void... params) {
        try {

            // TODO: Implementar Município
            String json = "{\"tipolocal\": \""+ local +"\", \"estado\": \""+ estado +
            "\", \"logradouro\": \""+ rua + "\", \"complemento\": \""+ complemento +
            "\", \"numero\": \""+ numero +"\"}";

            RequestBody body_endereco = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .patch(body_endereco)
                    .url(StaticProperties.getUrl()+ "endereco/" + StaticProperties.getId())
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getId());
            System.out.println(response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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