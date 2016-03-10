package com.example.eigenaar.booking;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;

    private String[] places, date, time;

    public static final String EXTRA_BOOKNG_ID = "extra_booking_Id";

    private CustomAdapter customAdapter;
    //private List<ItemHolder> list;

    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new DataSource(this);

        //list = new ArrayList<ItemHolder>();
        //list = datasource.getAllBookings();

        List<ItemHolder> list = datasource.getAllBookings();
        customAdapter = new CustomAdapter(getBaseContext(), R.layout.rows, list);
        listView = (ListView) findViewById(R.id.display_listview);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class), 1);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.clearUserName(getBaseContext());

                Intent intent = new Intent(getBaseContext(), TabbedActivityLogin.class);
                startActivity(intent);

                finish();
            }
        });

        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            Intent intent = new Intent(getBaseContext(), TabbedActivityLogin.class);
            startActivity(intent);

            finish();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // TODO Auto-generated method stub

                ItemHolder itemHolder = customAdapter.getItem(position);
                String place1 = itemHolder.getPlace();
                String date1 = itemHolder.getDate();
                String time1 = itemHolder.getTime();

                AlertDialog.Builder myDialog
                        = new AlertDialog.Builder(MainActivity.this);

                myDialog.setTitle("Delete/Edit?");

                //new upper textfield and get place
                final EditText dialogC1_id = new EditText(MainActivity.this);
                ViewGroup.LayoutParams dialogC1_idLayoutParams
                        = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogC1_id.setLayoutParams(dialogC1_idLayoutParams);
                dialogC1_id.setText(place1);

                //new textfield and get date
                final EditText dialogC2_id = new EditText(MainActivity.this);
                ViewGroup.LayoutParams dialogC2_idLayoutParams
                        = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogC2_id.setLayoutParams(dialogC2_idLayoutParams);
                dialogC2_id.setText(date1);

                //new textfield and het time
                final EditText dialogC3_id = new EditText(MainActivity.this);
                ViewGroup.LayoutParams dialogC3_idLayoutParams
                        = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogC3_id.setLayoutParams(dialogC3_idLayoutParams);
                dialogC3_id.setText(time1);

                //add the new textfields
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(dialogC1_id);
                layout.addView(dialogC2_id);
                layout.addView(dialogC3_id);
                myDialog.setView(layout);

                //left delete button
                myDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        ItemHolder itemHolder = customAdapter.getItem(position);
                        customAdapter.remove(itemHolder);
                        datasource.deleteBooking(itemHolder);
                        updateItemListView();
                    }
                });

                //middel update button
                myDialog.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        String place2 = dialogC1_id.getText().toString();
                        String date2 = dialogC2_id.getText().toString();
                        String time2 = dialogC3_id.getText().toString();
                        ItemHolder itemHolder = customAdapter.getItem(position);
                        itemHolder.setPlace(place2);
                        itemHolder.setDate(date2);
                        itemHolder.setTime(time2);
                        datasource.updateBooking(itemHolder);
                        Toast.makeText(getApplicationContext(), place2 + " " + date2 + " " + time2, Toast.LENGTH_LONG).show();
                        updateItemListView();
                    }
                });

                //right cancel button
                myDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                myDialog.show();
            }
        });


    }

    public void updateItemListView() {
        List<ItemHolder> itemHolder = datasource.getAllBookings();
        customAdapter = new CustomAdapter(getBaseContext(), R.layout.rows, itemHolder);
        listView = (ListView) findViewById(R.id.display_listview);
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Handle data
                long itemId = data.getLongExtra(EXTRA_BOOKNG_ID, -1);
                if (itemId != -1) {
                    ItemHolder itemHolder = datasource.getBooking(itemId);
                    customAdapter.add(itemHolder);

                }



            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateItemListView();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}


