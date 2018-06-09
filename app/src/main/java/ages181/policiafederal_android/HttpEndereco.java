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
    private String local, estado, cidade, rua, numero, complemento, id;
    public HttpEndereco(String local, String estado, String cidade, String rua, String nummero, String complemento, String id) {

        this.local = local;
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.id = id;
    }


    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected Void doInBackground(Void... params) {
        try {

            String json = "{\"tipolocal\": "+ local +"\"estado\": "+ estado +"\"municipio\": "+ cidade +
                            "\"logradouro\": "+ rua + "\"complemento\": "+ complemento +
                            "\"numero\": "+ numero + "\"_id\" :" + id +"}";

            RequestBody body_endereco = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .patch(body_endereco)
                    .url(StaticProperties.getUrl()+ "endereco/" + id)
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body());

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