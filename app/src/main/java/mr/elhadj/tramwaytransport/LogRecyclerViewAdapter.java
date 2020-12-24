package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogRecyclerViewAdapter extends RecyclerView.Adapter<LogRecyclerViewAdapter.LogViewHolder> {

    private ArrayList<Log> myLogArrayList;
    private OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_idLogRv, tv_logRv;

        public LogViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_idLogRv = itemView.findViewById(R.id.tv_idLogRv);
            tv_logRv = itemView.findViewById(R.id.tv_logRv);

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
        }
    }

    public LogRecyclerViewAdapter(ArrayList<Log> LogArrayList){
        myLogArrayList = LogArrayList;
    }
    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_logitem,parent,false);
        LogViewHolder lvh = new LogViewHolder(v, myListener);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        Log currentItem = myLogArrayList.get(position);
        String toDisplay = "";
        if (currentItem.getAct().toLowerCase().matches("located")){
             toDisplay = "Agent " + currentItem.getAgent_id() + " located"
                     + " stop " + currentItem.getStop_id()
                     + " vehicle " + currentItem.getVehicle_id()
                     + " dest " + currentItem.getDestination_id();

        } else {
             toDisplay = "Agent " + currentItem.getAgent_id() + " "
                     + currentItem.getAct().toLowerCase()
                    + " card " + currentItem.getCard_id()
                    + " stop " + currentItem.getStop_id()
                    + " vehicle " + currentItem.getVehicle_id()
                     + " dest " + currentItem.getDestination_id();
        }
        holder.tv_idLogRv.setText(currentItem.getId());
        holder.tv_logRv.setText(toDisplay);
    }

    @Override
    public int getItemCount() {
        return myLogArrayList.size();
    }


}

