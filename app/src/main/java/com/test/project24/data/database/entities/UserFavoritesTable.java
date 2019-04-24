package com.test.project24.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author goharali
 */
@Entity(tableName = "favorites")
public class UserFavoritesTable {


    @PrimaryKey
    @ColumnInfo(name = "content_id")
    private int contentId;

    @ColumnInfo(name = "is_updated")
    private boolean idUpdated;


    public boolean isIdUpdated() {
        return idUpdated;
    }

    public void setIdUpdated(boolean idUpdated) {
        this.idUpdated = idUpdated;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }


    @Override
    public String toString() {
        return "UserFavoritesTable{" +
                "  idUpdated=" + idUpdated +
                ", contentId='" + contentId + '\'' +
                '}';
    }


}
