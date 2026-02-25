package org.example.hormonika;

import Model.Booking;

import java.util.ArrayList;
import java.util.List;

public class DBRepo {
    private final DBConfig db;
    public DBRepo(DBConfig db) {
        this.db = db;
    }

    public List<Booking> loadCalendar() throws Exception{
        List<Booking> bookings = new ArrayList<>();



        return new ArrayList<>(bookings);
    }

}
