package com.test.project24.data.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.test.project24.data.database.entities.UserTable;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Gohar Ali on 09/11/2017.
 */
//an interface that has all the logical queries to database for one entity (table)
@Dao
public interface UserDao {

    @Query("SELECT * from users")
    Flowable<List<UserTable>> getAllUsers();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    Flowable<List<UserTable>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE user_id = :userId")
    Flowable<UserTable> getUserById(int userId);


    @Query("SELECT * FROM users WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    Flowable<UserTable> findByName(String first, String last);

//    @Query("SELECT * from users where age > :minAge")
//    Flowable<List<UserTable>> getUsersOlderThanAge(int minAge);
//
//    @Query("SELECT * FROM users WHERE age BETWEEN :minAge AND :maxAge")
//    Flowable<List<UserTable>> loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM users WHERE first_name LIKE :search "
           + "OR last_name LIKE :search")
    Flowable<List<UserTable>> findUserWithName(String search);

    @Insert
    long[] insertAllUsers(UserTable... userTables);

    @Insert
    long insertUser(UserTable userTables);

    @Delete
    void deleteUser(UserTable userTable);


    @Query("DELETE FROM users")
    void clearTable();

}
