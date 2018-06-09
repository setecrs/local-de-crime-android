package ages181.policiafederal_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TelaListarOcorrencias extends AppCompatActivity {
    RecyclerView rv;
    OcorrenciaAdapter oa;
    static int posicaoClicada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_ocorrencias);
        sendMessage();
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        oa = new OcorrenciaAdapter(this, ListaOcorrencia.getLista());
        rv.setAdapter(oa);
        oa.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) throws JSONException {
                System.out.println("Posição item clicado: "+position);
                Toast.makeText(TelaListarOcorrencias.this, "Posição clicada: "+
                        position, Toast.LENGTH_LONG).show();
                posicaoClicada = position;
                pegarObjeto();
                Intent i = new Intent (TelaListarOcorrencias.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void pegarObjeto() throws JSONException {
        for (int i = 0; i < StaticJson.getJsinho().length(); i++) {
            if(ListaOcorrencia.getLista().get(posicaoClicada).getId()==StaticJson.getJsinho().getJSONObject(i).getString("_id")){
                System.out.println("ACHEEEEI: "+ListaOcorrencia.getLista().get(posicaoClicada).getId());
                CarregarOcorrencia.carregaOcorrencia(StaticJson.getJsinho().getJSONObject(i));
            }
        }
    }

    public void sendMessage() {
        try {
            HttpOcorrencias t = new HttpOcorrencias();
            t.execute().get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

