package com.example.today.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.today.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();
    @Query("SELECT * FROM User WHERE ID = :id")
    User getById(int id);
    @Query("SELECT * FROM USER WHERE username = :username")
    User getByUsername(String username);
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
}
