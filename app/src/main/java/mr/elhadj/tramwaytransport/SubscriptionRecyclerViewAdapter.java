package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubscriptionRecyclerViewAdapter extends RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.SubscriptionViewHolder>{

    private ArrayList<Subscription> mySubscriptionArrayList;
    private SubscriptionRecyclerViewAdapter.OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(SubscriptionRecyclerViewAdapter.OnItemClickListener listener){
        myListener = listener;
    }

    public static class SubscriptionViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_idSubscriptionRv,tv_subscriptionNameRv, tv_subscriptionDelayRv;
        public ImageView iv_deleteSubscriptionRv,iv_editSubscriptionRv;
        public SubscriptionViewHolder(@NonNull View itemView, final SubscriptionRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_idSubscriptionRv = itemView.findViewById(R.id.tv_idSubscriptionRv);
            tv_subscriptionNameRv = itemView.findViewById(R.id.tv_subscriptionNameRv);
            tv_subscriptionDelayRv = itemView.findViewById(R.id.tv_subscriptionDelayRv);
            iv_editSubscriptionRv = itemView.findViewById(R.id.iv_editSubscriptionRv);
            iv_deleteSubscriptionRv = itemView.findViewById(R.id.iv_deleteSubscriptionRv);
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
            iv_editSubscriptionRv.setOnClickListener(new View.OnClickListener() {
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

            iv_deleteSubscriptionRv.setOnClickListener(new View.OnClickListener() {
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
    public SubscriptionRecyclerViewAdapter(ArrayList<Subscription> SubscriptionArrayList){
        mySubscriptionArrayList = SubscriptionArrayList;
    }
    @NonNull
    @Override
    public SubscriptionRecyclerViewAdapter.SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_subscriptionitem,parent,false);
        SubscriptionRecyclerViewAdapter.SubscriptionViewHolder subscriptionViewHolder = new SubscriptionRecyclerViewAdapter.SubscriptionViewHolder(v, myListener);
        return subscriptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        Subscription currentItem = mySubscriptionArrayList.get(position);
        holder.tv_idSubscriptionRv.setText(currentItem.getId());
        holder.tv_subscriptionNameRv.setText(currentItem.getSubscription_name());
        holder.tv_subscriptionDelayRv.setText(currentItem.getSubscription_delay());
        holder.iv_editSubscriptionRv.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.iv_deleteSubscriptionRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return mySubscriptionArrayList.size();
    }
}
