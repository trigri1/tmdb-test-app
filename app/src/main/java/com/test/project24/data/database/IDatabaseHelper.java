package com.test.project24.data.database;


import com.test.project24.data.database.daos.FavoritesDao;
import com.test.project24.data.database.daos.RecentlyUsedColorsDao;
import com.test.project24.data.database.daos.UserDao;

/**
 * Created by Gohar Ali on 21/11/2017.
 */

public interface IDatabaseHelper {

    UserDao getUserDao();

}
