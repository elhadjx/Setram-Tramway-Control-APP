package mr.elhadj.tramwaytransport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class loginFragment extends Fragment {



    public Button loginButton,btn_english,btn_arabic,btn_french;
    public BottomNavigationView bnv;
    DatabaseHelper databaseHelper;
    EditText eUsername,ePassword;

    public loginFragment() {
        // Required empty public constructor
    }


    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //where the shit happens

        loginButton = v.findViewById(R.id.loginButton);
        btn_english = v.findViewById(R.id.btn_english);
        btn_arabic = v.findViewById(R.id.btn_arabic);
        btn_french = v.findViewById(R.id.btn_french);
        eUsername = v.findViewById(R.id.username);
        ePassword = v.findViewById(R.id.password);
        bnv = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_navigation);

        databaseHelper = new DatabaseHelper(this.getContext());




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> loginUser = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_username");
                ArrayList<String> loginPass = databaseHelper.getData(databaseHelper.getAgentTable(), "ag_password");
                String username = eUsername.getText().toString();
                String password = ePassword.getText().toString();
                if ((username.matches("admin")) && (password.matches("admin"))){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new adminFragment()).commit();
                    ((MainActivity) getActivity()).setIsLogged(true);
                    ((MainActivity) getActivity()).setAdminAccess(true);
                    Toast.makeText(getContext(), R.string.welcome_back_admin,Toast.LENGTH_SHORT).show();

                } else if (loginUser.contains(username)){
                    int id = loginUser.indexOf(username);
                    if (loginPass.get(id).matches(password)){
                        ((MainActivity) getActivity()).setLoggedInId(id);
                        ((MainActivity) getActivity()).setIsLogged(true);
                        ((MainActivity) getActivity()).setAdminAccess(false);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new cardFragment()).commit();
                        bnv.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),getString(R.string.welcome_back) + username,Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),getString(R.string.wrong_password_for) + username,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.wrong_info, Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setLanguage("en");
            }
        });
        btn_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setLanguage("ar");
            }
        });
        btn_french.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setLanguage("fr");

            }
        });

        return v;
    }
}