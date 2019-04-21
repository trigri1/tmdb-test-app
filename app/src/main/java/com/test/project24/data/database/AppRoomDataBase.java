package com.test.project24.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.test.project24.data.database.daos.FavoritesDao;
import com.test.project24.data.database.daos.RecentlyUsedColorsDao;
import com.test.project24.data.database.daos.UserDao;
import com.test.project24.data.database.entities.RecentColorsTable;
import com.test.project24.data.database.entities.UserFavoritesTable;
import com.test.project24.data.database.entities.UserTable;

/**
 * Created by Gohar Ali on 09/11/2017.
 */

//database class should be abstract and singleton(because data base creation is expensive operation
//it should include abstract methods with zero arguments and return instance of a class that is annotated with @Dao
@Database(entities = {UserTable.class, RecentColorsTable.class, UserFavoritesTable.class}, version = 1)
public abstract class AppRoomDataBase extends RoomDatabase {


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract UserDao getUserDao();

    public abstract RecentlyUsedColorsDao getColorsDao();

    public abstract FavoritesDao getFavoriteDao();
}
