package com.test.project24.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.test.project24.data.database.entities.RecentColorsTable;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Gohar Ali on 19/12/2017.
 */
@Dao
public interface RecentlyUsedColorsDao {


    @Query("SELECT * from colors")
    Flowable<List<RecentColorsTable>> getAllColors();

    @Query("SELECT * from colors order by id desc limit 5")
    Flowable<List<RecentColorsTable>> getRecentFive();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertColor(RecentColorsTable colorsTables);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllColors(RecentColorsTable... colorsTables);

    @Delete
    void deleteColor(RecentColorsTable recentColorsTable);

}
