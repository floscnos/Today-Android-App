package com.example.today.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.today.models.Label;

import java.util.List;

@Dao
public interface LabelDao {
    @Query("SELECT * FROM Label WHERE userID = :userID")
    List<Label> getAll(int userID);
    @Query("SELECT * FROM Label WHERE ID = :id")
    Label getById(int id);
    @Insert
    void insert(Label label);
    @Update
    void update(Label label);
    @Delete
    void delete(Label label);
}
