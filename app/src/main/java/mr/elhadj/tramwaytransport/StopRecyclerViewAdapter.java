package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StopRecyclerViewAdapter extends RecyclerView.Adapter<StopRecyclerViewAdapter.StopViewHolder>{

    private ArrayList<Stop> myStopArrayList;
    private StopRecyclerViewAdapter.OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(StopRecyclerViewAdapter.OnItemClickListener listener){
        myListener = listener;
    }

    public static class StopViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_idStopRv,tv_stopNameRv;
        public ImageView iv_deleteStopRv,iv_editStopRv;
        public StopViewHolder(@NonNull View itemView, final StopRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_idStopRv = itemView.findViewById(R.id.tv_idStopRv);
            tv_stopNameRv = itemView.findViewById(R.id.tv_stopNameRv);
            iv_editStopRv = itemView.findViewById(R.id.iv_editStopRv);
            iv_deleteStopRv = itemView.findViewById(R.id.iv_deleteStopRv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            iv_editStopRv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            iv_deleteStopRv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            }


    }
    public StopRecyclerViewAdapter(ArrayList<Stop> stopArrayList){
        myStopArrayList = stopArrayList;
    }
    @NonNull
    @Override
    public StopRecyclerViewAdapter.StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_stopitem,parent,false);
        StopRecyclerViewAdapter.StopViewHolder svh = new StopRecyclerViewAdapter.StopViewHolder(v, myListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {
        Stop currentItem = myStopArrayList.get(position);
        holder.tv_idStopRv.setText(currentItem.getId());
        holder.tv_stopNameRv.setText(currentItem.getStop_name());
        holder.iv_editStopRv.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.iv_deleteStopRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return myStopArrayList.size();
    }
}
