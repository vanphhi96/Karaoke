package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 24/10/2017.
 */

public class RoomStyle implements Serializable {
    private int id_roomstyle;
    private String name_roomstyle;
    private int price_roomstyle;

    public RoomStyle() {

    }

    public RoomStyle(int id_roomstyle, String name_roomstyle, int price_roomstyle) {
        this.id_roomstyle = id_roomstyle;
        this.name_roomstyle = name_roomstyle;
        this.price_roomstyle = price_roomstyle;
    }

    public RoomStyle(String name_roomstyle, int price_roomstyle) {
        this.name_roomstyle = name_roomstyle;
        this.price_roomstyle = price_roomstyle;
    }

    public int getId_roomstyle() {
        return id_roomstyle;
    }

    public String getName_roomstyle() {
        return name_roomstyle;
    }

    public int getPrice_roomstyle() {
        return price_roomstyle;
    }

    public void setId_roomstyle(int id_roomstyle) {
        this.id_roomstyle = id_roomstyle;
    }

    public void setName_roomstyle(String name_roomstyle) {
        this.name_roomstyle = name_roomstyle;
    }

    public void setPrice_roomstyle(int price_roomstyle) {
        this.price_roomstyle = price_roomstyle;
    }

    @Override
    public String toString() {
        return "RoomStyle{" +
                "id_roomstyle=" + id_roomstyle +
                ", name_roomstyle='" + name_roomstyle + '\'' +
                ", price_roomstyle=" + price_roomstyle +
                '}';
    }
}
