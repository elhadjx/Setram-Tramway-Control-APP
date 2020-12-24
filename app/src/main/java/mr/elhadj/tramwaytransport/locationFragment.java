package mr.elhadj.tramwaytransport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class locationFragment extends Fragment {
    Button btn_updateLocation;
    ArrayList<Vehicle> vehicleArrayList;
    ArrayList<Stop> stopArrayList,destinationArrayList;
    Spinner spinnerVehicle, spinnerStop, spinnerDestination;
    DatabaseHelper databaseHelper;

    Vehicle currentVehicle;
    Stop currentStop;
    Stop currentDestination;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public locationFragment() {
        // Required empty public constructor
    }
    public static locationFragment newInstance(String param1, String param2) {
        locationFragment fragment = new locationFragment();
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
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        databaseHelper = new DatabaseHelper(this.getContext());

        spinnerVehicle = v.findViewById(R.id.spinnerVehicle);
        spinnerStop = v.findViewById(R.id.spinnerStop);
        spinnerDestination = v.findViewById(R.id.spinnerDestination);
        btn_updateLocation = v.findViewById(R.id.btn_updateLocation);

        vehicleArrayList = new ArrayList<>();
        stopArrayList = new ArrayList<>();
        destinationArrayList = new ArrayList<>();

        updateArrays();

        ArrayAdapter<Vehicle> spinnerVehicleAdapter = new ArrayAdapter<Vehicle>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_item,vehicleArrayList);
        ArrayAdapter<Stop> spinnerStopAdapter = new ArrayAdapter<Stop>(getContext(),android.R.layout.simple_spinner_item,stopArrayList);
        ArrayAdapter<Stop> spinnerDestinationAdapter = new ArrayAdapter<Stop>(getContext(),android.R.layout.simple_spinner_item,destinationArrayList);

        spinnerVehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVehicle.setAdapter(spinnerVehicleAdapter);
        spinnerStop.setAdapter(spinnerStopAdapter);
        spinnerDestination.setAdapter(spinnerDestinationAdapter);

        Vehicle selectedVehicle = ((MainActivity) Objects.requireNonNull(getActivity())).getSelectedVehicle();
        Stop selectedStop = ((MainActivity) getActivity()).getSelectedStop();
        Stop selectedDestination = ((MainActivity) getActivity()).getSelectedDestination();

        if (selectedVehicle != null) {
            spinnerVehicle.setSelection(spinnerVehicleAdapter.getPosition(((MainActivity) getActivity()).getSelectedVehicle()));
        }
        if (selectedStop != null) {
            spinnerStop.setSelection(spinnerStopAdapter.getPosition(((MainActivity) getActivity()).getSelectedStop()));
        }
        if (selectedDestination != null) {
            spinnerDestination.setSelection(spinnerDestinationAdapter.getPosition(((MainActivity) getActivity()).getSelectedDestination()));
        }

        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentVehicle = (Vehicle) spinnerVehicle.getSelectedItem();
                ((MainActivity) Objects.requireNonNull(getActivity())).setSelectedVehicle(currentVehicle);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerStop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentStop = (Stop) spinnerStop.getSelectedItem();
                ((MainActivity) Objects.requireNonNull(getActivity())).setSelectedStop(currentStop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentDestination = (Stop) spinnerDestination.getSelectedItem();
                ((MainActivity) Objects.requireNonNull(getActivity())).setSelectedDestination(currentDestination);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_updateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentVehicle = (Vehicle) spinnerVehicle.getSelectedItem();
                ((MainActivity) Objects.requireNonNull(getActivity())).setSelectedVehicle(currentVehicle);
                currentStop = (Stop) spinnerStop.getSelectedItem();
                ((MainActivity) getActivity()).setSelectedStop(currentStop);
                currentDestination = (Stop) spinnerDestination.getSelectedItem();
                ((MainActivity) getActivity()).setSelectedDestination(currentDestination);

                String LoggedInID = Integer.toString(((MainActivity) getActivity()).getLoggedInId());
                String SelectedVehicleID = ((MainActivity) getActivity()).getSelectedVehicle().getId();
                String SelectedStopID = ((MainActivity) getActivity()).getSelectedStop().getId();
                String SelectedDestinationID = ((MainActivity) getActivity()).getSelectedDestination().getId();

                Log log = new Log(LoggedInID,SelectedVehicleID,SelectedStopID,SelectedDestinationID,"Located");
                databaseHelper.insertLog(log);
                Toast.makeText(getContext(), R.string.location_updated,Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void updateArrays(){
        updateVehicleArray();
        updateStopArray();
        updateDestinationArray();
    }

    public void updateVehicleArray(){
        ArrayList<String> vehicle_id = databaseHelper.getData(databaseHelper.getVehicleTable(),"id");
        ArrayList<String> vehicle_code = databaseHelper.getData(databaseHelper.getVehicleTable(),"vehicle_code");

        vehicleArrayList.clear();
        if (!vehicle_id.isEmpty()) {
            for (int i = 0; i < vehicle_id.size(); i++) {
                vehicleArrayList.add(new Vehicle(vehicle_id.get(i), vehicle_code.get(i)));
            }
        } else {
            vehicleArrayList.add(new Vehicle("Null"));
        }
    }

    public void updateStopArray(){
        ArrayList<String> stop_id = databaseHelper.getData(databaseHelper.getStopTable(),"id");
        ArrayList<String> stop_name = databaseHelper.getData(databaseHelper.getStopTable(),"stop_name");

        stopArrayList.clear();
        if (!stop_id.isEmpty()) {
            for (int i = 0; i < stop_id.size(); i++) {
                stopArrayList.add(new Stop(stop_id.get(i), stop_name.get(i)));
            }
        } else {
            stopArrayList.add(new Stop("Null"));
            stopArrayList.add(new Stop("Null"));
        }
    }

    public void updateDestinationArray() {
        updateStopArray();
        destinationArrayList.clear();
        if (!stopArrayList.isEmpty()) {
            destinationArrayList.add(stopArrayList.get(0));
            destinationArrayList.add(stopArrayList.get(stopArrayList.size() - 1));
        } else {
            destinationArrayList.add(new Stop("Null"));
            destinationArrayList.add(new Stop("Null"));
        }
    }
}