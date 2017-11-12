package ca.bcit.ass3.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private ArrayList<PartyDetail> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Button onClick listener
        Button btnSearch = (Button) findViewById(R.id.searchEvent);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearchEvent(view);
            }
        });
    }

    public void onClickSearchEvent(View v) {
        DbHelper helper = new DbHelper(SearchResultsActivity.this);

        // get the user input event keyword
        EditText keywordInput = (EditText) findViewById(R.id.keywordInput);
        String eventKeyword = keywordInput.getText().toString();

        // find event in database
        result = helper.getSimilarEvents(eventKeyword);
        ListView lv = (ListView) findViewById(R.id.resultList);

        ArrayAdapter<PartyDetail> arrayAdapter = new ArrayAdapter<PartyDetail>(
                this, android.R.layout.simple_list_item_1, result
        );
        //EventsAdapter adapter = new EventsAdapter(SearchResultsActivity.this, result);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchResultsActivity.this, EventDetailsActivity.class);
                intent.putExtra("eventID", result.get(i).get_eventId());
                startActivity(intent);
            }
        });
    }
}
