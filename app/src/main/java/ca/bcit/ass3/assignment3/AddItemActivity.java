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
    private int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SaveItem = (Button) findViewById(R.id.saveItem);
        ItemName = (EditText) findViewById(R.id.ItemName);
        ItemUnit = (EditText) findViewById(R.id.ItemUnit);
        ItemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        eventID = (Integer) getIntent().getExtras().get("eventID");


        SaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper helper = new DbHelper(getApplicationContext());
                db = helper.getReadableDatabase();

                ItemsDetails id = new ItemsDetails(ItemName.getText().toString(), ItemUnit.getText().toString(), Integer.parseInt(ItemQuantity.getText().toString()) );

                helper.insertItems(helper.getWritableDatabase(),id,eventID);
                finish();
            }
        });
    }

}
