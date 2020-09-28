package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 30/10/2017.
 */

public class Item implements Serializable{
    private int id_item;
    private String name;
    private int price_item;
    private Group_Item group_item;
    public Item(int id_item, String name, int price_item, Group_Item group_item) {
        this.id_item = id_item;
        this.name = name;
        this.price_item = price_item;
        this.group_item = group_item;
    }

    public Item(String name, int price_item, Group_Item group_item) {
        this.name = name;
        this.price_item = price_item;
        this.group_item = group_item;
    }

    public int getId_item() {
        return id_item;
    }

    public String getName() {
        return name;
    }

    public int getPrice_item() {
        return price_item;
    }

    public Group_Item getGroup_item() {
        return group_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice_item(int price_item) {
        this.price_item = price_item;
    }

    public void setGroup_item(Group_Item group_item) {
        this.group_item = group_item;
    }
}
