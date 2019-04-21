package com.test.project24.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by Gohar Ali on 09/11/2017.
 */
@Entity
public class Address {

    @ColumnInfo(name = "street")
    private String street;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "post_code")
    public int postCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
