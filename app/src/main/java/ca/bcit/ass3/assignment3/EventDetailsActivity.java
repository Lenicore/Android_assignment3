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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);


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

    private PartyDetail getEvents(String pty) {
        PartyDetail events = null;
        SQLiteOpenHelper helper = new DbHelper(this);
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("Event_Master",
                    new String[]{"Name", "Date", "Time", "IMAGE_RESOURCE_ID"},
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
            String msg = "[CountryDetailsActivity/getCountry] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return events;
    }

    public void onClick(View v)
    {
        String details = getIntent().getExtras().get("event").toString();
        new UpdatePartyTask().execute(details);
    }

    private class UpdatePartyTask extends AsyncTask<String, Void, Boolean> {

        private ContentValues partyValues;


        @Override
        protected void onPreExecute() {
            EditText name = (EditText) findViewById(R.id.item_name);
            EditText unit = (EditText) findViewById(R.id.unit);
            EditText qtity = findViewById(R.id.quantity);
            String item_name = name.getText().toString();
            String unite = unit.getText().toString();
            int quantity = Integer.parseInt(qtity.getText().toString());
            partyValues = new ContentValues();
            //partyValues.put("FAVORITE", fav.isChecked());
        }

        @Override
        protected Boolean doInBackground(String... events) {
            String eventName = events[0];
            SQLiteOpenHelper helper = new DbHelper(EventDetailsActivity.this);
            try {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.update("Event_Details", partyValues,
                        "Name = ?", new String[]{eventName});
                db.close();
                return true;
            } catch (SQLiteException sqle) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast t = Toast.makeText(EventDetailsActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                t.show();
            }
        }

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