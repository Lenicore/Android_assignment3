package ca.bcit.ass3.assignment3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DbHelper myDb;
    SQLiteDatabase db;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myDb = new DbHelper(this);
        String Events = getMaster();

        tv = (TextView)findViewById(R.id.textView);
        tv.setText(Events);
       // viewAllEventMaster();
    }


    public void viewAllEventMaster(){

         Cursor res = myDb.GetMaster();
        //Cursor res = myDb.rawQuery("select * from Event_Master",null);
       // res.moveToFirst();
        if(res.getCount() == 0){
            Toast.makeText(this,"Nothing found",Toast.LENGTH_SHORT).show();
        }

        StringBuffer buffer = new StringBuffer();

       /* while (res.moveToNext()){
            buffer.append("Name :" + res.getString(1) +"\n");
            buffer.append("Date :" + res.getString(2) +"\n");
            buffer.append("Time :" + res.getString(3) +"\n\n");
        }
        */

        do{

            buffer.append("Name :" + res.getString(1) +"\n");
            buffer.append("Date :" + res.getString(2) +"\n");
            buffer.append("Time :" + res.getString(3) +"\n\n");

        } while (res.moveToNext());

        tv = (TextView)findViewById(R.id.textView);
        tv.setText(buffer.toString());
    }

    private String getMaster() {
        SQLiteOpenHelper helper = new DbHelper(this);
        String Events = "";
        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select *  from Event_Master", null);

           // int count = cursor.getCount();
           // Events = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    Events += cursor.getString(1) + cursor.getString(2) +cursor.getString(3) +"\n";
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return Events;
    }


}
