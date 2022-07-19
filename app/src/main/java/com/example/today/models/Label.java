package com.example.today.models;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.SET_NULL;

import com.example.today.Database;

import java.util.ArrayList;
import java.util.List;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "ID",
                childColumns = "userID",
                onDelete = SET_NULL
        )
})
public class Label {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private int userID;
    private String name;

    public static List<Label> getAll(Context context, int userID) {
        return Database.getDatabase(context).labelDao().getAll(userID);
    }

    public Label(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    // Empty Constructor, required for Room
    public Label(){
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void save(Context context) {
        Database.getDatabase(context).labelDao().update(this);
    }

    public void delete(Context context) {
        Database.getDatabase(context).labelDao().delete(this);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
