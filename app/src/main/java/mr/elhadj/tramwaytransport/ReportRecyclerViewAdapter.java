package mr.elhadj.tramwaytransport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ReportViewHolder>{

    private ArrayList<Report> myReportArrayList;
    private ReportRecyclerViewAdapter.OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(ReportRecyclerViewAdapter.OnItemClickListener listener){
        myListener = listener;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_idReportRv,tv_reportTitleRv;
        public ImageView iv_deleteReportRv;
        public ReportViewHolder(@NonNull View itemView, final ReportRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_idReportRv = itemView.findViewById(R.id.tv_idReportRv);
            tv_reportTitleRv = itemView.findViewById(R.id.tv_reportTitleRv);
            iv_deleteReportRv = itemView.findViewById(R.id.iv_deleteReportRv);
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

            iv_deleteReportRv.setOnClickListener(new View.OnClickListener() {
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
    public ReportRecyclerViewAdapter(ArrayList<Report> ReportArrayList){
        myReportArrayList = ReportArrayList;
    }
    @NonNull
    @Override
    public ReportRecyclerViewAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_reportitem,parent,false);
        ReportRecyclerViewAdapter.ReportViewHolder rvh = new ReportRecyclerViewAdapter.ReportViewHolder(v, myListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportRecyclerViewAdapter.ReportViewHolder holder, int position) {
        Report currentItem = myReportArrayList.get(position);
        holder.tv_idReportRv.setText(currentItem.getId());
        holder.tv_reportTitleRv.setText(currentItem.getReportTitle());
        holder.iv_deleteReportRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return myReportArrayList.size();
    }
}
