package com.test.project24.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.test.project24.data.database.entities.UserFavoritesTable;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author goharali
 */
@Dao
public interface FavoritesDao {

    @Query("SELECT * from favorites")
    Flowable<List<UserFavoritesTable>> getAllFavorites();

    @Query("SELECT * from favorites order by content_id desc limit 5")
    Flowable<List<UserFavoritesTable>> getRecentFive();

    @Query("SELECT * from favorites where content_id=:contId")
    Flowable<UserFavoritesTable> getFavoriteById(int contId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavorite(UserFavoritesTable userFavoritesTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllFavorite(UserFavoritesTable... userFavoriteTables);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllFavorite(List<UserFavoritesTable> userFavoriteTables);

    @Delete
    int deleteFavorite(UserFavoritesTable userFavoritesTable);


    @Query("DELETE from favorites where content_id=:contId")
    int deleteFavoriteById(int contId);

    @Query("SELECT * from favorites where NOT is_updated")
    Flowable<List<UserFavoritesTable>> getListToBeUpdated();

    @Query("DELETE FROM favorites")
    int clearTable();

}
