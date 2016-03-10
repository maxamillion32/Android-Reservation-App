package com.example.eigenaar.booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marcellis on 6-2-2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    private String[] bookingAllColumns = { MySQLHelper.COLUMN_BOOKING_ID,
                                                MySQLHelper.COLUMN_BOOKING_PLACE,
                                                MySQLHelper.COLUMN_BOOKING_DATE,
                                                MySQLHelper.COLUMN_BOOKING_TIME };

    private String[] accountAllColumns = {MySQLHelper.COLUMN_ACCOUNT_ID,
                                            MySQLHelper.COLUMN_USER_ID,
                                            MySQLHelper.COLUMN_USER_PASSWORD,
                                            MySQLHelper.COLUMN_USER_CONFIRM_PASSWORD };


    public DataSource(Context context)
    {
        dbHelper = new MySQLHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    //creating booking
    public long createBooking(String booking, String date, String time)
    {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_BOOKING_PLACE, booking);
        values.put(MySQLHelper.COLUMN_BOOKING_DATE, date);
        values.put(MySQLHelper.COLUMN_BOOKING_TIME, time);
        long insertId = database.insert(MySQLHelper.TABLE_BOOKING, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    //creating account
    public long createAccount(String userID, String userPassword, String userConfirmPassword)
    {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLHelper.COLUMN_USER_ID, userID);
        values.put(MySQLHelper.COLUMN_USER_PASSWORD, userPassword);
        values.put(MySQLHelper.COLUMN_USER_CONFIRM_PASSWORD, userConfirmPassword);
        long insertId = database.insert(MySQLHelper.TABLE_ACCOUNT, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    //Delete a booking item
    public void deleteBooking(ItemHolder itemHolder)
    {
        if (!database.isOpen())
            open();

        database.delete(MySQLHelper.TABLE_BOOKING, MySQLHelper.COLUMN_BOOKING_ID + " =?", new String[]{Long.toString(itemHolder.getId())});

        if (database.isOpen())
            close();
    }

    //delete account
    public void deleteAccount(AccountHolder accountHolder)
    {
        if (!database.isOpen())
            open();

        database.delete(MySQLHelper.TABLE_ACCOUNT, MySQLHelper.COLUMN_ACCOUNT_ID + " =?", new String[]{Long.toString(accountHolder.getId())});

        if (database.isOpen())
            close();
    }

    //Update a booking item
    public void updateBooking(ItemHolder itemHolder)
    {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLHelper.COLUMN_BOOKING_PLACE, itemHolder.getPlace());
        args.put(MySQLHelper.COLUMN_BOOKING_DATE, itemHolder.getDate());
        args.put(MySQLHelper.COLUMN_BOOKING_TIME, itemHolder.getTime());
        database.update(MySQLHelper.TABLE_BOOKING, args, MySQLHelper.COLUMN_BOOKING_ID + "=?", new String[]{Long.toString(itemHolder.getId())});

        if (database.isOpen())
            close();
    }

    //Get All the booking items
    public List<ItemHolder> getAllBookings()
    {
        if (!database.isOpen())
            open();

        List<ItemHolder> itemHolders = new ArrayList<ItemHolder>();

        Cursor cursor = database.query(MySQLHelper.TABLE_BOOKING, bookingAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemHolder itemHolder = cursorToBooking(cursor);
            itemHolders.add(itemHolder);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return itemHolders;
    }

    private ItemHolder cursorToBooking(Cursor cursor)
    {
        try {
            ItemHolder itemHolder = new ItemHolder();
            itemHolder.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLHelper.COLUMN_BOOKING_ID)));
            itemHolder.setPlace(cursor.getString(cursor.getColumnIndexOrThrow(MySQLHelper.COLUMN_BOOKING_PLACE)));
            itemHolder.setDate(cursor.getString(cursor.getColumnIndexOrThrow(MySQLHelper.COLUMN_BOOKING_DATE)));
            itemHolder.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MySQLHelper.COLUMN_BOOKING_TIME)));
            return itemHolder;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private AccountHolder cursorToAccountg(Cursor cursor)
    {
        try {
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setId(cursor.getLong(cursor.getColumnIndex(MySQLHelper.COLUMN_ACCOUNT_ID)));
            accountHolder.setUserID(cursor.getString(cursor.getColumnIndex(MySQLHelper.COLUMN_USER_ID)));
            accountHolder.setUserPassword(cursor.getString(cursor.getColumnIndex(MySQLHelper.COLUMN_USER_PASSWORD)));
            accountHolder.setConfirmPassword(cursor.getString(cursor.getColumnIndex(MySQLHelper.COLUMN_USER_CONFIRM_PASSWORD)));
            return accountHolder;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    //Get a single booking item
    public ItemHolder getBooking(long columnId)
    {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLHelper.TABLE_BOOKING, bookingAllColumns, MySQLHelper.COLUMN_BOOKING_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        ItemHolder itemHolder = cursorToBooking(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return itemHolder;
    }

    //Get a single booking item
    public AccountHolder getUser(long columnId)
    {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLHelper.TABLE_ACCOUNT, accountAllColumns, MySQLHelper.COLUMN_ACCOUNT_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        if(cursor.getCount() >= 1)
        {
            cursor.moveToFirst();
        }

        AccountHolder accountHolder = cursorToAccountg(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return accountHolder;
    }

    public boolean checkUserTable()
    {
        if (!database.isOpen())
            open();

        boolean empty = true;
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLHelper.TABLE_ACCOUNT + ";", null);

        if (cursor != null && cursor.getCount() >= 1 &&cursor.moveToFirst())
        {
            empty = false;
            Log.w("TEST", " First AddItemActivity" + Boolean.toString(empty));
            cursor.close();
            return empty;
        }
        else
        {
            empty = true;
        }

        if (database.isOpen())
            close();

        Log.w("TEST", " Second AddItemActivity" + Boolean.toString(empty));
        cursor.close();
        return empty;
    }

    public long checkAccountRowId()
    {
        if (!database.isOpen())
            open();

        long lastId = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLHelper.TABLE_ACCOUNT + ";", null);

        if (cursor != null && cursor.getCount() >= 1)
        {
            String query = "SELECT ROWID from " + MySQLHelper.TABLE_ACCOUNT +  " order by ROWID DESC limit 1";
            Cursor c = database.rawQuery(query, null);
            if (c != null && c.moveToFirst()) {
                lastId = c.getLong(c.getColumnIndex(MySQLHelper.COLUMN_ACCOUNT_ID));
            }
        }

        if (database.isOpen())
            close();

        cursor.close();
        return lastId;
    }

}
