package com.example.eigenaar.booking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    EditText editPlace,editDate,editTime;
    String place, date, time;
    Button save;

    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editPlace = (EditText) findViewById(R.id.add_place_name);
        editDate = (EditText) findViewById(R.id.add_date);
        editTime = (EditText) findViewById(R.id.add_time);

        save = (Button) findViewById(R.id.save_button);

        datasource = new DataSource(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                place = editPlace.getText().toString();
                date = editDate.getText().toString();
                time = editTime.getText().toString();

                long ID = datasource.createBooking(place, date, time);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_BOOKNG_ID, ID);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
