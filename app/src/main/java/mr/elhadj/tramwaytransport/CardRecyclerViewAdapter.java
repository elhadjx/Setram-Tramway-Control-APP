package mr.elhadj.tramwaytransport;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.CardViewHolder> {

    private ArrayList<Card> myCardArrayList;
    private CardRecyclerViewAdapter.OnItemClickListener myListener;
    public static DatabaseHelper databaseHelper;
    String valid,expired,nulldates,blacklisted,invalid;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(CardRecyclerViewAdapter.OnItemClickListener listener){
        myListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_idCardRv, tv_cardNumberRv, tv_cardStatus, tv_cardExpRv;
        public ImageView iv_deleteCardRv,iv_editCardRv;

        public CardViewHolder(@NonNull View itemView, final CardRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            databaseHelper = new DatabaseHelper(itemView.getContext());
            tv_idCardRv = itemView.findViewById(R.id.tv_idCardRv);
            tv_cardNumberRv = itemView.findViewById(R.id.tv_cardNumberRv);
            tv_cardExpRv = itemView.findViewById(R.id.tv_cardExpRv);
            tv_cardStatus = itemView.findViewById(R.id.tv_cardStatus);
            iv_editCardRv = itemView.findViewById(R.id.iv_editCardRv);
            iv_deleteCardRv = itemView.findViewById(R.id.iv_deleteCardRv);



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
            iv_editCardRv.setOnClickListener(new View.OnClickListener() {
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

            iv_deleteCardRv.setOnClickListener(new View.OnClickListener() {
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

    public CardRecyclerViewAdapter(ArrayList<Card> cardArrayList){
        myCardArrayList = cardArrayList;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carditem,parent,false);
        CardViewHolder cvh = new CardViewHolder(v, myListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card currentItem = myCardArrayList.get(position);

        valid = holder.itemView.getContext().getString(R.string.valid);
        invalid = holder.itemView.getContext().getString(R.string.invalid);
        nulldates = holder.itemView.getContext().getString(R.string.nulldates);
        blacklisted = holder.itemView.getContext().getString(R.string.blacklisted);
        expired = holder.itemView.getContext().getString(R.string.expired);

        holder.tv_idCardRv.setText(String.valueOf(currentItem.getId()));
        holder.tv_cardNumberRv.setText(currentItem.getCardNumber());
        holder.tv_cardExpRv.setText(currentItem.getExpDate());
        holder.tv_cardStatus.setText(card_Status(currentItem.getCardNumber()));
        if (isDate(currentItem.getExpDate())){
            try {
                holder.tv_cardStatus.setTextColor(status_color(card_Status(currentItem.getCardNumber())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.tv_cardStatus.setText(R.string.wrong_date_format);
            holder.tv_cardStatus.setTextColor(Color.RED);
        }
        holder.iv_editCardRv.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.iv_deleteCardRv.setImageResource(R.drawable.ic_baseline_delete_24);
    }

    @Override
    public int getItemCount() {
        return myCardArrayList.size();
    }

    public Boolean isDate(String dateString){
        String charTwo = String.valueOf(dateString.charAt(2));
        String charFive = String.valueOf(dateString.charAt(5));
        if ((dateString.length() == 10) && (charTwo.matches("-")) && (charFive.matches("-")) && (Integer.parseInt(dateString.substring(0,2)) < 32) && (Integer.parseInt(dateString.substring(3,5)) < 13)){
            return true;
        }
        else return false;
    }

    public int status_color(String status){
        if (status.matches(valid)) return Color.parseColor("#00B2AF");
        else if (status.matches(expired)) return Color.RED;
        else if (status.matches(blacklisted)) return Color.RED;
        else return Color.BLACK;
    }

    public String card_Status(String card_number){

        String toReturn = "Nothing to Return";
        ArrayList<String> cardsNumber = databaseHelper.getData(databaseHelper.getCardTable(),"cardNumber");
        ArrayList<String> cardsBL = databaseHelper.getData(databaseHelper.getCardTable(),"isBlacklisted");
        ArrayList<String> expDates = databaseHelper.getData(databaseHelper.getCardTable(),"ExpDate");
        if (cardsNumber.contains(card_number)) {
            int id = cardsNumber.indexOf(card_number);
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String sdf = df.format(todayDate);
            Date tdd = null;
            Date expd = null;

            try {
                tdd = df.parse(sdf);
                expd = df.parse(expDates.get(id));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (cardsBL.get(id).matches("0")) {
                if (tdd.before(expd)) {
                    toReturn = valid;
                } else if (tdd.after(expd)) {
                    toReturn = expired;
                } else if ((tdd == null) || (expd == null)) {
                    toReturn = nulldates;
                }

            } else {
                toReturn = blacklisted;
            }
        } else {
            toReturn = invalid;
        }
        return toReturn;
    }



}
