package com.example.today;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.today.daos.LabelDao;
import com.example.today.daos.TaskDao;
import com.example.today.daos.UserDao;
import com.example.today.models.Label;
import com.example.today.models.Task;
import com.example.today.models.User;

@androidx.room.Database
        (entities = {Task.class, Label.class, User.class},
        version = 1, exportSchema = false)

public abstract class Database extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract LabelDao labelDao();
    public abstract TaskDao taskdao();

    public static Database getDatabase(Context context){ Database database;
        synchronized (Database.class) {
            database = Room.databaseBuilder(
                            context,
                            Database.class,
                            "ToDayDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
