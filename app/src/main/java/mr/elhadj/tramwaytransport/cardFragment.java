package mr.elhadj.tramwaytransport;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class cardFragment extends Fragment {

    Button btn_Scan, btn_Blacklist, btn_Whitelist;
    TextView tv_cardStatus, tv_cardData;
    ImageView nfc_iv;
    DatabaseHelper databaseHelper;
    int Agid;
    String AgentiId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public cardFragment() {
        // Required empty public constructor
    }
    public static cardFragment newInstance(String param1, String param2) {
        cardFragment fragment = new cardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        databaseHelper = new DatabaseHelper(this.getContext());

        ((MainActivity) getActivity()).getLoggedInId();

        btn_Scan = v.findViewById(R.id.btn_Scan);

        btn_Blacklist = v.findViewById(R.id.btn_blacklist);
        btn_Whitelist = v.findViewById(R.id.btn_whitelist);
        tv_cardStatus = v.findViewById(R.id.tv_cardStatus);
        tv_cardData = v.findViewById(R.id.tv_cardData);
        nfc_iv = v.findViewById(R.id.nfc_iv);

        btn_Whitelist.setVisibility(View.GONE);
        btn_Blacklist.setVisibility(View.GONE);
        btn_Scan.setVisibility(View.GONE);
        nfc_iv.setVisibility(View.VISIBLE);

        Agid = ((MainActivity) getActivity()).getLoggedInId();
        AgentiId = Integer.toString(Agid);


        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perform_Scan();
            }
        });


        btn_Blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = ((MainActivity) getActivity()).getTagID();
                ArrayList<String> cardsNumber = databaseHelper.getData(databaseHelper.getCardTable(),"cardNumber");
                ArrayList<String> cardsBL = databaseHelper.getData(databaseHelper.getCardTable(),"isBlacklisted");
                tv_cardStatus.setText(getString(R.string.ready_to_scan));
                tv_cardStatus.setTextColor(Color.BLACK);
                nfc_iv.setVisibility(View.VISIBLE);
                if (!cardNumber.isEmpty()){
                    if (cardsNumber.contains(cardNumber)) {
                        int id = cardsNumber.indexOf(cardNumber);
                        if (cardsBL.get(id).matches("0")) {
                            if (databaseHelper.blacklistCard(cardNumber)) {
                                Toast.makeText(getContext(), cardNumber + getString(R.string.blacklisted_success), Toast.LENGTH_SHORT).show();
                                tv_cardData.setText("");
                                ArrayList<String> cardId = databaseHelper.getData(databaseHelper.getCardTable(), "id");
                                String SelectedVehicleID = "Null";
                                String SelectedStopID = "Null";
                                String SelectedDestinationID = "Null";
                                if (((MainActivity) getActivity()).getSelectedVehicle() != null ){
                                    SelectedVehicleID = ((MainActivity) getActivity()).getSelectedVehicle().getId();
                                }
                                if (((MainActivity) getActivity()).getSelectedStop() != null ) {
                                    SelectedStopID = ((MainActivity) getActivity()).getSelectedStop().getId();
                                }
                                if (((MainActivity) getActivity()).getSelectedDestination() != null ) {
                                    SelectedDestinationID = ((MainActivity) getActivity()).getSelectedDestination().getId();
                                }
                                Log log = new Log(AgentiId,cardId.get(id),SelectedVehicleID,SelectedStopID,SelectedDestinationID,"Blacklisted");
                                databaseHelper.insertLog(log);

                            } else {
                                Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), cardNumber + getString(R.string.already_in_blacklist_id_two_dots) + id, Toast.LENGTH_SHORT).show();
                            tv_cardData.setText("");
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.card_not_in_db, Toast.LENGTH_SHORT).show();
                        tv_cardData.setText("");

                    }
                } else {
                    Toast.makeText(getContext(), R.string.cn_empty_plz_write_cn,Toast.LENGTH_SHORT).show();
                }
                tv_cardData.setText("");
                btn_Blacklist.setVisibility(View.GONE);
            }
        });

        btn_Whitelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = ((MainActivity) getActivity()).getTagID();
                ArrayList<String> cardsNumber = databaseHelper.getData(databaseHelper.getCardTable(),"cardNumber");
                ArrayList<String> cardsBL = databaseHelper.getData(databaseHelper.getCardTable(),"isBlacklisted");
                tv_cardStatus.setText(R.string.ready_to_scan);
                tv_cardStatus.setTextColor(Color.BLACK);
                nfc_iv.setVisibility(View.VISIBLE);
                int id = cardsNumber.indexOf(cardNumber);
                if (!cardNumber.isEmpty()){
                    if (cardsNumber.contains(cardNumber)) {
                        if (cardsBL.get(id).matches("1")) {
                            databaseHelper.whitelistCard(cardNumber);
                            Toast.makeText(getContext(), cardNumber + getString(R.string.whitelisted_success), Toast.LENGTH_SHORT).show();
                            ArrayList<String> cardId = databaseHelper.getData(databaseHelper.getCardTable(), "id");
                            String SelectedVehicleID = "Null";
                            String SelectedStopID = "Null";
                            String SelectedDestinationID = "Null";
                            if (((MainActivity) getActivity()).getSelectedVehicle() != null ){
                                SelectedVehicleID = ((MainActivity) getActivity()).getSelectedVehicle().getId();
                            }
                            if (((MainActivity) getActivity()).getSelectedStop() != null ) {
                                SelectedStopID = ((MainActivity) getActivity()).getSelectedStop().getId();
                            }
                            if (((MainActivity) getActivity()).getSelectedDestination() != null ) {
                                SelectedDestinationID = ((MainActivity) getActivity()).getSelectedDestination().getId();
                            }
                            Log log = new Log(AgentiId,cardId.get(id),SelectedVehicleID,SelectedStopID,SelectedDestinationID,"Whitelisted");
                            databaseHelper.insertLog(log);
                        } else {
                            Toast.makeText(getContext(), cardNumber + getString(R.string.already_in_whitelist_id_two_dots) + Integer.toString(id + 1), Toast.LENGTH_SHORT).show();

                            tv_cardData.setText("");
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.card_not_in_db, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.card_number_empty,Toast.LENGTH_SHORT).show();
                }
                tv_cardData.setText("");
                btn_Whitelist.setVisibility(View.GONE);
            }
        });

        return v;
    }
    public void perform_Scan(){
        String cardNumber = ((MainActivity) getActivity()).getTagID();
        ArrayList<String> cardId = databaseHelper.getData(databaseHelper.getCardTable(), "id");
        ArrayList<String> cardsNumber = databaseHelper.getData(databaseHelper.getCardTable(),"cardNumber");
        ArrayList<String> expDates = databaseHelper.getData(databaseHelper.getCardTable(),"ExpDate");
        ArrayList<String> subscriptions = databaseHelper.getData(databaseHelper.getCardTable(),"subscription");

        if (!cardNumber.isEmpty()) {
            nfc_iv.setVisibility(View.GONE);
            tv_cardStatus.setText(card_Status(cardNumber));
            tv_cardStatus.setTextColor(status_color(card_Status(cardNumber)));
            String blackListed = getString(R.string.no);
            if (card_Status(cardNumber).matches(getString(R.string.blacklisted))){
                blackListed = getString(R.string.yes);
                btn_Blacklist.setVisibility(View.GONE);
                btn_Whitelist.setVisibility(View.VISIBLE);
            } else {
                blackListed = getString(R.string.no);
                btn_Blacklist.setVisibility(View.VISIBLE);
                btn_Whitelist.setVisibility(View.GONE);
            }

            if (cardsNumber.contains(cardNumber)){
                int id = cardsNumber.indexOf(cardNumber);
                tv_cardData.setText(getString(R.string.card_id_two_dots)+ cardId.get(id) + "\n"
                        + getString(R.string.card_number_two_dots) + cardsNumber.get(id) + "\n"
                        + getString(R.string.blacklisted_two_dots) + blackListed + "\n"
                        + getString(R.string.expires_on_two_dots) + expDates.get(id) + "\n"
                        + getString(R.string.sub_two_dots) + subscriptions.get(id));
                String SelectedVehicleID;
                String SelectedStopID;
                String SelectedDestinationID;
                try{
                    SelectedVehicleID = ((MainActivity) getActivity()).getSelectedVehicle().getId();
                } catch (Exception e){
                    SelectedVehicleID = "Null";
                }
                try{
                    SelectedStopID = ((MainActivity) getActivity()).getSelectedStop().getId();
                } catch (Exception e) {
                    SelectedStopID = "Null";
                }
                try{
                    SelectedDestinationID = ((MainActivity) getActivity()).getSelectedDestination().getId();
                } catch (Exception e){
                    SelectedDestinationID = "Null";
                }
                Log log = new Log(AgentiId,cardId.get(id),SelectedVehicleID,SelectedStopID,SelectedDestinationID,"Scanned");
                databaseHelper.insertLog(log);
            } else {
                tv_cardData.setText(R.string.card_not_in_db);
            }

        } else {
            Toast.makeText(getContext(), R.string.no_card_data_detected,Toast.LENGTH_SHORT).show();
        }
    }

    public int status_color(String status){
        if (status.matches(getString(R.string.valid))) return Color.parseColor("#00B2AF");
        else if (status.matches(getString(R.string.expired))) return Color.RED;
        else if (status.matches(getString(R.string.blacklisted))) return Color.RED;
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
                    toReturn = getString(R.string.valid);
                } else if (tdd.after(expd)) {
                    toReturn = getString(R.string.expired);
                } else if ((tdd == null) || (expd == null)) {
                    toReturn = "Null Dates";
                }

            } else {
                toReturn = getString(R.string.blacklisted);
            }
        } else {
            toReturn = getString(R.string.invalid);
        }
        return toReturn;
    }
}