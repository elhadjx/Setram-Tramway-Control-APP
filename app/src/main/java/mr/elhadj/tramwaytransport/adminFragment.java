package mr.elhadj.tramwaytransport;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class adminFragment extends Fragment {
    EditText et_userToAdd, et_passToAdd, et_fullNameToAdd, et_cardNumber, et_vehicleCode, et_stopName, et_subscriptionName, et_subscriptionDelay;
    Button btn_addUser, btn_addCard, btn_updateCard, btn_updateUser, btn_addVehicle, btn_updateVehicle, btn_addStop, btn_updateStop, btn_addSubscription, btn_updateSubscription;
    TextInputLayout et_cardNumberSpace;
    //Button btn_gotoCardsDB, btn_gotoAgentsDB, btn_gotoVehiclesDB, btn_gotoStopsDB, btn_gotoSubscriptionsDB, btn_gotoLogsDB;
    RecyclerView recyclerView;
    AgentRecyclerViewAdapter agentAdapter;
    CardRecyclerViewAdapter cardAdapter;
    VehicleRecyclerViewAdapter vehicleAdapter;
    StopRecyclerViewAdapter stopAdapter;
    SubscriptionRecyclerViewAdapter subscriptionAdapter;
    LogRecyclerViewAdapter logAdapter;
    ReportRecyclerViewAdapter reportAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    DatabaseHelper databaseHelper;
    ArrayList<Card> cardArrayList;
    ArrayList<Agent> agentArrayList;
    ArrayList<Vehicle> vehicleArrayList;
    ArrayList<Stop> stopArrayList;
    ArrayList<Subscription> subscriptionArrayList;
    ArrayList<Log> logArrayList;
    ArrayList<Report> reportArrayList;
    ConstraintLayout agentGroupLayout, cardGroupLayout, vehicleGroupLayout, stopGroupLayout, subscriptionGroupLayout;
    LinearLayout linearLayoutRv;
    Switch switchBlacklist;
    Spinner spinnerDate;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    AppBarLayout appbar;
    NavigationView adminMenuView;
    TextView tv_swipe;
    String spinnerSubscriptionName;
    int currentPosition;
    int spinnerSubscriptionPosition;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public static adminFragment newInstance(String param1, String param2) {
        adminFragment fragment = new adminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public adminFragment() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        databaseHelper = new DatabaseHelper(this.getContext());

        et_userToAdd = v.findViewById(R.id.et_userToAdd);
        et_passToAdd = v.findViewById(R.id.et_passToAdd);
        et_fullNameToAdd = v.findViewById(R.id.et_fullnameToAdd);
        et_cardNumber = v.findViewById(R.id.et_cardNumber);
        et_cardNumberSpace = v.findViewById(R.id.et_cardNumberSpace);
        //et_expDate = v.findViewById(R.id.et_expDate);
        et_vehicleCode = v.findViewById(R.id.et_vehicleCode);
        et_stopName = v.findViewById(R.id.et_stopName);
        et_subscriptionName = v.findViewById(R.id.et_subscriptionName);
        et_subscriptionDelay = v.findViewById(R.id.et_subscriptionDelay);
        tv_swipe= v.findViewById(R.id.tv_swipe);
        btn_addUser = v.findViewById(R.id.btn_addUser);
        btn_addCard = v.findViewById(R.id.btn_addCard);
        btn_addVehicle = v.findViewById(R.id.btn_addVehicle);
        btn_addStop = v.findViewById(R.id.btn_addStop);
        btn_addSubscription = v.findViewById(R.id.btn_addSubscription);
        btn_updateCard = v.findViewById(R.id.btn_updateCard);
        btn_updateCard.setVisibility(View.GONE);
        btn_updateUser = v.findViewById(R.id.btn_updateUser);
        btn_updateUser.setVisibility(View.GONE);
        btn_updateVehicle = v.findViewById(R.id.btn_updateVehicle);
        btn_updateVehicle.setVisibility(View.GONE);
        btn_updateStop = v.findViewById(R.id.btn_updateStop);
        btn_updateStop.setVisibility(View.GONE);
        btn_updateSubscription = v.findViewById(R.id.btn_updateSubscription);
        btn_updateSubscription.setVisibility(View.GONE);
        cardGroupLayout = v.findViewById(R.id.cardGroupLayout);
        cardGroupLayout.setVisibility(View.GONE);
        agentGroupLayout = v.findViewById(R.id.agentGroupLayout);
        agentGroupLayout.setVisibility(View.GONE);
        vehicleGroupLayout = v.findViewById(R.id.vehicleGroupLayout);
        vehicleGroupLayout.setVisibility(View.GONE);
        stopGroupLayout = v.findViewById(R.id.stopGroupLayout);
        stopGroupLayout.setVisibility(View.GONE);
        subscriptionGroupLayout = v.findViewById(R.id.subscriptionGroupLayout);
        subscriptionGroupLayout.setVisibility(View.GONE);
        switchBlacklist = v.findViewById(R.id.switchBlacklist);
        switchBlacklist.setVisibility(View.GONE);
        linearLayoutRv = v.findViewById(R.id.linearLayoutRv);
        appbar = v.findViewById(R.id.appbar);
        toolbar = v.findViewById(R.id.admin_toolbar);
        drawerLayout = v.findViewById(R.id.drawerLayout);
        adminMenuView = v.findViewById(R.id.admin_menu);
        spinnerDate = v.findViewById(R.id.spinnerDate);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.openMenuDrawer,R.string.closeMenuDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        //btn_blacklistCard = v.findViewById(R.id.btn_blacklistCard);
        //btn_whitelistCard = v.findViewById(R.id.btn_whitelistCard);
        //btn_showCard = v.findViewById(R.id.btn_showCard);
        recyclerView = v.findViewById(R.id.recyclerView);



        currentPosition = 0;
        spinnerSubscriptionPosition = -1;
        spinnerSubscriptionName = "";

        cardArrayList = new ArrayList<>();
        agentArrayList = new ArrayList<>();
        vehicleArrayList = new ArrayList<>();
        stopArrayList = new ArrayList<>();
        subscriptionArrayList = new ArrayList<>();
        logArrayList = new ArrayList<>();
        reportArrayList = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        updateArrays();

        agentAdapter = new AgentRecyclerViewAdapter(agentArrayList);
        cardAdapter = new CardRecyclerViewAdapter(cardArrayList);
        vehicleAdapter = new VehicleRecyclerViewAdapter(vehicleArrayList);
        stopAdapter = new StopRecyclerViewAdapter(stopArrayList);
        subscriptionAdapter = new SubscriptionRecyclerViewAdapter(subscriptionArrayList);
        logAdapter = new LogRecyclerViewAdapter(logArrayList);
        reportAdapter = new ReportRecyclerViewAdapter(reportArrayList);




        agentAdapterOnItemClickListener();

        cardAdapterOnItemClickListener();

        vehicleAdapterOnItemClickListener();

        stopAdapterOnItemClickListener();

        subscriptionAdapterOnItemClickListener();

        logAdapterOnItemClickListener();

        reportAdapterOnItemClickListener();


        //bedeltha b Side Menu
        /*
        btn_gotoCardsDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCardArray();
                recyclerView.setAdapter(cardAdapter);
                ShowGroupLayout(cardGroupLayout);
                cardAdapterOnItemClickListener();
                HideGoToButton(btn_gotoCardsDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,convertDpToPixel(250,getContext()));
                linearLayoutRv.setLayoutParams(params);
            }
        });

        btn_gotoAgentsDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAgentArray();
                recyclerView.setAdapter(agentAdapter);
                ShowGroupLayout(agentGroupLayout);
                agentAdapterOnItemClickListener();
                HideGoToButton(btn_gotoAgentsDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,convertDpToPixel(250,getContext()));
                linearLayoutRv.setLayoutParams(params);
            }
        });

        btn_gotoVehiclesDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVehicleArray();
                recyclerView.setAdapter(vehicleAdapter);
                ShowGroupLayout(vehicleGroupLayout);
                vehicleAdapterOnItemClickListener();
                HideGoToButton(btn_gotoVehiclesDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,convertDpToPixel(250,getContext()));
                linearLayoutRv.setLayoutParams(params);
            }
        });

        btn_gotoStopsDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStopArray();
                recyclerView.setAdapter(stopAdapter);
                ShowGroupLayout(stopGroupLayout);
                stopAdapterOnItemClickListener();
                HideGoToButton(btn_gotoStopsDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,convertDpToPixel(250,getContext()));
                linearLayoutRv.setLayoutParams(params);
            }
        });

        btn_gotoSubscriptionsDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSubscriptionArray();
                recyclerView.setAdapter(subscriptionAdapter);
                ShowGroupLayout(subscriptionGroupLayout);
                subscriptionAdapterOnItemClickListener();
                HideGoToButton(btn_gotoSubscriptionsDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,convertDpToPixel(250,getContext()));
                linearLayoutRv.setLayoutParams(params);
            }
        });

        btn_gotoLogsDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLogsArray();
                recyclerView.setAdapter(logAdapter);
                ShowGroupLayout(subscriptionGroupLayout);
                subscriptionGroupLayout.setVisibility(View.GONE);
                logAdapterOnItemClickListener();
                HideGoToButton(btn_gotoLogsDB);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                params.setMargins(0,0,0,0);
                linearLayoutRv.setLayoutParams(params);
            }
        });*/

        toolbar.setTitle("-> Swipe To Right ");
        adminMenuView.bringToFront();
        toolbar.bringToFront();
        appbar.bringToFront();

        adminMenuView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ViewGroup.MarginLayoutParams params;
                switch (item.getItemId()){
                    case R.id.menuItem_Cards:
                        updateSubscriptionArray();
                        ArrayAdapter<Subscription> spinnerAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, subscriptionArrayList);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDate.setAdapter(spinnerAdapter);
                        spinnerDate.setSelection(0);
                        tv_swipe.setVisibility(View.GONE);
                        updateCardArray();
                        recyclerView.setAdapter(cardAdapter);
                        ShowGroupLayout(cardGroupLayout);
                        cardAdapterOnItemClickListener();
                        spinnerOnItemSelectedListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,convertDpToPixel(250,getContext()));
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.cards_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Agents:
                        tv_swipe.setVisibility(View.GONE);
                        updateAgentArray();
                        recyclerView.setAdapter(agentAdapter);
                        ShowGroupLayout(agentGroupLayout);
                        agentAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,convertDpToPixel(250,getContext()));
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.agents_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Vehicles:
                        tv_swipe.setVisibility(View.GONE);
                        updateVehicleArray();
                        recyclerView.setAdapter(vehicleAdapter);
                        ShowGroupLayout(vehicleGroupLayout);
                        vehicleAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,convertDpToPixel(250,getContext()));
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.vehicles_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Stops:
                        tv_swipe.setVisibility(View.GONE);
                        updateStopArray();
                        recyclerView.setAdapter(stopAdapter);
                        ShowGroupLayout(stopGroupLayout);
                        stopAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,convertDpToPixel(250,getContext()));
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.stops_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Subscriptions:
                        tv_swipe.setVisibility(View.GONE);
                        updateSubscriptionArray();
                        recyclerView.setAdapter(subscriptionAdapter);
                        ShowGroupLayout(subscriptionGroupLayout);
                        subscriptionAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,convertDpToPixel(250,getContext()));
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.subscription_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Logs:
                        tv_swipe.setVisibility(View.GONE);
                        updateLogsArray();
                        recyclerView.setAdapter(logAdapter);
                        HideAllGroupLayout();
                        logAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,0);
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.logs_database);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_Reports:
                        tv_swipe.setVisibility(View.GONE);
                        updateReportArray();
                        recyclerView.setAdapter(reportAdapter);
                        HideAllGroupLayout();
                        reportAdapterOnItemClickListener();
                        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
                        params.setMargins(0,toolbar.getHeight() + 10,0,0);
                        linearLayoutRv.setLayoutParams(params);
                        toolbar.setTitle(R.string.reportsdatabase);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menuItem_LogOut:
                        tv_swipe.setVisibility(View.GONE);
                        drawerLayout.closeDrawers();
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new loginFragment()).commit();
                        ((MainActivity) getActivity()).setLoggedInId(null);
                        ((MainActivity) getActivity()).setTagID("");
                        ((MainActivity) getActivity()).setAdminAccess(false);
                        ((MainActivity) getActivity()).setIsLogged(false);

                        return true;
                }
                return false;
            }
        });




        //replaced with spinnerDate
        /*et_expDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 2 || charSequence.length() == 5){
                    et_expDate.setText(charSequence + "-");
                    et_expDate.setSelection(et_expDate.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/




        //Notice: in this procedures, the id var represents the id of the object in tha java array
        //java arrays starts from 0 while rows in database starts with 1
        spinnerOnItemSelectedListener();
        btn_addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cardNumber = et_cardNumber.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                updateSubscriptionArray();

                if (!subscriptionArrayList.isEmpty()) {
                    ArrayAdapter<Subscription> spinnerAdapter = new ArrayAdapter<Subscription>(getContext(), android.R.layout.simple_spinner_item, subscriptionArrayList);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDate.setAdapter(spinnerAdapter);
                    //int spinnerItemPosition = spinnerAdapter.getPosition(findSubscription(spinnerDate.getSelectedItem().toString()));
                    String ExpDate = df.format(addDays(subscriptionArrayList.get(spinnerSubscriptionPosition).getSubscription_delay()));
                    String subscription_name = subscriptionArrayList.get(spinnerSubscriptionPosition).getSubscription_name();

                    final ArrayList<String> cardsNumber = databaseHelper.getData(databaseHelper.getCardTable(), "cardNumber");

                    try {
                        if (!cardNumber.isEmpty() && !ExpDate.isEmpty()) {
                            if (!cardsNumber.contains(cardNumber)) {
                                if (ExpDate.contains("/")) {
                                    ExpDate = ExpDate.replace("/", "-");
                                }

                                if (isDate(ExpDate)) {
                                    Card cardToAdd = new Card(cardNumber, ExpDate, subscription_name);
                                    if (databaseHelper.insertCard(cardToAdd)) {
                                        toastThis(cardNumber + " Added Successfully!");
                                        updateCardArray();
                                        cardAdapter = new CardRecyclerViewAdapter(cardArrayList);
                                        recyclerView.setAdapter(cardAdapter);
                                        et_cardNumber.setText("");
                                        //et_expDate.setText("");
                                    } else {
                                        Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                    cardAdapterOnItemClickListener();
                                } else {
                                    Toast.makeText(getContext(), "Wrong Date Format", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                int id = cardsNumber.indexOf(cardNumber);
                                Toast.makeText(getContext(), " Card Already in Database, id: " + Integer.toString(id + 1), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "Please Write Card info", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    cardAdapterOnItemClickListener();
                    spinnerOnItemSelectedListener();
                } else {
                Toast.makeText(getContext(), "Create a Subscription First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userToAdd = et_userToAdd.getText().toString();
                String passToAdd = et_passToAdd.getText().toString();
                String fullNameToAdd = et_fullNameToAdd.getText().toString();
                ArrayList<String> users = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_username");

                if ( !userToAdd.isEmpty() && !passToAdd.isEmpty() && !fullNameToAdd.isEmpty()){
                    if (!users.contains(userToAdd)){
                        Agent agentToAdd = new Agent(userToAdd,passToAdd,fullNameToAdd);
                        if (databaseHelper.insertAgent(agentToAdd)) {
                            Toast.makeText(getContext(),userToAdd + " Added Successfully!" ,Toast.LENGTH_SHORT).show();
                            updateAgentArray();
                            agentAdapter = new AgentRecyclerViewAdapter(agentArrayList);
                            recyclerView.setAdapter(agentAdapter);
                            et_userToAdd.setText("");
                            et_passToAdd.setText("");
                            et_fullNameToAdd.setText("");
                        } else {
                            Toast.makeText(getContext(),"Something Went Wrong!" ,Toast.LENGTH_SHORT).show();
                        }
                        agentAdapterOnItemClickListener();
                    } else {
                        Toast.makeText(getContext(),userToAdd + " Already in Database!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"Please Write User And Password To Be Added!",Toast.LENGTH_SHORT).show();
                }
                agentAdapterOnItemClickListener();
            }
        });

        btn_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicle_code = et_vehicleCode.getText().toString();
                ArrayList<String> vehicles_codes = databaseHelper.getData(databaseHelper.getVehicleTable(),"vehicle_code");
                if (!vehicle_code.isEmpty()) {
                    if (!vehicles_codes.contains(vehicle_code)){
                        try {
                            Vehicle vehicle = new Vehicle(vehicle_code);
                            if (databaseHelper.insertVehicle(vehicle)){
                                toastThis(vehicle_code + getString(R.string.added_success));
                                updateVehicleArray();
                                vehicleAdapter = new VehicleRecyclerViewAdapter(vehicleArrayList);
                                recyclerView.setAdapter(vehicleAdapter);
                                et_vehicleCode.setText("");
                            } else {
                                toastThis(getString(R.string.something_went_wrong));
                            }
                            vehicleAdapterOnItemClickListener();
                        } catch (Exception e){
                            e.printStackTrace();
                            toastThis(e.toString());
                        }
                    } else {
                        toastThis(getString(R.string.already_in_db));
                    }
                } else {
                    toastThis(getString(R.string.vehicle_code_empty));
                }
                vehicleAdapterOnItemClickListener();
            }
        });

        btn_addStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stop_name = et_stopName.getText().toString();
                ArrayList<String> stops_names = databaseHelper.getData(databaseHelper.getStopTable(),"stop_name");
                if (!stop_name.isEmpty()){
                    if (!stops_names.contains(stop_name)){
                        try {
                            Stop stop = new Stop(stop_name);
                            if (databaseHelper.insertStop(stop)){
                                toastThis(stop_name + getString(R.string.added_success));
                                updateStopArray();
                                stopAdapter = new StopRecyclerViewAdapter(stopArrayList);
                                recyclerView.setAdapter(stopAdapter);
                                et_stopName.setText("");

                            } else {
                                toastThis(getString(R.string.something_went_wrong));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            toastThis(e.toString());
                        }
                    } else {
                        toastThis(getString(R.string.already_in_db));
                    }
                } else {
                    toastThis(getString(R.string.stop_name_empty));
                }
                stopAdapterOnItemClickListener();
            }
        });

        btn_addSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subscription_name = et_subscriptionName.getText().toString();
                String subscription_delay = et_subscriptionDelay.getText().toString();
                ArrayList<String> subscriptions_names = databaseHelper.getData(databaseHelper.getSubscriptionTable(),"subscription_name");
                if (!subscription_name.isEmpty() && !subscription_delay.isEmpty()){
                    if (!subscriptions_names.contains(subscription_name)){
                        try{
                            Subscription subscription = new Subscription(subscription_name,subscription_delay);
                            if (databaseHelper.insertSubscription(subscription)){
                                toastThis(subscription_name + getString(R.string.added_success));
                                updateSubscriptionArray();
                                subscriptionAdapter = new SubscriptionRecyclerViewAdapter(subscriptionArrayList);
                                recyclerView.setAdapter(subscriptionAdapter);
                                et_subscriptionName.setText("");
                                et_subscriptionDelay.setText("");
                            } else {
                                toastThis(getString(R.string.something_went_wrong));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            toastThis(e.toString());
                        }
                    } else {
                        toastThis(getString(R.string.already_in_db));
                    }
                } else {
                    toastThis(getString(R.string.subname_or_delay_empty));
                }
                subscriptionAdapterOnItemClickListener();
            }
        });

        btn_updateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                updateSubscriptionArray();
                ArrayAdapter<Subscription> spinnerAdapter = new ArrayAdapter<Subscription>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_item,subscriptionArrayList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDate.setAdapter(spinnerAdapter);
                //int spinnerItemPosition = spinnerAdapter.getPosition(findSubscription(spinnerDate.getSelectedItem().toString()));
                spinnerDate.setSelection(spinnerSubscriptionPosition);
                String ExpDate = df.format(addDays(subscriptionArrayList.get(spinnerSubscriptionPosition).getSubscription_delay()));
                String subscription_name = subscriptionArrayList.get(spinnerSubscriptionPosition).getSubscription_name();
                Card card = new Card(cardArrayList.get(currentPosition).getId(), cardArrayList.get(currentPosition).getCardNumber(),ExpDate, "0",subscription_name);
                if(switchBlacklist.isChecked()){
                   card.setIsBlacklisted("1");
                }
                if (databaseHelper.updateCard(card)){
                    updateCardArray();
                    cardAdapter.notifyItemChanged(currentPosition);
                    toastThis(getString(R.string.card_updated));
                    switchBlacklist.setVisibility(View.GONE);
                    btn_updateCard.setVisibility(View.GONE);
                    btn_addCard.setVisibility(View.VISIBLE);
                    et_cardNumber.setVisibility(View.VISIBLE);
                    //request focus
                    /*try {
                        et_expDate.clearFocus();
                        et_cardNumber.requestFocus();
                        et_cardNumber.setSelection(et_cardNumber.getText().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                } else {
                    toastThis(getString(R.string.something_went_wrong));
                }
                cardAdapterOnItemClickListener();
                spinnerOnItemSelectedListener();
                et_cardNumberSpace.setVisibility(View.VISIBLE);
                et_cardNumber.setText("");
                et_cardNumber.requestFocus();
            }
        });

        btn_updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Agent agent = new Agent(agentArrayList.get(currentPosition).getId(),et_userToAdd.getText().toString(),et_passToAdd.getText().toString(),et_fullNameToAdd.getText().toString());

                if (databaseHelper.updateAgent(agent)){
                    updateAgentArray();
                    agentAdapter.notifyItemChanged(currentPosition);
                    toastThis(getString(R.string.agent_updated));
                    btn_updateUser.setVisibility(View.GONE);
                    btn_addUser.setVisibility(View.VISIBLE);
                    et_userToAdd.setText("");
                    et_passToAdd.setText("");
                    et_fullNameToAdd.setText("");

                } else toastThis(getString(R.string.something_went_wrong));
                agentAdapterOnItemClickListener();
                et_fullNameToAdd.clearFocus();
                et_passToAdd.clearFocus();
                et_userToAdd.requestFocus();
            }
        });

        btn_updateVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle vehicle = new Vehicle(vehicleArrayList.get(currentPosition).getId(),et_vehicleCode.getText().toString());
                if (databaseHelper.updateVehicle(vehicle)){
                    updateVehicleArray();
                    vehicleAdapter.notifyItemChanged(currentPosition);
                    toastThis(getString(R.string.vehicle_updated));
                    btn_updateVehicle.setVisibility(View.GONE);
                    btn_addVehicle.setVisibility(View.VISIBLE);
                    et_vehicleCode.setText("");
                } else toastThis(getString(R.string.something_went_wrong));
                vehicleAdapterOnItemClickListener();
            }
        });

        btn_updateStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop stop = new Stop(stopArrayList.get(currentPosition).getId(),et_stopName.getText().toString());
                if (databaseHelper.updateStop(stop)){
                    updateStopArray();
                    stopAdapter.notifyItemChanged(currentPosition);
                    toastThis(getString(R.string.stop_updated));
                    btn_updateStop.setVisibility(View.GONE);
                    btn_addStop.setVisibility(View.VISIBLE);
                    et_stopName.setText("");
                } else toastThis(getString(R.string.something_went_wrong));
                stopAdapterOnItemClickListener();
            }
        });

        btn_updateSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subscription subscription = new Subscription(subscriptionArrayList.get(currentPosition).getId(),et_subscriptionName.getText().toString(),et_subscriptionDelay.getText().toString());
                if (databaseHelper.updateSubscription(subscription)){
                    updateSubscriptionArray();
                    subscriptionAdapter.notifyItemChanged(currentPosition);
                    toastThis(getString(R.string.sub_updated));
                    btn_updateSubscription.setVisibility(View.GONE);
                    btn_addSubscription.setVisibility(View.VISIBLE);
                    et_subscriptionName.setText("");
                    et_subscriptionDelay.setText("");
                } else toastThis(getString(R.string.something_went_wrong));
                subscriptionAdapterOnItemClickListener();
                et_subscriptionDelay.clearFocus();
                et_subscriptionName.requestFocus();
            }
        });


        /*btn_blacklistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = et_cardNumber.getText().toString();
                ArrayList<String> cardsNumber = databaseHelper.getData("Card","cardNumber");
                ArrayList<String> cardsBL = databaseHelper.getData("Card","isBlacklisted");
                if (!cardNumber.isEmpty()){
                    if (cardsNumber.contains(cardNumber)) {
                        int id = cardsNumber.indexOf(cardNumber);
                        if (cardsBL.get(id).matches("0")) {
                            databaseHelper.blacklistCard(cardNumber);
                            Toast.makeText(getContext(), cardNumber + " Blacklisted Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), cardNumber + "  Already in Blacklist. id:" + " " + Integer.toString(id + 1), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Card Not Found in Database!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"Card Number Empty, Please Write a Card Number",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_whitelistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = et_cardNumber.getText().toString();
                ArrayList<String> cardsNumber = databaseHelper.getData("Card","cardNumber");
                ArrayList<String> cardsBL = databaseHelper.getData("Card","isBlacklisted");
                int id = cardsNumber.indexOf(cardNumber);
                if (!cardNumber.isEmpty()){
                    if (cardsNumber.contains(cardNumber)) {
                        if (cardsBL.get(id).matches("1")) {
                            databaseHelper.whitelistCard(cardNumber);
                            Toast.makeText(getContext(), cardNumber + " Whitelisted Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), cardNumber + " Already in Whitelist. id: " + Integer.toString(id + 1), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Card Not Found in Database!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"Card Number Empty, Please Write a Card Number",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_showCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = et_cardNumber.getText().toString();
                ArrayList<String> cardsID = databaseHelper.getData("Card","id");
                ArrayList<String> cardsNumber = databaseHelper.getData("Card","cardNumber");
                ArrayList<String> cardsBL = databaseHelper.getData("Card","isBlacklisted");
                ArrayList<String> cardExp = databaseHelper.getData("Card", "ExpDate");
                if (cardsNumber.contains(cardNumber)){
                    int id = cardsNumber.indexOf(cardNumber);
                    Toast.makeText(getContext(),cardsID.get(id) + " " + cardsNumber.get(id) + " " + cardsBL.get(id) + "\nExpires: " + cardExp.get(id),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),"Card Not Found in Database!",Toast.LENGTH_SHORT).show();
                }

            }
        });*/
        drawerLayout.openDrawer((int) GravityCompat.START);
        initiateMenu();
        return v;
    }

    void initiateMenu(){
        ViewGroup.MarginLayoutParams params;
        updateSubscriptionArray();
        ArrayAdapter<Subscription> spinnerAdapter = new ArrayAdapter<Subscription>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_item,subscriptionArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(spinnerAdapter);
        spinnerDate.setSelection(0);
        tv_swipe.setVisibility(View.GONE);
        updateCardArray();
        recyclerView.setAdapter(cardAdapter);
        ShowGroupLayout(cardGroupLayout);
        cardAdapterOnItemClickListener();
        spinnerOnItemSelectedListener();
        params = (ViewGroup.MarginLayoutParams) linearLayoutRv.getLayoutParams();
        params.setMargins(0, 250,0,convertDpToPixel(250,getContext()));
        linearLayoutRv.setLayoutParams(params);
        toolbar.setTitle(getString(R.string.cards_database));
        drawerLayout.closeDrawers();
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
        if (status.matches("Valid")) return Color.parseColor("#00B2AF");
        else if (status.matches("Expired")) return Color.RED;
        else if (status.matches("Blacklisted")) return Color.RED;
        else return Color.BLACK;
    }

    public static Date addDays(String days) {
        Date date = Calendar.getInstance().getTime();
        int daysInt = Integer.parseInt(days);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysInt); //minus number would decrement the days
        return cal.getTime();
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
                    toReturn = "Valid";
                } else if (tdd.after(expd)) {
                    toReturn = "Expired";
                } else if ((tdd == null) || (expd == null)) {
                    toReturn = "Null Dates";
                }

            } else {
                toReturn = "Blacklisted";
            }
        } else {
            toReturn = "Invalid";
        }
        return toReturn;
    }

    public void updateArrays(){
        updateAgentArray();
        updateCardArray();
        updateVehicleArray();
        updateStopArray();
        updateSubscriptionArray();
        updateLogsArray();
        updateReportArray();
    }

    public void updateAgentArray(){
        ArrayList<String> ag_id = databaseHelper.getData(databaseHelper.getAgentTable(),"id");
        ArrayList<String> ag_username = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_username");
        ArrayList<String> ag_fullname = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_fullname");
        ArrayList<String> ag_password = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_password");

        agentArrayList.clear();

        for (int i=0; i < ag_id.size(); i++){
            agentArrayList.add(new Agent(ag_id.get(i), ag_username.get(i),ag_password.get(i),ag_fullname.get(i)));
        }
    }

    public void updateCardArray(){

        ArrayList<String> card_id = databaseHelper.getData(databaseHelper.getCardTable(),"id");
        ArrayList<String> card_number= databaseHelper.getData(databaseHelper.getCardTable(),"cardNumber");
        ArrayList<String> card_expdate= databaseHelper.getData(databaseHelper.getCardTable(),"ExpDate");
        ArrayList<String> card_isBlacklisted = databaseHelper.getData(databaseHelper.getCardTable(), "isBlacklisted");
        ArrayList<String> card_subscription = databaseHelper.getData(databaseHelper.getCardTable(),"subscription");

        cardArrayList.clear();

        for (int i = 0; i < card_id.size(); i++) {
            cardArrayList.add(new Card(card_id.get(i), card_number.get(i),card_expdate.get(i),card_isBlacklisted.get(i),card_subscription.get(i)));
        }
    }

    public void updateVehicleArray(){
        ArrayList<String> vehicle_id = databaseHelper.getData(databaseHelper.getVehicleTable(),"id");
        ArrayList<String> vehicle_code = databaseHelper.getData(databaseHelper.getVehicleTable(),"vehicle_code");

        vehicleArrayList.clear();

        for (int i = 0; i < vehicle_id.size(); i++){
            vehicleArrayList.add(new Vehicle(vehicle_id.get(i),vehicle_code.get(i)));
        }
    }

    public void updateStopArray(){
        ArrayList<String> stop_id = databaseHelper.getData(databaseHelper.getStopTable(),"id");
        ArrayList<String> stop_name = databaseHelper.getData(databaseHelper.getStopTable(),"stop_name");

        stopArrayList.clear();

        for (int i = 0; i < stop_id.size(); i++) {
            stopArrayList.add(new Stop(stop_id.get(i),stop_name.get(i)));
        }
    }

    public void updateSubscriptionArray(){
        ArrayList<String> subscription_id = databaseHelper.getData(databaseHelper.getSubscriptionTable(),"id");
        ArrayList<String> subscription_name = databaseHelper.getData(databaseHelper.getSubscriptionTable(),"subscription_name");
        ArrayList<String> subscription_delay = databaseHelper.getData(databaseHelper.getSubscriptionTable(),"subscription_delay");

        subscriptionArrayList.clear();

        for (int i = 0; i < subscription_id.size(); i++) {
            subscriptionArrayList.add(new Subscription(subscription_id.get(i), subscription_name.get(i), subscription_delay.get(i)));
        }
    }

    public void updateLogsArray(){
        ArrayList<String> log_id = databaseHelper.getData(databaseHelper.getLogsTable(),"id");
        ArrayList<String> log_agentId = databaseHelper.getData(databaseHelper.getLogsTable(),"agent_id");
        ArrayList<String> log_cardId = databaseHelper.getData(databaseHelper.getLogsTable(),"card_id");
        ArrayList<String> log_vehicleId = databaseHelper.getData(databaseHelper.getLogsTable(),"vehicle_id");
        ArrayList<String> log_stopId = databaseHelper.getData(databaseHelper.getLogsTable(),"stop_id");
        ArrayList<String> log_destinationId = databaseHelper.getData(databaseHelper.getLogsTable(),"destination_id");
        ArrayList<String> log_act = databaseHelper.getData(databaseHelper.getLogsTable(),"act");

        logArrayList.clear();

        for (int i = 0; i < log_id.size(); i++) {
            logArrayList.add(new Log(log_id.get(i),log_agentId.get(i),log_cardId.get(i),log_vehicleId.get(i),log_stopId.get(i),log_destinationId.get(i),log_act.get(i)));
        }
    }

    public void updateReportArray(){
        ArrayList<String> report_id = databaseHelper.getData(databaseHelper.getReportsTable(),"id");
        ArrayList<String> report_agent_id = databaseHelper.getData(databaseHelper.getReportsTable(),"agent_id");
        ArrayList<String> report_title = databaseHelper.getData(databaseHelper.getReportsTable(),"reporttitle");
        ArrayList<String> report_message = databaseHelper.getData(databaseHelper.getReportsTable(),"reportmessage");

        reportArrayList.clear();

        for (int i = 0; i < report_id.size(); i++) {
            reportArrayList.add(new Report(report_id.get(i),report_agent_id.get(i),report_title.get(i),report_message.get(i)));
        }
    }

    public void toastThis(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }

    public void cardAdapterOnItemClickListener(){
        cardAdapter.setOnItemClickListener(new CardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),getString(R.string.id_two_dots) + cardArrayList.get(position).getId()
                        + "\n" + getString(R.string.card_two_dots) + cardArrayList.get(position).getCardNumber()
                        + "\n" + getString(R.string.expiry_date_two_dots) + cardArrayList.get(position).getExpDate()
                        + "\n" + getString(R.string.sub_two_dots) + cardArrayList.get(position).getSubscription()
                        + "\n" + getString(R.string.status_two_dots) + card_Status(cardArrayList.get(position).getCardNumber()),Toast.LENGTH_SHORT).show();
                currentPosition = position;
            }
            @Override
            public void onEditClick(int position) {
                btn_addCard.setVisibility(View.GONE);
                et_cardNumberSpace.setVisibility(View.GONE);
                et_cardNumber.setText(cardArrayList.get(position).getCardNumber());
                spinnerDate.setSelection(subscriptionArrayList.indexOf(findSubscription(cardArrayList.get(position).getSubscription())));
                switchBlacklist.setVisibility(View.VISIBLE);

                if (cardArrayList.get(position).getIsBlacklisted().matches("1")){
                    switchBlacklist.setChecked(true);
                } else if (cardArrayList.get(position).getIsBlacklisted().matches("0")){
                    switchBlacklist.setChecked(false);
                } else {
                    Toast.makeText(getContext(),cardArrayList.get(position).getIsBlacklisted(),Toast.LENGTH_SHORT).show();
                }
                btn_updateCard.setVisibility(View.VISIBLE);
                currentPosition = position;
                spinnerOnItemSelectedListener();
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteCard(cardArrayList.get(position));
                cardArrayList.remove(position);
                et_cardNumber.setText("");
                spinnerDate.setSelection(0);
                cardAdapter.notifyItemRemoved(position);
                currentPosition = position;
                switchBlacklist.setVisibility(View.GONE);
                btn_updateCard.setVisibility(View.GONE);
                et_cardNumberSpace.setVisibility(View.VISIBLE);
                btn_addCard.setVisibility(View.VISIBLE);
            }
        });
    }

    public void agentAdapterOnItemClickListener(){
        agentAdapter.setOnItemClickListener(new AgentRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),getString(R.string.id_two_dots) + agentArrayList.get(position).getId()
                        + "\n" + getString(R.string.name_two_dots) + agentArrayList.get(position).getAg_fullname()
                        + "\n" + getString(R.string.username_two_dots) + agentArrayList.get(position).getAg_username()
                        + "\n" + getString(R.string.password_two_dots) + agentArrayList.get(position).getAg_password(),Toast.LENGTH_SHORT).show();
                currentPosition = position;
            }

            @Override
            public void onEditClick(int position) {
                btn_addUser.setVisibility(View.GONE);
                btn_updateUser.setVisibility(View.VISIBLE);
                et_userToAdd.setText(agentArrayList.get(position).getAg_username());
                et_passToAdd.setText(agentArrayList.get(position).getAg_password());
                et_fullNameToAdd.setText(agentArrayList.get(position).getAg_fullname());
                currentPosition = position;
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteAgent(agentArrayList.get(position));

                agentArrayList.remove(position);
                et_userToAdd.setText("");
                et_passToAdd.setText("");
                et_fullNameToAdd.setText("");
                agentAdapter.notifyItemRemoved(position);
                currentPosition = position;
            }
        });
    }

    public void vehicleAdapterOnItemClickListener(){
        vehicleAdapter.setOnItemClickListener(new VehicleRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),getString(R.string.id_two_dots) + vehicleArrayList.get(position).getId()
                        + "\n" + getString(R.string.code_two_dots) + vehicleArrayList.get(position).getVehicle_code(),Toast.LENGTH_SHORT).show();
                currentPosition = position;
            }

            @Override
            public void onEditClick(int position) {
                btn_addVehicle.setVisibility(View.GONE);
                btn_updateVehicle.setVisibility(View.VISIBLE);
                et_vehicleCode.setText(vehicleArrayList.get(position).getVehicle_code());
                currentPosition = position;
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteVehicle(vehicleArrayList.get(position));
                vehicleArrayList.remove(position);
                et_vehicleCode.setText("");
                vehicleAdapter.notifyItemRemoved(position);
                currentPosition = position;
            }
        });
    }

    public void stopAdapterOnItemClickListener(){
        stopAdapter.setOnItemClickListener(new StopRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),getString(R.string.id_two_dots) + stopArrayList.get(position).getId()
                        + "\n" + getString(R.string.name_two_dots)+ stopArrayList.get(position).getStop_name(),Toast.LENGTH_SHORT).show();
                currentPosition = position;
            }

            @Override
            public void onEditClick(int position) {
                btn_addStop.setVisibility(View.GONE);
                btn_updateStop.setVisibility(View.VISIBLE);
                et_stopName.setText(stopArrayList.get(position).getStop_name());
                currentPosition = position;
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteStop(stopArrayList.get(position));
                stopArrayList.remove(position);
                et_stopName.setText("");
                stopAdapter.notifyItemRemoved(position);
                currentPosition = position;
            }
        });
    }

    public void subscriptionAdapterOnItemClickListener(){
        subscriptionAdapter.setOnItemClickListener(new SubscriptionRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),getString(R.string.id_two_dots) + subscriptionArrayList.get(position).getId()
                        + "\n" + getString(R.string.name_two_dots) + subscriptionArrayList.get(position).getSubscription_name()
                        + "\n" + getString(R.string.delay_two_dots) + subscriptionArrayList.get(position).getSubscription_delay()
                        + "\n" + getString(R.string.days),Toast.LENGTH_SHORT).show();
                currentPosition = position;
            }

            @Override
            public void onEditClick(int position) {
                btn_addSubscription.setVisibility(View.GONE);
                btn_updateSubscription.setVisibility(View.VISIBLE);
                et_subscriptionName.setText(subscriptionArrayList.get(position).getSubscription_name());
                et_subscriptionDelay.setText(subscriptionArrayList.get(position).getSubscription_delay());
                currentPosition = position;
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteSubscription(subscriptionArrayList.get(position));
                subscriptionArrayList.remove(position);
                et_subscriptionName.setText("");
                et_subscriptionDelay.setText("");
                subscriptionAdapter.notifyItemRemoved(position);

                currentPosition = position;
            }
        });
    }

    public void logAdapterOnItemClickListener(){
        logAdapter.setOnItemClickListener(new LogRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*ArrayList<String> agentId = databaseHelper.getData(databaseHelper.getAgentTable(),"id");
                ArrayList<String> cardId = databaseHelper.getData(databaseHelper.getCardTable(),"id");
                ArrayList<String> vehicleId = databaseHelper.getData(databaseHelper.getVehicleTable(),"id");
                ArrayList<String> stopId = databaseHelper.getData(databaseHelper.getStopTable(),"id");
                toastThis(agentArrayList.get(Integer.parseInt(agentId.get(position))).getAg_username() + " "
                        + logArrayList.get(position).getAct() + " "
                        + cardArrayList.get(Integer.parseInt(cardId.get(position))).getCardNumber() + " at "
                        + stopArrayList.get(Integer.parseInt(stopId.get(position))).getStop_name() + " in "
                        + vehicleArrayList.get(Integer.parseInt(vehicleId.get(position))).getVehicle_code());*/
                toastThis("id: " + logArrayList.get(position).getId()
                        + "\n" + getString(R.string.agent_tw_dots) + logArrayList.get(position).getAgent_id()
                        + "\n" + getString(R.string.card_tw_dots) + logArrayList.get(position).getCard_id()
                        + "\n" + getString(R.string.vehicle_tw_dots) + logArrayList.get(position).getVehicle_id()
                        + "\n" + getString(R.string.stop_tw_dots) + logArrayList.get(position).getStop_id()
                        + "\n" + getString(R.string.destination_tw_dots) + logArrayList.get(position).getDestination_id()
                        + "\n" + getString(R.string.action_tw_dots) + logArrayList.get(position).getAct());
                currentPosition = position;
            }
        });
    }

    public void reportAdapterOnItemClickListener(){
        reportAdapter.setOnItemClickListener(new ReportRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((MainActivity)getActivity()).reportID_AF_to_RF = reportArrayList.get(position).getId();
                /*Bundle bundle = new Bundle();
                bundle.putString("title",reportArrayList.get(position).getReportTitle());
                bundle.putString("reporter_id",reportArrayList.get(position).getAgent_id());
                bundle.putString("message",reportArrayList.get(position).getReportMessage());
                rFragment.setArguments(bundle);*/

                reportFragment rFragment = new reportFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,rFragment)
                        .commit();
                currentPosition = position;
            }

            @Override
            public void onDeleteClick(int position) {
                databaseHelper.deleteReport(reportArrayList.get(position));
                reportArrayList.remove(position);
                reportAdapter.notifyItemRemoved(position);

                currentPosition = position;

            }
        });
    }

    public void spinnerOnItemSelectedListener(){
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSubscriptionName = spinnerDate.getSelectedItem().toString();
                spinnerSubscriptionPosition = spinnerDate.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void ShowGroupLayout(ConstraintLayout layoutToShowAndHideOthers){
        cardGroupLayout.setVisibility(View.GONE);
        agentGroupLayout.setVisibility(View.GONE);
        vehicleGroupLayout.setVisibility(View.GONE);
        stopGroupLayout.setVisibility(View.GONE);
        subscriptionGroupLayout.setVisibility(View.GONE);
        layoutToShowAndHideOthers.setVisibility(View.VISIBLE);
    }

    public void HideAllGroupLayout(){
        cardGroupLayout.setVisibility(View.GONE);
        agentGroupLayout.setVisibility(View.GONE);
        vehicleGroupLayout.setVisibility(View.GONE);
        stopGroupLayout.setVisibility(View.GONE);
        subscriptionGroupLayout.setVisibility(View.GONE);
    }
    /*
    public void HideGoToButton(Button btn){
        btn_gotoCardsDB.setVisibility(View.VISIBLE);
        btn_gotoAgentsDB.setVisibility(View.VISIBLE);
        btn_gotoVehiclesDB.setVisibility(View.VISIBLE);
        btn_gotoStopsDB.setVisibility(View.VISIBLE);
        btn_gotoSubscriptionsDB.setVisibility(View.VISIBLE);
        btn_gotoLogsDB.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);
    } */

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public int convertPixelToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public Subscription findSubscription(String subscription_name) {
        updateSubscriptionArray();
        for(Subscription subscription : subscriptionArrayList) {
            if(subscription.getSubscription_name().equals(subscription_name)) {
                return subscription;
            }
        }
        return null;
    }
}