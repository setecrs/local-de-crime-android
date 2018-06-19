package ages181.policiafederal_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

public class OcorrenciaAdapter extends RecyclerView.Adapter<OcorrenciaAdapter.OcorrenciaViewHolder> {

    private Context context;
    private List<Ocorrencia> listaOcorrencia;
    private static ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public OcorrenciaAdapter(Context context, List<Ocorrencia> listaOcorrencia) {
        this.context = context;
        this.listaOcorrencia = listaOcorrencia;
    }

    @NonNull
    @Override
    public OcorrenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(R.layout.list_layout, null);

        return new OcorrenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OcorrenciaViewHolder holder, int position) {
        Ocorrencia oc = listaOcorrencia.get(position);
        holder.tvId.setText("Id: "+oc.getId().substring(oc.getId().length()-5, oc.getId().length()));
        holder.tvNumOcorrencia.setText("Número Ocorrência: "+oc.getNumeroOcorrencia());
        holder.tvDataAc.setText("Data e Hora Acionamento: "+oc.formatarDataHora());
        holder.tvTipoLocal.setText("Tipo Local: "+oc.getTipoLocal());
    }



    @Override
    public int getItemCount() {
        return listaOcorrencia.size();
    }


    class OcorrenciaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvId, tvDataAc, tvNumOcorrencia, tvTipoLocal;

        public OcorrenciaViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.idOcorrencia);
            tvDataAc = (TextView) itemView.findViewById(R.id.tvDataHoraAcionamento);
            tvNumOcorrencia = (TextView) itemView.findViewById(R.id.numOcorrencia);
            tvTipoLocal = (TextView) itemView.findViewById(R.id.tvTipoLocal);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                try {
                    itemClickListener.onItemClick(getAdapterPosition());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

