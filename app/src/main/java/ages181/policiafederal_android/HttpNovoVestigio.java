package ages181.policiafederal_android;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

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

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpNovoVestigio extends AsyncTask<Void, Void, Void> {

    //Variáveis que o usuário informou na tela de adicionar vestígios
    private String etiqueta, informacoesAdicionais;
    private boolean coletado;
    private String tipoVestigio, nomeVestigio;

    //Construtor
    public HttpNovoVestigio (boolean coletadoN, String etiquetaN, String informacoesAdicionaisN, String tipo, String nomeVest){
        this.coletado = coletadoN;
        this.etiqueta = etiquetaN;
        this.informacoesAdicionais = informacoesAdicionaisN;
        this.tipoVestigio = tipo;
        this.nomeVestigio = nomeVest;
    };

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
                    .url(StaticProperties.getUrl() + "vestigios/"+StaticProperties.getIdOcorrencia())
                    .build();

            //Capturando a resposta da requisição
            Response responseNovoVestigio = client.newCall(requestNovoVestigio).execute();

            System.out.println("ID ocorrencia: " + StaticProperties.getIdOcorrencia());
            System.out.println(responseNovoVestigio.body().string());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }
    }

}