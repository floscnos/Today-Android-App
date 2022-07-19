package com.example.today;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.today.adapters.ClickListener;
import com.example.today.adapters.TaskListAdapter;
import com.example.today.models.Label;
import com.example.today.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksListActivity extends AppCompatActivity  {
    private boolean getClosedTasks = false;

    private int checkedLabelFilter;

    private ImageButton filterButton;
    private Label filteredLabel;

    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> launcher;

    private TextView titleField;
    private EditText nameField;
    private EditText notesField;
    private List<Task> tasksList = new ArrayList<>();

    private SharedPreferences preferences;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_lists);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                checkedLabelFilter = 0;
                filterButton.setColorFilter(getColor(R.color.black));

                tasksList.clear();

                if (getClosedTasks) {
                    tasksList.addAll(Task.getClosedTasks(getApplicationContext(), userID));
                    titleField.setText(R.string.tasksList_closedTasks_tv_text);
                } else {
                    tasksList.addAll(Task.getOpenTasks(getApplicationContext(), userID));
                    titleField.setText(R.string.open_tasks_title_text);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        checkedLabelFilter = 0;
        filterButton = findViewById(R.id.tasksList_filter_btn_id);
        filterButton.setColorFilter(getColor(R.color.black));

        recyclerView = findViewById(R.id.tasksList_tasks_rv_id);

        titleField = findViewById(R.id.tasksList_title_tv_id);

        preferences = getSharedPreferences("com.example.today", Context.MODE_PRIVATE);
        userID = preferences.getInt("userId", 0);

        // Get open or closed tasks, based on String passed by launching app. If no data was passed, open tasks will be displayed.
        Intent intent = getIntent();
        getClosedTasks  = intent.getBooleanExtra("CLOSEDTASKS", false);

        if (getClosedTasks) {
            tasksList = Task.getClosedTasks(this, userID);
            titleField.setText(R.string.tasksList_closedTasks_tv_text);

            // Hide plus button
            findViewById(R.id.tasksList_new_fab_id).setVisibility(View.GONE);
        } else {
            tasksList = Task.getOpenTasks(this, userID);

            titleField.setText(R.string.open_tasks_title_text);
        }

        TaskListAdapter adapter = new TaskListAdapter(tasksList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        adapter.setDetailClickListener(new ClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("TASKID", id);

                launcher.launch(intent);
            }
        });

        adapter.setDoneClickListener(new ClickListener() {
            @Override
            public void onClick(int id) {
                Task task = Task.getTaskByID(id, getApplicationContext());

                if (task.getDone()) {
                    task.setDone(false);
                    task.setDoneDate(null);

                } else {
                    task.setDone(true);
                    task.setDoneDate(new Date());

                }
                task.update(getApplicationContext());

                tasksList.clear();

                if (filteredLabel != null) {
                    tasksList.addAll(Task.getFilteredTasks(getApplicationContext(), getClosedTasks, userID, filteredLabel.getID()));
                } else {
                    if (getClosedTasks) {
                        tasksList.addAll(Task.getClosedTasks(getApplicationContext(), userID));
                        titleField.setText(R.string.tasksList_closedTasks_tv_text);
                    } else {
                        tasksList.addAll(Task.getOpenTasks(getApplicationContext(), userID));
                        titleField.setText(R.string.open_tasks_title_text);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void filterTasks(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        List<Label> labels = Label.getAll(this, userID);

        String[] listItems = new String[labels.size() + 1];
        listItems[0] = getString(R.string.tasksList_reset_filterDialog_text);

        for (int i = 1; i < listItems.length; i++) {
            listItems[i] = labels.get(i - 1).getName();
        }

        alertDialog.setTitle(R.string.tasksList_title_filterDaliog_text);
        alertDialog.setSingleChoiceItems(listItems, checkedLabelFilter, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasksList.clear();
                checkedLabelFilter = which;
                if (which > 0) {
                    filteredLabel = labels.get(which - 1);
                    filterButton.setColorFilter(getColor(R.color.hhs_green));
                    tasksList.addAll(Task.getFilteredTasks(getApplicationContext(), getClosedTasks, userID, filteredLabel.getID()));
                } else {
                    filteredLabel = null;
                    filterButton.setColorFilter(getColor(R.color.black));

                    if (getClosedTasks) {
                        tasksList.addAll(Task.getClosedTasks(getApplicationContext(), userID));
                    } else {
                        tasksList.addAll(Task.getOpenTasks(getApplicationContext(), userID));
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        AlertDialog filterTasksDialog = alertDialog.create();
        filterTasksDialog.show();
    }

    public void gotoEditActivity(View view) {
        // For creating new tasks
        Intent intent = new Intent(this, EditActivity.class);
        launcher.launch(intent);
    }
}