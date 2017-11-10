package ca.bcit.ass3.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nelson on 11/2/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Asn31.sqlite";
    private static final String EventMaster = "Event_Master";
    private static final String EventDetail = "Event_Detail";

    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Cursor GetMaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + EventMaster,null);
        return res;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateEventMasterTableSql());
        sqLiteDatabase.execSQL(getCreateEventDetailTableSql());

        ContentValues initialValues1 = new ContentValues();
        initialValues1.put("NAME", "Halloween Party");
        initialValues1.put("Date", "Oct 30, 2017");
        initialValues1.put("Time", "6:30 PM");

        ContentValues initialValues2 = new ContentValues();
        initialValues2.put("NAME", "Christmas Party");
        initialValues2.put("Date", "December 20, 2017");
        initialValues2.put("Time", "12:30 PM");

        ContentValues initialValues3 = new ContentValues();
        initialValues3.put("NAME", "New Year Eve");
        initialValues3.put("Date", "December 31, 2017");
        initialValues3.put("Time", "8:00 PM");

        sqLiteDatabase.insert(EventMaster,null,initialValues1);
        sqLiteDatabase.insert(EventMaster,null,initialValues2);
        sqLiteDatabase.insert(EventMaster,null,initialValues3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventMaster);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventDetail);
        onCreate(sqLiteDatabase);
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



}
