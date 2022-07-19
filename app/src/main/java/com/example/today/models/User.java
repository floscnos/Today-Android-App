package com.example.today.models;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.today.Database;

import java.util.List;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String username;
    private String password;

    public static List<User> getAll(Context context) {
        return Database.getDatabase(context).userDao().getAll();
    }

    public static User getByUsername(Context context, String username) {
        return Database.getDatabase(context).userDao().getByUsername(username);
    }

    // Empty Constructor, required for Room.
    public User() {

    }

    public void insert(Context context) {
        Database.getDatabase(context).userDao().insert(this);
    }

    public void update(Context context) {
        Database.getDatabase(context).userDao().update(this);

    }
    public void delete(Context context) {
        Database.getDatabase(context).userDao().delete(this);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
