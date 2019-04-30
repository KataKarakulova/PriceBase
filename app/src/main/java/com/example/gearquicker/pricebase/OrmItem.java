package com.example.gearquicker.pricebase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OrmItem extends RealmObject {

    @PrimaryKey
    private String id;

    public OrmItem() {
    }

    public OrmItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
