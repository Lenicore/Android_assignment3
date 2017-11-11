package ca.bcit.ass3.assignment3;

import android.content.Context;
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
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPartiesListView();

        addEvent = (Button) findViewById(R.id.addEvent);
    }

    // Original method
    private void setupPartiesListView() {
        ListView list_parties = (ListView) findViewById(R.id.list_events);

        String[] events = getEvents();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, events
        );

        list_parties.setAdapter(arrayAdapter);

        list_parties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String event_name = tv.getText().toString();


                Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
                intent.putExtra("event", event_name);

                startActivity(intent);
            }
        });
    }




    private String[] getEvents() {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        //setupFavoriteCountriesListView();
    }
}
