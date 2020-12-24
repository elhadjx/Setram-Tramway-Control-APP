package mr.elhadj.tramwaytransport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class profileFragment extends Fragment {

    TextView tv_profileId, tv_profileUsername, tv_profilefullname;
    public DatabaseHelper databaseHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        databaseHelper = new DatabaseHelper(this.getContext());
        ArrayList<String> profileUser = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_username");
        ArrayList<String> profileName = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_fullname");
        ArrayList<String> profileId =   databaseHelper.getData(databaseHelper.getAgentTable(),"id");
        int id = ((MainActivity) getActivity()).getLoggedInId();
        tv_profileId = v.findViewById(R.id.tv_profileid);
        tv_profileUsername = v.findViewById(R.id.tv_profileUsername);
        tv_profilefullname = v.findViewById(R.id.tv_profileFullname);

        tv_profileId.setText(profileId.get(id));
        tv_profileUsername.setText(profileUser.get(id));
        tv_profilefullname.setText(profileName.get(id));


        return v;
    }
}