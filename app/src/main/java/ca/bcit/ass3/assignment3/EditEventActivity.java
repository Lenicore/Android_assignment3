package ca.bcit.ass3.assignment3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditEventActivity extends AppCompatActivity {
    DbHelper helper = new DbHelper(EditEventActivity.this);
    private int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        eventID = (Integer) getIntent().getExtras().get("eventID");


        PartyDetail event = helper.getEvent(eventID);

        EditText eventNameInput = (EditText) findViewById(R.id.eventNameInput);
        eventNameInput.setText(event.get_name());

        EditText eventDateInput = (EditText) findViewById(R.id.eventDateInput);
        eventDateInput.setText(event.get_date());

        EditText eventTimeInput = (EditText) findViewById(R.id.eventTimeInput);
        eventTimeInput.setText(event.get_time());


        // Button onClick listener
        Button btnSave = (Button) findViewById(R.id.updateEvent);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateEvent(view);
            }
        });
    }

    public void onClickUpdateEvent(View v) {
        // Get the user chosen rule
        EditText eventNameInput = (EditText) findViewById(R.id.eventNameInput);
        String eventName = eventNameInput.getText().toString();

        EditText eventDateInput = (EditText) findViewById(R.id.eventDateInput);
        String eventDate = eventDateInput.getText().toString();

        EditText eventTimeInput = (EditText) findViewById(R.id.eventTimeInput);
        String eventTime = eventTimeInput.getText().toString();


        // Update data in database
        PartyDetail event = new PartyDetail(eventName, eventDate, eventTime);
        helper.updateEvent(event, eventID);
        finish();
    }
}
