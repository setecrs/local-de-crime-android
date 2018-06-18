package ages181.policiafederal_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

public class VestigioAdapter extends RecyclerView.Adapter<VestigioAdapter.VestigioViewHolder> {

    private Context context;
    private List<Vestigio> listaVestigio;
    private static ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public VestigioAdapter(Context context, List<Vestigio> listaVestigio) {
        this.context = context;
        this.listaVestigio = listaVestigio;
    }


    @Override
    public VestigioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_vestigios, null);
        return new VestigioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VestigioViewHolder holder, int position) {
        Vestigio oc = listaVestigio.get(position);
        holder.tvTipoVestigio.setText(oc.getNumId()+" - "+oc.getEtiqueta()+"("+oc.getNomeVestigio()+")");
        holder.tvInformacoesAdicionais.setText(oc.getInformacoesAdicionais());
    }


    @Override
    public int getItemCount() {
        return listaVestigio.size();
    }


    class VestigioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTipoVestigio, tvInformacoesAdicionais;

        public VestigioViewHolder(View itemView) {
            super(itemView);

            tvTipoVestigio = itemView.findViewById(R.id.tvTipoVestigio);
            tvInformacoesAdicionais = itemView.findViewById(R.id.tvInformacaoAdicional);
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
