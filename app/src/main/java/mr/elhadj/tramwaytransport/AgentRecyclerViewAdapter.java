package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AgentRecyclerViewAdapter extends RecyclerView.Adapter<AgentRecyclerViewAdapter.AgentViewHolder> {

    private ArrayList<Agent> myAgentArrayList;
    private OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    public static class AgentViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_idRv, tv_agentUsernameRv, tv_agentFullnameRv, tv_agentPasswordRv;
        public ImageView iv_deleteRv,iv_editRv;

        public AgentViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_idRv = itemView.findViewById(R.id.tv_idRv);
            tv_agentUsernameRv = itemView.findViewById(R.id.tv_agentUsernameRv);
            tv_agentFullnameRv = itemView.findViewById(R.id.tv_agentFullnameRv);
            tv_agentPasswordRv = itemView.findViewById(R.id.tv_agentPasswordRv);
            iv_editRv = itemView.findViewById(R.id.iv_editRv);
            iv_deleteRv = itemView.findViewById(R.id.iv_deleteRv);

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

            iv_editRv.setOnClickListener(new View.OnClickListener() {
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

            iv_deleteRv.setOnClickListener(new View.OnClickListener() {
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

    public AgentRecyclerViewAdapter(ArrayList<Agent> agentArrayList){
        myAgentArrayList = agentArrayList;
    }
    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_agentitem,parent,false);
        AgentViewHolder avh = new AgentViewHolder(v, myListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position) {
        Agent currentItem = myAgentArrayList.get(position);

        holder.tv_idRv.setText(String.valueOf(currentItem.getId()));
        holder.tv_agentUsernameRv.setText(currentItem.getAg_username());
        holder.tv_agentFullnameRv.setText(currentItem.getAg_fullname());
        holder.tv_agentPasswordRv.setText(currentItem.getAg_password());
        holder.iv_editRv.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.iv_deleteRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return myAgentArrayList.size();
    }


}
