package com.example.vanph.karaokemanage.model;

/**
 * Created by vanph on 01/11/2017.
 */

public class ItemSelect {
    private int soluong;
    private Item item;

    public ItemSelect(int soluong, Item item) {
        this.soluong = soluong;
        this.item = item;
    }

    public int getSoluong() {
        return soluong;
    }

    public Item getItem() {
        return item;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
