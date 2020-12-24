package mr.elhadj.tramwaytransport;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;


public class MainActivity extends FragmentActivity {

    public String tagID = "";
    public Boolean isLogged = false;
    public Boolean adminAccess = false;
    public String titleAFtoRF = "";
    public String idAFtoRF = "";
    public String messageAFtoRF = "";
    public String reportID_AF_to_RF = "";

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }



    public BottomNavigationView bnv;
    public DatabaseHelper databaseHelper;
    private Integer LoggedInId;
    private Vehicle selectedVehicle;
    private Stop selectedStop;
    private Stop selectedDestination;

    // list of NFC technologies detected:
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();
        databaseHelper = new DatabaseHelper(this);




        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navListener);

        bnv.setVisibility(View.GONE);
        setTheme(R.style.MaterialTheme);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new loginFragment()).commit();



    }
    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        try {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.nfcunavailable,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        try {
            nfcAdapter.disableForegroundDispatch(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {

            //Toast.makeText(getApplicationContext(), "NFC Tag ID: \n" + tagID, Toast.LENGTH_SHORT).show();
            if (isLogged){
                if (adminAccess){
                    tagID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
                    ((EditText) findViewById(R.id.et_cardNumber)).setText(tagID);
                } else {

                    if (bnv.getSelectedItemId() != R.id.nav_card){
                        tagID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

                    } else {
                        tagID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
                        ((Button) findViewById(R.id.btn_Scan)).performClick();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.login_first, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void setLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()){
                case R.id.nav_card:
                    selectedFragment = new cardFragment();
                    break;
                case R.id.nav_map:
                    selectedFragment = new locationFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new profileFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new settingsFragment();
                    break;
                default:
                    throw new IllegalStateException(getString(R.string.unexpected_value_two_dots) + item.getItemId());
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
            return true;

        }
    };

    public Context getContext() {
        return getApplicationContext();
    }

    public Integer getLoggedInId() {
        return LoggedInId;
    }

    public void setLoggedInId(Integer loggedInId) {
        LoggedInId = loggedInId;
    }


    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public Stop getSelectedStop() {
        return selectedStop;
    }

    public void setSelectedStop(Stop selectedStop) {
        this.selectedStop = selectedStop;
    }

    public Stop getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(Stop selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean islogged) {
        isLogged = islogged;
        if (!isLogged){
            setTheme(R.style.MaterialTheme);
        }
    }

    public Boolean getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
        if (adminAccess){
            setTheme(R.style.MaterialTheme);
        }
    }
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        try {
            Resources res = context.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}