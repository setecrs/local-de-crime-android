package ages181.policiafederal_android;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TelaListarOcorrencias extends AppCompatActivity {
    RecyclerView rv;
    OcorrenciaAdapter oa;
    static int posicaoClicada;
    FloatingActionButton fabCriarOcorrencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar_ocorrencias);
        fabCriarOcorrencia = findViewById(R.id.buttonCriarOcorrencia);
        rv = (RecyclerView) findViewById(R.id.recyclerView);

        sendMessage();
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if(StaticProperties.getListaOcorrencias()!=null) {
            oa = new OcorrenciaAdapter(this, StaticProperties.getListaOcorrencias());
            rv.setAdapter(oa);
        }
        else {
            StaticProperties.setListaOcorrencias(new ArrayList<Ocorrencia>());
            oa = new OcorrenciaAdapter(this, StaticProperties.getListaOcorrencias());
            rv.setAdapter(oa);
        }

        fabCriarOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpNovaOcorrencia hno = new HttpNovaOcorrencia();
                hno.execute();
                Intent i = new Intent (TelaListarOcorrencias.this, MainActivity.class);
                startActivity(i);
            }
        });

        oa.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) throws JSONException {
                System.out.println("Posição item clicado: "+position);
                posicaoClicada = position;
                pegarObjeto();
                Intent i = new Intent (TelaListarOcorrencias.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void pegarObjeto() throws JSONException {
        for (int i = 0; i < StaticProperties.getJsonArrayOcorrencias().length(); i++) {
            if(StaticProperties.getListaOcorrencias().get(posicaoClicada).getId()==StaticProperties.getJsonArrayOcorrencias().getJSONObject(i).getString("_id")){
                StaticProperties.setId(StaticProperties.getJsonArrayOcorrencias().getJSONObject(i).getString("_id"));
                CarregarOcorrencia.carregaOcorrencia(StaticProperties.getJsonArrayOcorrencias().getJSONObject(i));
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
