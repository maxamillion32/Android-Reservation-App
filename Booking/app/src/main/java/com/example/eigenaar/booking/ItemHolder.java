package com.example.eigenaar.booking;

/**
 * Created by Eigenaar on 25-2-2016.
 */
public class ItemHolder {
    private long id;
    private String place, date, time;

//    public ItemHolder(String place, String date, String time)
//    {
//        this.place = place;
//        this.date = date;
//        this.time = time;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
