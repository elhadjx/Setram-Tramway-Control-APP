package mr.elhadj.tramwaytransport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SetramDB.db";
    private static final String AGENT_TABLE = "Agent";
    private static final String CARD_TABLE = "Card";
    private static final String VEHICLE_TABLE = "Vehicle";
    private static final String STOP_TABLE = "Stop";
    private static final String SUBSCRIPTION_TABLE = "Subscription";
    private static final String LOGS_TABLE = "Logs";
    private static final String REPORTS_TABLE = "Reports";


    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateCardTable  = "CREATE TABLE " + CARD_TABLE  + "(id INTEGER PRIMARY KEY, cardNumber TEXT, isBlacklisted TEXT NOT NULL DEFAULT '0', ExpDate TEXT NOT NULL, subscription TEXT)";
        String CreateAgentTable = "CREATE TABLE " + AGENT_TABLE + "(id INTEGER PRIMARY KEY, ag_username TEXT, ag_password TEXT, ag_fullname TEXT)";
        String CreateVehicleTable = "CREATE TABLE " + VEHICLE_TABLE + "(id INTEGER PRIMARY KEY, vehicle_code TEXT)";
        String CreateStopTable = "CREATE TABLE " + STOP_TABLE + "(id INTEGER PRIMARY KEY, stop_name TEXT)";
        String CreateSubscriptionTable = "CREATE TABLE " + SUBSCRIPTION_TABLE + "(id INTEGER PRIMARY KEY, subscription_name TEXT, subscription_delay INT)";
        String CreateLogsTable = "CREATE TABLE " + LOGS_TABLE + "(id INTEGER PRIMARY KEY, agent_id TEXT, card_id TEXT DEFAULT 'NULL', vehicle_id TEXT, stop_id TEXt, destination_id TEXT, act TEXT)";
        String CreateReportsTable = "CREATE TABLE " + REPORTS_TABLE + "(id INTEGER PRIMARY KEY, agent_id TEXT, reporttitle TEXT, reportmessage TEXT)";

        sqLiteDatabase.execSQL(CreateAgentTable);
        sqLiteDatabase.execSQL(CreateCardTable);
        sqLiteDatabase.execSQL(CreateVehicleTable);
        sqLiteDatabase.execSQL(CreateStopTable);
        sqLiteDatabase.execSQL(CreateSubscriptionTable);
        sqLiteDatabase.execSQL(CreateLogsTable);
        sqLiteDatabase.execSQL(CreateReportsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //DROP EXISTING TABLE
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AGENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VEHICLE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STOP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUBSCRIPTION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LOGS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REPORTS_TABLE);
        onCreate(sqLiteDatabase);
    }



    public boolean insertAgent(Agent agent){
        try {
            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //Create ContentValues
            ContentValues values1 = new ContentValues();
            values1.put("ag_username", agent.getAg_username());
            values1.put("ag_password", agent.getAg_password());
            values1.put("ag_fullname", agent.getAg_fullname());

            //Insert Data into Database
            sqLiteDatabase.insert(AGENT_TABLE, null, values1);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertCard(Card card){
        try {
            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //Create ContentValues
            ContentValues cardValues = new ContentValues();
            cardValues.put("cardNumber", card.getCardNumber());
            cardValues.put("ExpDate", card.getExpDate());
            cardValues.put("subscription",card.getSubscription());


            //Insert Data into Database
            sqLiteDatabase.insert(CARD_TABLE, null, cardValues);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertVehicle(Vehicle vehicle){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues vehicleValues = new ContentValues();
            vehicleValues.put("vehicle_code", vehicle.getVehicle_code());

            sqLiteDatabase.insert(VEHICLE_TABLE,null,vehicleValues);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertStop(Stop stop){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues stopValues = new ContentValues();
            stopValues.put("stop_name", stop.getStop_name());
            sqLiteDatabase.insert(STOP_TABLE,null,stopValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertSubscription(Subscription subscription){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues subscriptionValues = new ContentValues();
            subscriptionValues.put("subscription_name", subscription.getSubscription_name());
            subscriptionValues.put("subscription_delay", subscription.getSubscription_delay());
            sqLiteDatabase.insert(SUBSCRIPTION_TABLE,null,subscriptionValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertLog(Log log){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues logValues = new ContentValues();
            logValues.put("agent_id",log.getAgent_id());
            logValues.put("card_id",log.getCard_id());
            logValues.put("vehicle_id",log.getVehicle_id());
            logValues.put("stop_id",log.getStop_id());
            logValues.put("destination_id",log.getDestination_id());
            logValues.put("act",log.getAct());
            sqLiteDatabase.insert(LOGS_TABLE,null,logValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertReport(Report report){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues reportValues = new ContentValues();
            reportValues.put("agent_id",report.getAgent_id());
            reportValues.put("reporttitle",report.getReportTitle());
            reportValues.put("reportmessage",report.getReportMessage());
            sqLiteDatabase.insert(REPORTS_TABLE,null,reportValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAgent(Agent agent){
        try {
            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //sql string
            String sqlDelete = "UPDATE " + AGENT_TABLE + " SET ag_username = '" + agent.getAg_username()+ "' , ag_password = '" + agent.getAg_password() + "', ag_fullname = '" + agent.getAg_fullname() + "' WHERE id = " + agent.getId();

            //Delete Data from Database
            sqLiteDatabase.execSQL(sqlDelete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCard(Card card){
        try {
            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //sql string
            String sqlUpdate = "UPDATE " + CARD_TABLE + " SET isBlacklisted = '" + card.getIsBlacklisted()+ "', ExpDate = '" + card.getExpDate()  + "', subscription = '" + card.getSubscription() + "' WHERE id = " + card.getId()+ ";";

            //Delete Data from Database
            sqLiteDatabase.execSQL(sqlUpdate);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateVehicle(Vehicle vehicle){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            String sqlUpdate = "UPDATE " + VEHICLE_TABLE+ " SET vehicle_code = '" + vehicle.getVehicle_code() + "' WHERE id = " + vehicle.getId() + ";";
            sqLiteDatabase.execSQL(sqlUpdate);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStop(Stop stop){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlUpdate = "UPDATE " + STOP_TABLE + " SET stop_name = '" + stop.getStop_name() + "' WHERE id = " + stop.getId() + ";";
            sqLiteDatabase.execSQL(sqlUpdate);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSubscription(Subscription subscription){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlUpdate = "UPDATE " + SUBSCRIPTION_TABLE+ " SET subscription_name = '" + subscription.getSubscription_name() + "', subscription_delay = '" + subscription.getSubscription_delay() + "' WHERE id = " + subscription.getId() + ";";
            sqLiteDatabase.execSQL(sqlUpdate);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAgent(Agent agent){
        try {

            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //sql string
            String sqlDelete = "DELETE FROM " + AGENT_TABLE + " WHERE id = " + agent.getId() + ";";

            //Delete Data from Database
            sqLiteDatabase.execSQL(sqlDelete);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCard(Card card){
        try {
            //Get Writeable Database
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            //sql string
            String sqlDelete = "DELETE FROM " + CARD_TABLE + " WHERE cardNumber = '" + card.getCardNumber() + "';";

            //Delete Data from Database
            sqLiteDatabase.execSQL(sqlDelete);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicle(Vehicle vehicle){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlDelete = "DELETE FROM " + VEHICLE_TABLE + " WHERE vehicle_code = '" + vehicle.getVehicle_code() + "';";
            sqLiteDatabase.execSQL(sqlDelete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStop(Stop stop){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlDelete = "DELETE FROM " + STOP_TABLE + " WHERE stop_name = '" + stop.getStop_name() + "';";
            sqLiteDatabase.execSQL(sqlDelete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubscription(Subscription subscription){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlDelete = "DELETE FROM " + SUBSCRIPTION_TABLE + " WHERE subscription_name = '" + subscription.getSubscription_name() + "';";
            sqLiteDatabase.execSQL(sqlDelete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReport(Report report){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String sqlDelete = "DELETE FROM " + REPORTS_TABLE + " WHERE id = " + report.getId() + ";";
            sqLiteDatabase.execSQL(sqlDelete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean blacklistCard(String cardNumber){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("UPDATE " + CARD_TABLE + " SET isBlacklisted = '1' WHERE cardNumber = '" + cardNumber + "';" );
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        return true;
    }

    public boolean whitelistCard(String cardNumber){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            //sqLiteDatabase.update(CARD_TABLE,values1,"cardNumber= '" + cardNumber + "';",null);
            sqLiteDatabase.execSQL("UPDATE " + CARD_TABLE + " SET isBlacklisted = '0' WHERE cardNumber = '" + cardNumber + "';" );
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        return true;
    }

    public ArrayList getData( String table, String column){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String>  arrayList= new ArrayList<>();

        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ table,null); // change this
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex(column))); //change this
            cursor.moveToNext();
        }
        return arrayList;
    }

    public String getAgentTable() {
        return AGENT_TABLE;
    }

    public String getCardTable() {
        return CARD_TABLE;
    }

    public String getVehicleTable() {
        return VEHICLE_TABLE;
    }

    public String getStopTable() {
        return STOP_TABLE;
    }

    public String getSubscriptionTable() {
        return SUBSCRIPTION_TABLE;
    }

    public String getLogsTable() {
        return LOGS_TABLE;
    }

    public static String getReportsTable() {
        return REPORTS_TABLE;
    }

    /*public String getDataWhere( String table, String column, String WhereByColumn, String WhereByValue){
        //Get Readable Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + column + " FROM " + table + " WHERE " + WhereByColumn + " = " + WhereByValue,null);
        return cursor.getString(0);
    }
    public String[] getRow(String table, String SearchbyColumnName, String SearchbyColumnValue){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + table + " WHERE " + SearchbyColumnName + " = '" + SearchbyColumnValue + "';",null);
        String[] row = new String[cursor.getColumnCount()];
        for(int i=0; i < cursor.getColumnCount(); i++){
            row[i]=cursor.getString(i);
        }
        return row;

    }*/
}
