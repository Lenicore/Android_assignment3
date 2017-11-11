package ca.bcit.ass3.assignment3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDetailsActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private Button addItems;
    private PartyDetail event;
    private ItemsDetails eventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setupEventListView();

        event = new PartyDetail("","","");
        eventDetail = new ItemsDetails("","",0);
        addItems = (Button)findViewById(R.id.addItem);

        String getEventId = getIntent().getExtras().get("event").toString();
        eventDetail = getEventDetail(getEventId);

        findViewById(R.id.addItem).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailsActivity.this, AddItemActivity.class);
                //hard coded ID
                intent.putExtra("eventId", "3");

                startActivity(intent);
            }
        });

        String events = getIntent().getExtras().get("event").toString();
        PartyDetail party = getEvents(events);

        // Populate the party name
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(party.get_name());

        // populate the party date
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(party.get_date());

        // populate the party time
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(party.get_time());



    }

    private void setupEventListView() {
        DbHelper dbHelper = new DbHelper(this);
        ListView list_parties = (ListView) findViewById(R.id.eventDetails);

        //ItemsDetails details = getItems(1);
       // String[] details = getItems(1);
        String[] details = dbHelper.getItems(1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, details
        );

        list_parties.setAdapter(arrayAdapter);

    }

    private PartyDetail getEvents(String pty) {
        PartyDetail events = null;
        SQLiteOpenHelper helper = new DbHelper(this);
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("Event_Master",
                    new String[]{"Name", "Date", "Time"},
                    "Name = ?",
                    new String[]{pty},
                    null, null, null);


            // move to the first record
            if (cursor.moveToFirst()) {
                do {
                    // get the data into array, or class variable
                    events = new PartyDetail(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetailsActivity/getEvents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return events;
    }

    private ItemsDetails getEventDetail(String items) {
        ItemsDetails event = null;
        SQLiteOpenHelper helper = new DbHelper(this);
        List<String> itemDetails = new ArrayList<>();

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select ItemName, ItemUnit, ItemQuantity, eventId from Event_Detail where eventId in (select eventId from Event_Master where NAME = ?)",
                    new String[] {items});

            // move to the first record
            if (cursor.moveToFirst()) {
                // get the country details from the cursor
                do {
                    String temp = "Item: " + cursor.getString(0);
                    temp += "\nUnit: " + cursor.getString(1);
                    temp += "\nQuantity: " + cursor.getInt(2);
                    itemDetails.add(temp);
                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, itemDetails
            );
            ListView itemDetailList = (ListView) findViewById(R.id.eventDetails);
            itemDetailList.setAdapter(arrayAdapter);
            itemDetailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString();
                    Pattern pattern = Pattern.compile("'(.*?)'");
                    Matcher matcher = pattern.matcher(name);
                    if (matcher.find())
                    {
                        //deleteItem(matcher.group(1));
                    }
                    Log.d("Event Detail", "name is: " + matcher.group(1));
                    Log.d("Event Detail", "number is " + i + " and " + l);
                    //deleteItem(l+1);
                }
            });
        } catch (SQLiteException sqlex) {
            String msg = "[EventActivity/getEvent] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return event;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }
}
