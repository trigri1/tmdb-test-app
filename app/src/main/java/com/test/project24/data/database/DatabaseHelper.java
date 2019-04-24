package com.test.project24.data.database;



import com.test.project24.data.database.daos.FavoritesDao;
import com.test.project24.data.database.daos.RecentlyUsedColorsDao;
import com.test.project24.data.database.daos.UserDao;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author goharali
 */
@Singleton
public class DatabaseHelper implements IDatabaseHelper {


    private AppRoomDataBase appRoomDataBase;

    @Inject
    public DatabaseHelper(AppRoomDataBase appRoomDataBase) {
        this.appRoomDataBase = appRoomDataBase;
    }

    @Override
    public UserDao getUserDao() {
        return appRoomDataBase.getUserDao();
    }

//    @Override
//    public FavoritesDao getFavoriteDao() {
//        return appRoomDataBase.getFavoriteDao();
//    }
//
//    @Override
//    public RecentlyUsedColorsDao getColorsDao() {
//        return appRoomDataBase.getColorsDao();
//    }
}
