package com.example.vanph.karaokemanage.model;

import java.util.List;

/**
 * Created by vanph on 01/11/2017.
 */

public class Oder {
    private int id_bill;
    private List<ItemSelect> itemList;

    public Oder()
    {

    }
    public Oder(int id_bill, List<ItemSelect> itemList) {
        this.id_bill = id_bill;
        this.itemList = itemList;
    }

    public int getId_bill() {
        return id_bill;
    }

    public List<ItemSelect> getItemList() {
        return itemList;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public void setItemList(List<ItemSelect> itemList) {
        this.itemList = itemList;
    }
}

