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

public class AddItemActivity extends AppCompatActivity {

    private Button SaveItem;
    private EditText ItemUnit;
    private EditText ItemName;
    private EditText ItemQuantity;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SaveItem = (Button) findViewById(R.id.saveItem);
        ItemName = (EditText) findViewById(R.id.ItemName);
        ItemUnit = (EditText) findViewById(R.id.ItemUnit);
        ItemQuantity = (EditText) findViewById(R.id.ItemQuantity);



        SaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ItemName.getText().toString().equals("")) {
                    int IQuantity = Integer.parseInt(ItemQuantity.getText().toString());

                    String pkID = getIntent().getStringExtra("eventId");
                    ItemsDetails id = new ItemsDetails(ItemName.getText().toString(), ItemUnit.getText().toString(), IQuantity, Integer.parseInt(pkID));
                    insertDetails(id);
                }

            }
        });
    }

    private void insertDetails (ItemsDetails event){
        SQLiteOpenHelper helper = new DbHelper(this);
        db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("ItemName", event.get_name());
        values.put("ItemUnit", event.get_unit());
        values.put("ItemQuantity", event.getQuantity());
        values.put("eventId", event.getEventID());

        db.insert("Event_Detail", null, values);

    }
}
