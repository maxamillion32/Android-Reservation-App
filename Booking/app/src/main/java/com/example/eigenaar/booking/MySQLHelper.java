package com.example.eigenaar.booking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Marcellis on 6-2-2016.
 */
public class MySQLHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "booking.db";
    private static final int DATABASE_VERSION = 10;

    //Common Column name
    public static final String Column_ID = "id";

    // Reservations
    public static final String TABLE_BOOKING = "table_booking";
    public static final String COLUMN_BOOKING_ID = "booking_id";
    public static final String COLUMN_BOOKING_PLACE = "booking";
    public static final String COLUMN_BOOKING_DATE = "date";
    public static final String COLUMN_BOOKING_TIME = "time";

    //Account data
    public static final String TABLE_ACCOUNT = "table_user_data";
    public static final String COLUMN_ACCOUNT_ID = "user_data_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_CONFIRM_PASSWORD = "confirm_user_password";

    // Creating the table
    private static final String DATABASE_CREATE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKING + "(" +
                    COLUMN_BOOKING_ID + " integer primary key autoincrement, " +
                    COLUMN_BOOKING_PLACE + " text not null, " +
                    COLUMN_BOOKING_DATE + " text, " +
                    COLUMN_BOOKING_TIME + " text" + ");";

    private static final String DATABASE_CREATE_ACCOUNTS =
            "CREATE TABLE " + TABLE_ACCOUNT + "(" +
                    COLUMN_ACCOUNT_ID + " integer primary key autoincrement, " +
                    COLUMN_USER_ID + " text, " +
                    COLUMN_USER_PASSWORD + " text, " +
                    COLUMN_USER_CONFIRM_PASSWORD + " text" + ");";


    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_BOOKINGS);
        database.execSQL(DATABASE_CREATE_ACCOUNTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	/*
     	* When the database gets upgraded you should handle the update to make sure there is no data loss.
     	* This is the default code you put in the upgrade method, to delete the table and call the oncreate again.
     	*/
        Log.w(MySQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);
    }
}
