package ca.bcit.ass3.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by nelson on 11/2/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Asn31.sqlite";
    private static final String EventMaster = "Event_Master";
    private static final String EventDetail = "Event_Detail";
    private Context context;

    private static final int DB_VERSION = 3;

    public DbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public Cursor GetMaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + EventMaster,null);
        return res;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(getCreateEventMasterTableSql());
//        sqLiteDatabase.execSQL(getCreateEventDetailTableSql());
//
//        ContentValues initialValues1 = new ContentValues();
//        initialValues1.put("NAME", "Halloween Party");
//        initialValues1.put("Date", "Oct 30, 2017");
//        initialValues1.put("Time", "6:30 PM");
//
//        ContentValues initialValues2 = new ContentValues();
//        initialValues2.put("NAME", "Christmas Party");
//        initialValues2.put("Date", "December 20, 2017");
//        initialValues2.put("Time", "12:30 PM");
//
//        ContentValues initialValues3 = new ContentValues();
//        initialValues3.put("NAME", "New Year Eve");
//        initialValues3.put("Date", "December 31, 2017");
//        initialValues3.put("Time", "8:00 PM");
//
//        sqLiteDatabase.insert(EventMaster,null,initialValues1);
//        sqLiteDatabase.insert(EventMaster,null,initialValues2);
//        sqLiteDatabase.insert(EventMaster,null,initialValues3);
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(getCreateEventMasterTableSql());
                db.execSQL(getCreateEventDetailTableSql());
                for (PartyDetail p : northAmericaCountries) {
                    insertParty(db, p);
                }
            }

            if (oldVersion < 2)
                db.execSQL("ALTER TABLE COUNTRY ADD COLUMN POPULATION NUMERIC;");

            if (oldVersion < 3)
                db.execSQL("ALTER TABLE COUNTRY ADD COLUMN FAVORITE NUMERIC;");

        } catch (SQLException sqle) {
            String msg = "[MyPlanetDbHelper / updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }


    private String getCreateEventMasterTableSql(){
        String sql ="";
        sql += "CREATE TABLE " +EventMaster +"(";
        sql += "_eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT,";
        sql += "Date TEXT,";
        sql += "Time TEXT);";
        return sql;
    }


    private String getCreateEventDetailTableSql(){
        String sql ="";
        sql += "CREATE TABLE " +EventDetail+ "(";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "ItemName TEXT,";
        sql += "ItemUnit INTEGER,";
        sql += "ItemQuantity INTEGER,";
        sql += "eventId INTEGER, FOREIGN KEY(eventId) REFERENCES Event_Master(eventId));";
        return sql;
    }

    private void insertParty(SQLiteDatabase db, PartyDetail party) {
        ContentValues values = new ContentValues();
        values.put("Party Name", party.get_name());
        values.put("Date", party.get_date());
        values.put("Date", party.get_time());
        values.put("IMAGE_RESOURCE_ID", party.getImageResourceId());

        db.insert("PARTY", null, values);
    }

    private static final PartyDetail[] northAmericaCountries = {
            new PartyDetail("Halloween Party", "Oct 30, 2017", "6:30 PM", R.drawable.happyhalloween),
            new PartyDetail("Christmas Party", "December 20, 2017", "12:30 PM", R.drawable.happyhalloween),
            new PartyDetail("New Year Eve", "December 31, 2017", "8:00 PM", R.drawable.happyhalloween),
    };

}
