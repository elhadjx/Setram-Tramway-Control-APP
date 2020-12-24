package mr.elhadj.tramwaytransport;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class settingsFragment extends Fragment {

    Button btn_logOut, btn_exportDB, btn_importDB, btn_report,btn_sendReport;
    EditText et_reportText, et_reportTitle;
    TextView tv_about;
    TextInputLayout et_report, et_reportTitleField;
    BottomNavigationView bnv;
    String pathDB;

    public DatabaseHelper databaseHelper;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public settingsFragment() {
        // Required empty public constructor
    }
    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        bnv = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_navigation);
        databaseHelper = new DatabaseHelper(this.getContext());


        btn_importDB = v.findViewById(R.id.btn_importDB);
        btn_logOut = v.findViewById(R.id.logoutButton);
        btn_exportDB = v.findViewById(R.id.btn_exportDB);
        btn_report = v.findViewById(R.id.btn_report);
        btn_sendReport = v.findViewById(R.id.btn_sendReport);
        et_report = v.findViewById(R.id.et_report);
        et_reportTitle = v.findViewById(R.id.et_reportTitle);
        et_reportTitleField = v.findViewById(R.id.et_reportTitleField);
        et_reportText = v.findViewById(R.id.et_reportText);
        tv_about = v.findViewById(R.id.tv_about);

        //ArrayList<String> profileName = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_fullname");



        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_importDB.setVisibility(View.GONE);
                btn_exportDB.setVisibility(View.GONE);
                btn_logOut.setVisibility(View.GONE);
                btn_report.setVisibility(View.GONE);
                tv_about.setVisibility(View.GONE);
                btn_sendReport.setVisibility(View.VISIBLE);
                et_report.setVisibility(View.VISIBLE);
                et_reportTitleField.setVisibility(View.VISIBLE);
            }
        });

        btn_sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_importDB.setVisibility(View.VISIBLE);
                btn_exportDB.setVisibility(View.VISIBLE);
                btn_logOut.setVisibility(View.VISIBLE);
                btn_report.setVisibility(View.VISIBLE);
                tv_about.setVisibility(View.VISIBLE);
                btn_sendReport.setVisibility(View.GONE);
                et_report.setVisibility(View.GONE);
                et_reportTitleField.setVisibility(View.GONE);

                ArrayList<String> agentId =   databaseHelper.getData(databaseHelper.getAgentTable(),"id");
                int id = ((MainActivity) getActivity()).getLoggedInId();

                Report report = new Report(agentId.get(id),et_reportTitle.getText().toString(), et_reportText.getText().toString());
                if (et_reportTitle.getText().toString().isEmpty() || et_reportText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), R.string.reportEmpty,Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.insertReport(report)) {
                        Toast.makeText(getContext(), R.string.reported, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.notreported, Toast.LENGTH_SHORT).show();
                    }
                }

                et_reportTitle.setText("");
                et_reportText.setText("");

            }
        });

        btn_importDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent db = new Intent();
                db.setType("*/.db");
                db.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(db,1);

            }
        });

        btn_exportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB();
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) Objects.requireNonNull(getActivity())).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new loginFragment()).commit();
                ((MainActivity) getActivity()).setLoggedInId(null);
                ((MainActivity) getActivity()).setIsLogged(false);
                ((MainActivity) getActivity()).setAdminAccess(false);
                bnv.setVisibility(View.GONE);
            }
        });
        return v;
    }

    private void exportDB(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this.getContext());
        File sd = Objects.requireNonNull(getContext()).getExternalFilesDir(null);
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "dz.setram.control" +"/databases/"+databaseHelper.getDatabaseName();
        String backupDBPath = databaseHelper.getDatabaseName();
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(getContext(), R.string.db_exported, Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private boolean importDB(String db_path) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this.getContext());

            File file=new File(db_path);
            InputStream mInputStream = new DataInputStream(new FileInputStream(file));
            String outFileName = Objects.requireNonNull(getContext()).getDatabasePath(databaseHelper.getDatabaseName()).getAbsolutePath();
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
            databaseHelper.onUpgrade(SQLiteDatabase.openDatabase(db_path,null, SQLiteDatabase.OPEN_READWRITE),0,1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }

    }



    @Override
    public void onActivityResult(int requestCode ,int resultCode,Intent data) {

        if(requestCode==1 && resultCode ==RESULT_OK && data!=null && data.getData()!=null){
            pathDB = "/storage/emulated/0/" + data.getData().getPath().substring(15);
            if (importDB(pathDB)){
                Toast.makeText(getContext(), R.string.db_imported,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.db_not_imported,Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode,data);
    }

}