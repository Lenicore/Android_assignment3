package ca.bcit.ass3.assignment3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static ca.bcit.ass3.assignment3.DbHelper.Event_Detail;

public class AddEventActivity extends AppCompatActivity {

    private Button SaveEvent;
    private EditText Name;
    private EditText Date;
    private EditText Time;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SaveEvent = (Button) findViewById(R.id.saveEvent);
        Name = (EditText) findViewById(R.id.name);
        Date = (EditText) findViewById(R.id.date);
        Time = (EditText) findViewById(R.id.time);

        SaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PartyDetail event = new PartyDetail(Name.getText().toString(),Date.getText().toString(),Time.getText().toString());


                DbHelper helper = new DbHelper(getApplicationContext());
                db = helper.getReadableDatabase();
                helper.insertEvents(db,event);
                finish();
            }
        });
    }

}
