package com.example.today.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.today.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    List<Task> getAll();
    @Query("SELECT * FROM Task WHERE done = 0 AND userID = :userID")
    List<Task> getOpenTasks(int userID);
    @Query("SELECT * FROM Task WHERE done = 1 AND userID = :userID ORDER BY doneDate DESC")
    List<Task> getClosedTasks(int userID);
    @Query("SELECT * FROM task WHERE done = :done AND userID = :userID AND labelID = :labelID")
    List<Task> getFilteredTasks(boolean done, int userID, int labelID);
    @Query("SELECT * FROM Task WHERE ID = :id")
    Task getById(int id);
    @Insert
    void insert(Task task);
    @Update
    void update(Task task);
    @Delete
    void delete(Task task);
}
