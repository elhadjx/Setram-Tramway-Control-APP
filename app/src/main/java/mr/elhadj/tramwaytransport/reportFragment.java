package mr.elhadj.tramwaytransport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class reportFragment extends Fragment {

    TextView tv_reportTitle, tv_reporter, tv_reportMessage;
    Button btn_return, btn_deleteReportRF;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public reportFragment() {
        // Required empty public constructor
    }
    public static reportFragment newInstance(String param1, String param2) {
        reportFragment fragment = new reportFragment();
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
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        final DatabaseHelper databaseHelper = new DatabaseHelper(this.getContext());
        tv_reportTitle = v.findViewById(R.id.tv_reportTitleReportFragment);
        tv_reporter = v.findViewById(R.id.tv_reportAgentReportFragment);
        tv_reportMessage = v.findViewById(R.id.tv_reportMessageReportFragment);
        btn_return = v.findViewById(R.id.btn_returnToAF);
        btn_deleteReportRF = v.findViewById(R.id.btn_deleteReportRF);

        final String reportID = ((MainActivity) Objects.requireNonNull(getActivity())).reportID_AF_to_RF;


        ArrayList<Report> reportArrayList = new ArrayList<>();
        final ArrayList<String> report_id = databaseHelper.getData(databaseHelper.getReportsTable(),"id");
        final ArrayList<String> report_agent_id = databaseHelper.getData(databaseHelper.getReportsTable(),"agent_id");
        final ArrayList<String> report_title = databaseHelper.getData(databaseHelper.getReportsTable(),"reporttitle");
        final ArrayList<String> report_message = databaseHelper.getData(databaseHelper.getReportsTable(),"reportmessage");

        for (int i = 0; i < report_id.size(); i++) {
            Report reportToAdd = new Report(report_id.get(i),report_agent_id.get(i),report_title.get(i),report_message.get(i));
            reportArrayList.add(reportToAdd);
        }
        final int reportIndex = report_id.indexOf(reportID);
        try{

            ArrayList<String> ag_id = databaseHelper.getData(databaseHelper.getAgentTable(),"id");
            ArrayList<String> ag_fullname = databaseHelper.getData(databaseHelper.getAgentTable(),"ag_fullname");


            tv_reportTitle.setText(report_title.get(reportIndex));
            tv_reporter.setText(getString(R.string.from_two_dots) + ag_fullname.get(ag_id.indexOf(report_agent_id.get(reportIndex))));
            tv_reportMessage.setText(report_message.get(reportIndex));
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        btn_deleteReportRF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteReport(new Report(reportID,report_agent_id.get(reportIndex),report_title.get(reportIndex),report_message.get(reportIndex)));
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new adminFragment()).commit();
                ((MainActivity) getActivity()).setIsLogged(true);
                ((MainActivity) getActivity()).setAdminAccess(true);
                Toast.makeText(getContext(), R.string.report_deleted,Toast.LENGTH_SHORT).show();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new adminFragment()).commit();
                ((MainActivity) getActivity()).setIsLogged(true);
                ((MainActivity) getActivity()).setAdminAccess(true);
            }
        });


        return v;
    }
}