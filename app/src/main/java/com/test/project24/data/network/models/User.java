package com.test.project24.data.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Salman on 4/10/2018.
 */

@Entity(tableName = "user")
public class User {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    @Expose
    public String id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String userName;

    @ColumnInfo(name = "login_type")
    @SerializedName("login_type")
    @Expose
    public String login_type;

    @ColumnInfo(name = "social_media_id")
    @SerializedName("social_media_id")
    @Expose
    public String social_media_id;

    @ColumnInfo(name = "lives")
    @SerializedName("lives")
    @Expose
    public int lives;

    @ColumnInfo(name = "promo_code")
    @SerializedName("promo_code")
    @Expose
    public String promo_code;

    @ColumnInfo(name = "balance")
    @SerializedName("balance")
    @Expose
    public int balance;
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar_url")
    @Expose
    public String avatar;

    @ColumnInfo(name = "x_auth")
    public String x_auth;

    public boolean isNewUser = false;

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getLogin_type() {
        return login_type;
    }

    public String getSocial_media_id() {
        return social_media_id;
    }

    public int getLives() {
        return lives;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public int getBalance() {
        return balance;
    }

    public String getX_auth() {
        return x_auth;
    }

    public String getAvatar() {
        return avatar;
    }
}
