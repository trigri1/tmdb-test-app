package com.test.project24.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Gohar Ali on 09/11/2017.
 */
//Each entity must define at least 1 field as a primary key.
@Entity(tableName = "users")
public class UserTable {

    //for private fields getters and setters required in order for Room to work
    //or make fields public

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int uid;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "udid")
    private String udid;

    @ColumnInfo(name = "subscribe_status")
    private int subscribeStatus;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "gcm_token")
    private String gcmToken;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;


    @ColumnInfo(name = "free_views")
    private int freeViews;

//    @Embedded(prefix = "fav_")
//    private UserFavoritesTable userFavorites;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public int getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(int subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFreeViews() {
        return freeViews;
    }

    public void setFreeViews(int freeViews) {
        this.freeViews = freeViews;
    }
}
