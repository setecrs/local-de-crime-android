package ages181.policiafederal_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

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


    public CarregarCidades (String nomeCidade, String idCidade){
        this.nomeCidade = nomeCidade;
        this.idCidade = idCidade;
    }

    public void carregaCidades() throws IOException, JSONException {

        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .addHeader("x-access-token", StaticProperties.getToken())
                .url("https://ages-pf.herokuapp.com//obter_listas/")
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();
        JSONObject responseObject = new JSONObject(body);
        array = responseObject.getJSONArray("Estados");
        JSONObject aux = null;
        for(int i = 0 ; i < array.length() ; i++){
            aux = array.getJSONObject(i);
            CarregarCidades cidade = new CarregarCidades(aux.get("nome").toString(), aux.get("_id").toString());
        }
    }



    public String getNomeCidade(){return nomeCidade;}

    public String getIdCidade(){return idCidade;}

}
