package com.example.today.models;

import java.io.Serializable;
import static androidx.room.ForeignKey.SET_NULL;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.today.Converters;
import com.example.today.Database;

import java.util.Date;
import java.util.List;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Label.class,
                parentColumns = "ID",
                childColumns = "labelID",
                onDelete = SET_NULL        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "ID",
                childColumns = "userID",
                onDelete = SET_NULL
        )
})
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private Integer labelID;
    private Integer userID;
    private String name;
    private String notes;
    private Boolean done;

    @TypeConverters({Converters.class})
    private Date deadline;

    @TypeConverters({Converters.class})
    private Date doneDate;

    public static List<Task> getOpenTasks(Context context, int userID) {
        return Database.getDatabase(context).taskdao().getOpenTasks(userID);
    }

    public static List<Task> getClosedTasks(Context context, int userID) {
        return Database.getDatabase(context).taskdao().getClosedTasks(userID);
    }

    public static List<Task> getFilteredTasks(Context context, boolean done, int userID, int labelID) {
        return Database.getDatabase(context).taskdao().getFilteredTasks(done, userID, labelID);
    }

    public static Task getTaskByID(int id, Context context) {
        return Database.getDatabase(context).taskdao().getById(id);
    }

    // Empty Constructor, required for Room
    public Task() {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Label getLabel(Context context) {
        Label label = Database.getDatabase(context).labelDao().getById(this.labelID);
        return label;
    }

    public Integer getLabelID() {
        return labelID;
    }

    public void setLabelID(Integer labelID) {
        this.labelID = labelID;
    }

    public void insert(Context context) {
        Database.getDatabase(context).taskdao().insert(this);
    }

    public void update(Context context) {
        Database.getDatabase(context).taskdao().update(this);
    }

    public void delete(Context context) {
        Database.getDatabase(context).taskdao().delete(this);
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
