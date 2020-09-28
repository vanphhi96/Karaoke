package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 31/10/2017.
 */

public class StatusRoom implements Serializable{
    private int id_room;
    private int status;

    public StatusRoom(int id_room, int status) {
        this.id_room = id_room;
        this.status = status;
    }

    public int getId_room() {
        return id_room;
    }

    public int getStatus() {
        return status;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
