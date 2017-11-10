package ca.bcit.ass3.assignment3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public DbHelper myDb;
    public SQLiteDatabase db;
    private Cursor cursor;

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

    private void setupPartyListView() {
        ListView list_partys = (ListView) findViewById(R.id.list_continents);

        String[] continents = getParties();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, continents
        );

        list_partys.setAdapter(arrayAdapter);

        list_partys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String continent = tv.getText().toString();

                Intent intent = new Intent(MainActivity.this, PartyDetail.class);
                intent.putExtra("continent", continent);

                startActivity(intent);
            }
        });
    }

    private String[] getParties() {
        SQLiteOpenHelper helper = new DbHelper(this);
        String[] parties = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select DISTINCT Name from Event_Master", null);

            int count = cursor.getCount();
            parties = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    parties[ndx++] = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getParties] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return parties;
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
