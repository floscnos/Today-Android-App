package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        preferences = getSharedPreferences("com.example.today", Context.MODE_PRIVATE);
        System.out.println(preferences.getInt("userId", 0));
    }

    public void gotoTasksListActivity(View view, Boolean gotoClosedTasks) {
        Intent intent = new Intent(this, TasksListActivity.class);

        if (gotoClosedTasks) {
            intent.putExtra("CLOSEDTASKS", true);
        } else {
            intent.putExtra("CLOSEDTASKS", false);
        }
        startActivity(intent);
    }

    public void gotoOpenTasks(View view) {
        gotoTasksListActivity(view, false);
    }

    public void gotoClosedTasks(View view) {
        gotoTasksListActivity(view, true);
    }

    public void gotoLabelList(View view) {
        Intent intent = new Intent(this, LabelsListActivity.class);

        startActivity(intent);
    }
}