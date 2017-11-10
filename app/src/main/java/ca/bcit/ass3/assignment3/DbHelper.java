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
    private Context context;

    private static final int DB_VERSION = 3;

    public DbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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
                //db.execSQL(getCreateEventDetailTableSql());
                for (PartyDetail p : events) {
                    insertEvents(db, p);
                }
            }

            if (oldVersion < 2)
                db.execSQL("ALTER TABLE Event_Master ADD COLUMN POPULATION NUMERIC;");

            if (oldVersion < 3)
                db.execSQL("ALTER TABLE Event_Detail ADD COLUMN FAVORITE NUMERIC;");

        } catch (SQLException sqle) {
            String msg = "[MyPlanetDbHelper / updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }


    private String getCreateEventMasterTableSql(){
        String sql ="";
        sql += "CREATE TABLE EventMaster (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT,";
        sql += "Date TEXT,";
        sql += "Time TEXT);";

        return sql;
    }

    private void insertEvents(SQLiteDatabase db, PartyDetail event) {
        ContentValues values = new ContentValues();
        values.put("Name", event.get_name());
        values.put("Date", event.get_date());
        values.put("Time", event.get_time());

        db.insert("Event_Master", null, values);
    }

    private String getCreateEventDetailTableSql(){
        String sql ="";
        sql += "CREATE TABLE EventDetail (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "ItemName TEXT,";
        sql += "ItemUnit INTEGER,";
        sql += "ItemQuantity INTEGER,";
        sql += "eventId INTEGER, FOREIGN KEY(eventId) REFERENCES Event_Master(eventId));";
        return sql;
    }

    private void insertItems(SQLiteDatabase db, PartyDetail party) {
        ContentValues values = new ContentValues();
        values.put("ItemName", party.get_name());
        values.put("ItemUnit", party.get_date());
        values.put("ItemQuantity", party.get_time());

        db.insert("Event_Detail", null, values);
    }

    private static final PartyDetail[] events = {
            new PartyDetail("Halloween Party", "Oct 30, 2017", "6:30 PM"),
            new PartyDetail("Christmas Party", "December 20, 2017", "12:30 PM"),
            new PartyDetail("New Year Eve", "December 31, 2017", "8:00 PM"),
    };

    public ItemsDetails[] foods = {

    };
}
