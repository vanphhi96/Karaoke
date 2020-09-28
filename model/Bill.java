package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 01/11/2017.
 */

public class Bill implements Serializable {
    private Room room;
    private String time_start;
    private String time_stop;
    private int id_bill;

    public Bill (Room room, String time_start) {
        this.room = room;
        this.time_start = time_start;
    }

    public Bill(Room room, String time_start, String time_stop, int id_bill) {
        this.room = room;
        this.time_start = time_start;
        this.time_stop = time_stop;
        this.id_bill = id_bill;
    }

    public Room getRoom() {
        return room;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_stop() {
        return time_stop;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setTime_stop(String time_stop) {
        this.time_stop = time_stop;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }
}
