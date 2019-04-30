package com.example.gearquicker.pricebase;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OrmPrice  extends RealmObject {

    @PrimaryKey
    private String id;
    private OrmItem item;
    private long date;
    private double price;

    public OrmPrice() {
    }

    public OrmPrice(String id, OrmItem item, long date, double price) {
        this.id = id;
        this.item = item;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrmItem getItem() {
        return item;
    }

    public void setItem(OrmItem item) {
        this.item = item;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}