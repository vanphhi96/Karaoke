package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 24/10/2017.
 */

public class Room implements Serializable {
    private int id;
    private String name_room;
    private RoomStyle roomStyle;
    private int status;

    public Room() {
    }

    public Room(int id, String name_room, RoomStyle roomStyle,int status) {

        this.id = id;
        this.name_room = name_room;
        this.roomStyle = roomStyle;
        this.status=status;
    }
    public Room( String name_room, RoomStyle roomStyle,int status) {

        this.name_room = name_room;
        this.roomStyle = roomStyle;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName_room() {
        return name_room;
    }

    public RoomStyle getRoomStyle() {
        return roomStyle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_room(String name_room) {
        this.name_room = name_room;
    }

    public void setRoomStyle(RoomStyle roomStyle) {
        this.roomStyle = roomStyle;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name_room='" + name_room + '\'' +
                ", roomStyle=" + roomStyle +
                '}';
    }
}
