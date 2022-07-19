package com.example.today;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.today.models.Label;
import com.example.today.models.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;
    private Task task;

    protected void populateFields() {
        // populating fields on the screen with info from the task.
        TextView title = findViewById(R.id.detail_taskName_tv_id);
        title.setText(task.getName());

        TextView notes = findViewById(R.id.detail_taskNotes_tv_id);
        notes.setText(task.getNotes());

        TextView date = findViewById(R.id.detail_taskDeadline_tv_id);

        Date deadline = task.getDeadline();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date.setText(dateFormat.format(deadline));

        if (deadline.before(new Date())) {
            date.setTextColor(getColor(R.color.red));
        }

        TextView labelField = findViewById(R.id.detail_label_tv_id);
        if (task.getLabelID() != null) {
            Label label = task.getLabel(this);
            labelField.setText(label.getName());
        } else {
            labelField.setText(R.string.edit_noLabel_spinner_text);
            labelField.setTextColor(getColor(R.color.grey));
        }

        TextView doneDateText = findViewById(R.id.detail_taskDoneDate_tv_id);
        if (!task.getDone()) {
            doneDateText.setVisibility(View.GONE);
        } else {
            String doneDate = dateFormat.format(task.getDoneDate());
            doneDateText.setText(getString(R.string.task_detail_task_done_date_text) + " " + doneDate);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int taskId = intent.getIntExtra("TASKID", 1);
        task = Task.getTaskByID(taskId, this);

        populateFields();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    if (result.getData().getBooleanExtra("DELETED", false)) {
                        finish();
                    }
                } else {
                    task = null;
                    task = Task.getTaskByID(taskId, getApplicationContext());
                    populateFields();
                }

            }
        });
    }

    public void gotoEditActivity(View view) {
        Intent intent = getIntent();
        int taskId = intent.getIntExtra("TASKID", 1);

        Intent gotoEditIntent = new Intent(this, EditActivity.class);
        gotoEditIntent.putExtra("TASKID", taskId);

        launcher.launch(gotoEditIntent);
    }
}