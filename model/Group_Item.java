package com.example.vanph.karaokemanage.model;

import java.io.Serializable;

/**
 * Created by vanph on 30/10/2017.
 */

public class Group_Item implements Serializable {
    private int id_groupItem;
    private String name_group;

    public Group_Item(int id_groupItem, String name_group) {
        this.id_groupItem = id_groupItem;
        this.name_group = name_group;
    }

    public Group_Item(String name_group) {
        this.name_group = name_group;
    }

    public int getId_groupItem() {
        return id_groupItem;
    }

    public String getName_group() {
        return name_group;
    }

    public void setId_groupItem(int id_groupItem) {
        this.id_groupItem = id_groupItem;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }
}
