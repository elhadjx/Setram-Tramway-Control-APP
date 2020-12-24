package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter<VehicleRecyclerViewAdapter.VehicleViewHolder>{

    private ArrayList<Vehicle> myVehicleArrayList;
    private VehicleRecyclerViewAdapter.OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(VehicleRecyclerViewAdapter.OnItemClickListener listener){
        myListener = listener;
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_vehicleCodeRv,tv_idVehicleRv;
        public ImageView iv_deleteVehicleRv,iv_editVehicleRv;
        public VehicleViewHolder(@NonNull View itemView, final VehicleRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_vehicleCodeRv = itemView.findViewById(R.id.tv_vehicleCodeRv);
            tv_idVehicleRv = itemView.findViewById(R.id.tv_idVehicleRv);
            iv_editVehicleRv = itemView.findViewById(R.id.iv_editVehicleRv);
            iv_deleteVehicleRv = itemView.findViewById(R.id.iv_deleteVehicleRv);
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
            iv_editVehicleRv.setOnClickListener(new View.OnClickListener() {
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

            iv_deleteVehicleRv.setOnClickListener(new View.OnClickListener() {
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
    public VehicleRecyclerViewAdapter(ArrayList<Vehicle> VehicleArrayList){
        myVehicleArrayList = VehicleArrayList;
    }
    @NonNull
    @Override
    public VehicleRecyclerViewAdapter.VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_vehicleitem,parent,false);
        VehicleRecyclerViewAdapter.VehicleViewHolder vvh = new VehicleRecyclerViewAdapter.VehicleViewHolder(v, myListener);
        return vvh;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle currentItem = myVehicleArrayList.get(position);

        holder.tv_idVehicleRv.setText(currentItem.getId());
        holder.tv_vehicleCodeRv.setText(currentItem.getVehicle_code());
        holder.iv_editVehicleRv.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.iv_deleteVehicleRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return myVehicleArrayList.size();
    }
}