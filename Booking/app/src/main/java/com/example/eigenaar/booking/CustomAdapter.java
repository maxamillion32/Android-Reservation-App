package com.example.eigenaar.booking;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class CustomAdapter extends ArrayAdapter<ItemHolder> {

    private LayoutInflater inflater;

    //Constructor
    public CustomAdapter(Context context, int resource, List<ItemHolder> objects) {
        super(context, resource, objects);

        //Initialize the layout inflater
        inflater = LayoutInflater.from(getContext());
    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        // Inflate layout into the View for the Row
        itemView = inflater.inflate(R.layout.rows, parent, false);

        //Retrieve ListItem at the position
        ItemHolder itemHolder = getItem(position);

        //Retrieve all Views of a ListItem
        TextView place = (TextView) itemView.findViewById(R.id.place_view);
        TextView date = (TextView) itemView.findViewById(R.id.date_view);
        TextView time = (TextView) itemView.findViewById(R.id.time_view);

        //Set the title for this row
        place.setText(itemHolder.getPlace());
        date.setText(itemHolder.getDate());
        time.setText(itemHolder.getTime());

        return itemView;
    }
}

