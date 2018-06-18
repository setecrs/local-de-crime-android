package ages181.policiafederal_android;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

public class TelaListaVestigios extends Fragment {
    RecyclerView rv;
    VestigioAdapter va;
    FloatingActionButton fabCriarVestigio;
    int posicaoClicada;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_tela_listar_vestigios, container, false);

        sendMessage();

        fabCriarVestigio = v.findViewById(R.id.fabCriarVestigio);
        rv = v.findViewById(R.id.rvVestigios);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        va = new VestigioAdapter(this.getContext(), StaticProperties.getListaVestigios());
        rv.setAdapter(va);
        configuraBotaoAdiciona(v);
        va.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) throws JSONException {
                System.out.println("Posição item clicado: "+position);
                posicaoClicada = position;
                pegarObjeto();
                JSONObject json = StaticProperties.getVestigioClicado();
                Intent i = new Intent(getActivity(), TelaAdicionarVestigio.class);
                i.putExtra("coletado",json.getString("coletado"));
                i.putExtra("etiqueta", json.getString("etiqueta"));
                i.putExtra("tipo",json.getJSONObject("tipo").getString("tipoVestigio"));
                i.putExtra("nome", json.getJSONObject("tipo").getString("nomeVestigio"));
                i.putExtra("infoAdicional",json.getString("informacoesAdicionais"));
                startActivity(i);
            }
        });
        return v;
    }
    public void pegarObjeto() throws JSONException {
        for (int i = 0; i < StaticProperties.getJsonArrayVestigios().length(); i++) {
            if(StaticProperties.getListaVestigios().get(posicaoClicada).getIdBanco()==StaticProperties.getJsonArrayVestigios().getJSONObject(i).getString("_id")){
                StaticProperties.setVestigioClicado(StaticProperties.getJsonArrayVestigios().getJSONObject(i));
            }
        }
    }

    public void sendMessage() {
        try {
            HttpVestigios t = new HttpVestigios();
            t.execute().get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void configuraBotaoAdiciona(View v) {
        if(fabCriarVestigio != null) {
            fabCriarVestigio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), TelaAdicionarVestigio.class));
                }
            });
        }
    }

    public static TelaListaVestigios newInstance() {
        TelaListaVestigios f = new TelaListaVestigios();
        return f;
    }
}
