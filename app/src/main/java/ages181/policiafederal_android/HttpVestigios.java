package ages181.policiafederal_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.util.Base64;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15104313 on 13/04/18.
 */

public class HttpVestigios extends AsyncTask<Void, Void, List<Vestigio>> {


    public HttpVestigios ( ){
    };

    private Exception exception;

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    protected List<Vestigio> doInBackground(Void... params) {
        try {
            //Variável local
            String idOcorrencia;

            //Comparação para verificar se é uma nova ocorrência ou uma existente
            idOcorrencia = StaticProperties.getId();

            //Requisição para capturar os vestígios da ocorrência acessada
            Request requestSignup = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .addHeader("x-access-token", StaticProperties.getToken())
                    .url("https://ages-pf.herokuapp.com/vestigios/"+idOcorrencia)
                    .build();

            //Capturando resposta
            Response responseSignup = client.newCall(requestSignup).execute();

            //Quebrando a resposta em um JSONArray para poder manipular
            JSONArray vestigiosArray = new JSONArray(responseSignup.body().string());

            //Váriavel local para numerar os vestígios
            int numId = 0;

            //Lista para adicionar os vestígios encontrados
            List<Vestigio> lista = new ArrayList<>();


            StaticProperties.setJsonArrayVestigios(vestigiosArray);

            //Método para percorrer todos os vestígios encontrados
            for (int i = 0; i < vestigiosArray.length(); i++) {
                //Adicionando todos vestígios na lista com os atributos desejados
                lista.add(new Vestigio(numId,
                        vestigiosArray.getJSONObject(i).getString("informacoesAdicionais"),
                        vestigiosArray.getJSONObject(i).getString("etiqueta"),
                        vestigiosArray.getJSONObject(i).getJSONObject("tipo").getString("nomeVestigio"),
                        vestigiosArray.getJSONObject(i).getString("_id")));
                numId++;
            }

            //Setando a lista static de vestígios
            StaticProperties.setListaVestigios(lista);

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return null;

        }

    }


}