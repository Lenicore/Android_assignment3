package ca.bcit.ass3.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/2/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Asn33.sqlite";
    public static Context context;
    public static final String Event_Master = "Event_Master";
    public static final String Event_Detail = "Event_Detail";

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
        // Drop older table if existed
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Event_Master);
        db.execSQL("DROP TABLE IF EXISTS " + Event_Detail);
        updateMyDatabase(sqLiteDatabase, i, i1);

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(getCreateEventMasterTableSql());
                db.execSQL(getCreateEventDetailTableSql());
                for (PartyDetail p : events) {
                    insertEvents(db, p);
                }

                for(ItemsDetails it: foods) {
                    insertItems(db,it, 1);
                    insertItems(db,it, 2);
                    insertItems(db,it, 3);
                }
            }

        } catch (SQLException sqle) {
            String msg = "[MyPlanetDbHelper / updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public PartyDetail getEvent(int _EVENTID) {
        PartyDetail event = null;

        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Event_Master WHERE _EVENTID = "
                    +_EVENTID, null);

            // move to the first record
            if (cursor.moveToFirst()) {
                // get the country details from the cursor
                event  = new PartyDetail(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)
                );
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MyDbHelper/getEvent] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return  event;
    }

    /**
     * Getting all events
     * returns list of events
     * */
    public List<String> getAllEvents(){
        List<String> events = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Event_Master;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                events.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning events
        return events;
    }

    /**
     * Getting all items
     * returns list of items
     * */
    public void getAllItems(ArrayList<ItemsDetails> items, int _EVENTID){
        SQLiteDatabase db = getReadableDatabase();

        try {
            db = getReadableDatabase();
            Cursor cursor= db.rawQuery("select * from Event_Detail where _EVENTID = "
                    + _EVENTID, null);

            if (cursor.moveToFirst()) {

                do {
                    ItemsDetails oneItem = new ItemsDetails(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            _EVENTID
                    );
                    items.add(oneItem);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MyDbHelper / getItems] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public ArrayList<PartyDetail> getSimilarEvents(String keyWord) {
        ArrayList<PartyDetail> event = new ArrayList<PartyDetail>();

        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor= db.rawQuery("select * from Event_Master WHERE NAME LIKE '%"
                    + keyWord + "%' ;", null);

            if (cursor.moveToFirst()) {
                do {
                    PartyDetail oneEvent = new PartyDetail(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3));
                    event.add(oneEvent);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[DbHelper / getSimilarEvents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return event;
    }

    private String getCreateEventMasterTableSql(){
        String sql ="";
        sql += "CREATE TABLE Event_Master (";
        sql += "eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT,";
        sql += "Date TEXT,";
        sql += "Time TEXT);";

        return sql;
    }

    public void insertEvents(SQLiteDatabase db, PartyDetail event) {
        ContentValues values = new ContentValues();
        values.put("Name", event.get_name());
        values.put("Date", event.get_date());
        values.put("Time", event.get_time());

        db.insert("Event_Master", null, values);
    }

    private String getCreateEventDetailTableSql(){
        String sql ="";
        sql += "CREATE TABLE Event_Detail (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "ItemName TEXT,";
        sql += "ItemUnit TEXT,";
        sql += "ItemQuantity INTEGER,";
        sql += "eventId INTEGER, FOREIGN KEY(eventId) REFERENCES Event_Master(eventId));";
        return sql;
    }

    public void insertItems(SQLiteDatabase db, ItemsDetails food, int _eventId ) {
        ContentValues values = new ContentValues();
        values.put("ItemName", food.get_name());
        values.put("ItemUnit", food.get_unit());
        values.put("ItemQuantity", food.getQuantity());
        values.put("eventId", _eventId);

        db.insert("Event_Detail", null, values);
    }

    public void updateEvent(PartyDetail event, int _EVENTID) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "UPDATE EVENT_MASTER SET " + "NAME = '" + event.get_name() + "', "
                + "DATE = '" + event.get_date() + "', " + "TIME = '" + event.get_time() + "' "
                + "WHERE _EVENTID = " + _EVENTID;

        db.execSQL(sql);
    }

    public void deleteEvent(SQLiteDatabase db, int _EVENTID) {
        String sql = "DELETE FROM Event_Master WHERE _EVENTID = " + _EVENTID;
        db.execSQL(sql);
    }

    public void updateItem(ItemsDetails item, int _DETAILID) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "UPDATE Event_Detail SET " + "ITEMNAME = '" + item.get_name() + "', "
                + "ITEMUNIT = '" + item.get_unit() + "', "
                + "ITEMQUANTITY = '" + item.getQuantity() + "' "
                + "WHERE _DETAILID = " + _DETAILID;
        db.execSQL(sql);
    }

    public void deleteItem(SQLiteDatabase db, int _DETAILID) {
        String sql = "DELETE FROM Event_Detail WHERE _DETAILID = " + _DETAILID;
        db.execSQL(sql);
    }

    private static final PartyDetail[] events = {
            new PartyDetail("Halloween Party", "Oct 30, 2017", "6:30 PM"),
            new PartyDetail("Christmas Party", "December 20, 2017", "12:30 PM"),
            new PartyDetail("New Year Eve", "December 31, 2017", "8:00 PM"),
    };

    public static final ItemsDetails[] foods = {
            new ItemsDetails("Paper plates", "Pieces", 20),
            new ItemsDetails("Paper cups", "Pieces", 30),
            new ItemsDetails("Napkins", "Pieces", 100),
            new ItemsDetails("Beer", "6 packs", 5),
            new ItemsDetails("Pop", "2 Liter bottles", 3),
            new ItemsDetails("Pizza", "Large", 3),
            new ItemsDetails("Peanuts", "Grams", 200),
    };

    public String[] getItems(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] details = null;

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM Event_Detail where eventId = "
            + id, null);
            int count = cursor.getCount();
            details = new String[count];

            if(cursor.moveToFirst()) {
                int ndx = 0;
                do {
                    details[ndx++] = cursor.getString(1) +
                            cursor.getString(2) +
                            cursor.getString(3);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[DbHelper/getItems] DB unavailable";
            msg += "\n\n" + sqlex.toString();

//            Toast t = Toast.makeText(EventDetailsActivity.class, msg, Toast.LENGTH_LONG);
//            t.show();
        }
        return details;
    }

}
