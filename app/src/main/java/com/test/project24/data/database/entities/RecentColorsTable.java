package com.test.project24.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Gohar Ali on 19/12/2017.
 */
@Entity(tableName = "colors")
public class RecentColorsTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int uid;

    @ColumnInfo(name = "color_id")
    private String colorId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
