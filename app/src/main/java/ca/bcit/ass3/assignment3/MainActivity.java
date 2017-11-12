package ca.bcit.ass3.assignment3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    SQLiteDatabase db;
    Cursor cursor;
    Button addEvent;
    Button deleteEvent;
    Spinner spinner;
    ArrayList<PartyDetail> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPartiesListView();

        // get all events and store in ArrayList
        //getEvents(events);

        // button elements
        addEvent = (Button) findViewById(R.id.addEvent);
        deleteEvent = (Button) findViewById(R.id.deleteEvent);

        // add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener();

        // Capture button clicks
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        AddEventActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.event_create:
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(intent);
                return true;
            case R.id.event_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchResultsActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


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

                //int id = Integer.parseInt(getIntent().getStringExtra("eventId"));


                Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
                intent.putExtra("event", event_name);
                //intent.putExtra("eventId", id);

                startActivity(intent);
            }
        });
    }

//    private void setupPartiesListView() {
//        ListView list_parties = (ListView) findViewById(R.id.list_events);
//
//        ArrayAdapter<PartyDetail> arrayAdapter = new ArrayAdapter<PartyDetail>(this, android.R.layout.simple_list_item_1, events);
//
//        list_parties.setAdapter(arrayAdapter);
//
//        list_parties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tv = (TextView) view;
//                String event_name = tv.getText().toString();
//
//                //int id = Integer.parseInt(getIntent().getStringExtra("eventId"));
//
//
//                Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
//                intent.putExtra("event", event_name);
//                //intent.putExtra("eventId", id);
//
//                startActivity(intent);
//            }
//        });
//    }
//
//    public void itemClicked(int eventID) {
//        View fragmenyContainer = findViewById(R.id.list_frag);
//        if (fragmenyContainer != null) {
//            ItemsDetails.EvFragment details = new EvFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            details.setEventID(eventID);
//            ft.replace(R.id.list_frag, details);
//            ft.addToBackStack(null);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.commit();
//        } else {
//            Intent i = new Intent(this, EventDetailsActivity.class);
//            i.putExtra("eventID", eventID);
//            startActivity(i);
//        }
//    }

//    private void getEvents(ArrayList<PartyDetail> event) {
//        SQLiteOpenHelper helper = new DbHelper(this);
//
//        try {
//            db = helper.getReadableDatabase();
//            cursor = db.rawQuery("select Name from Event_Master", null);
//
//            if (cursor.moveToFirst()) {
//
//                do {
//                    PartyDetail temp = new PartyDetail(cursor.getString(0),
//                            cursor.getString(1),
//                            cursor.getString(2),
//                            cursor.getInt(3));
//                    event.add(temp);
//                } while (cursor.moveToNext());
//            }
//        } catch (SQLiteException sqlex) {
//            String msg = "[MainActivity / getParties] DB unavailable";
//            msg += "\n\n" + sqlex.toString();
//
//            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
//            t.show();
//        }
//
//    }


    private String[] getEvents() {
        SQLiteOpenHelper helper = new DbHelper(this);
        String[] parties = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select Name from Event_Master", null);

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

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        DbHelper db = new DbHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> events = db.getAllEvents();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, events);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPartiesListView();
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
        setupPartiesListView();
    }


}
