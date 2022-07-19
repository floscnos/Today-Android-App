package com.example.today;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.today.fragments.DatePickerFragment;
import com.example.today.fragments.TimePickerFragment;
import com.example.today.models.Label;
import com.example.today.models.Task;
import com.example.today.validation.TaskValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private int day, month, year, hours, minutes;
    private Date taskDeadline;

    private String model;

    private Task populatedTask;
    private Label populatedLabel;

    private List<Label> labels;
    private Label chosenLabel;

    private TextView titleField;
    private EditText nameField;
    private EditText notesField;
    private TextView deadlineField;
    private ImageButton setDeadlineButton;
    private Spinner labelField;

    private ArrayAdapter<Label> labelAdapter;

    private SharedPreferences preferences;
    private int userID;

    private void populateTaskFields() {
        titleField.setText(R.string.edit_editTaskTitle_tv_text);
        nameField.setText(populatedTask.getName());
        notesField.setText(populatedTask.getNotes());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        taskDeadline = populatedTask.getDeadline();
        deadlineField.setText(dateFormat.format(populatedTask.getDeadline()));

        // Dirty code; would be nicer when done with a while loop or with map and finding in list. Refactor in future.
        if (populatedTask.getLabelID() != null) {
            labels.forEach(label -> {
                if (populatedTask.getLabelID() == label.getID()) {
                    chosenLabel = label;
                }
            });
            labelField.setSelection(labelAdapter.getPosition(chosenLabel));
        }
    }

    private void populateLabelFields() {
        titleField.setText(R.string.edit_editLabeltitle_tv_text);

        nameField.setText(populatedLabel.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleField = findViewById(R.id.edit_title_tv_id);
        nameField = findViewById(R.id.edit_taskName_et_id);
        notesField = findViewById(R.id.edit_taskNotes_et_id);
        labelField = findViewById(R.id.edit_taskLabel_spinner_id);
        deadlineField = findViewById(R.id.edit_taskDeadline_tv_id);
        setDeadlineButton = findViewById(R.id.edit_taskDeadline_btn_id);

        preferences = getSharedPreferences("com.example.today", Context.MODE_PRIVATE);
        userID = preferences.getInt("userId", 0);

        Intent intent = getIntent();
        boolean label = intent.getBooleanExtra("LABEL", false);

        if (label) {
            model = "label";
            titleField.setText("New Label");

            int labelID = intent.getIntExtra("LABELID", 0);

            if (labelID > 0) {
                populatedLabel = Database.getDatabase(this).labelDao().getById(labelID);
                populateLabelFields();
            }

            notesField.setVisibility(View.GONE);
            labelField.setVisibility(View.GONE);
            deadlineField.setVisibility(View.GONE);
            setDeadlineButton.setVisibility(View.GONE);

        } else {
            model = "task";

            int taskID = intent.getIntExtra("TASKID", 0);

            labels = Label.getAll(this, userID);
            Label nullLabel = new Label(0, "No Label");
            labels.add(0, nullLabel);

            labelAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    labels);
            labelField.setAdapter(labelAdapter);

            if (taskID > 0) {
                populatedTask = Database.getDatabase(this).taskdao().getById(taskID);
                populateTaskFields();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        DialogFragment picker = new TimePickerFragment();
        this.year = year;
        this.month = month;
        this.day = day;
        picker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hourOfDay, minute);
        this.hours = hourOfDay;
        this.minutes = minute;
        taskDeadline = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        deadlineField.setText(dateFormat.format(taskDeadline));
    }

    public void openDatePicker(View view) {
        DialogFragment picker = new DatePickerFragment();
        picker.show(getSupportFragmentManager(), "date picker");
    }

    public void save(View view) {
        boolean valid = true;
        if (model.equals("task")) {
            valid = saveTask(view);
        } else if (model.equals("label")) {
            saveLabel(view);
        }
        if (valid) {
            finish();
        }
    }

    public boolean saveTask(View view) {
        String nameValue = nameField.getText().toString();
        String notesValue = notesField.getText().toString();
        Label label = (Label) labelField.getSelectedItem();

        TaskValidator taskValidator = new TaskValidator();
        boolean valid = true;
        String error = "";

        if (!taskValidator.dateValidator(taskDeadline)) {
            valid = false;
            error = getString(R.string.task_date_error_toast);
        }

        // Only validate if date is in future when making new task.
        if (valid) {
            if (populatedTask == null && !taskValidator.dateInFutureValidator(taskDeadline)) {
                valid = false;
                error = getString(R.string.task_date_error_toast);
            }
        }


        if (!taskValidator.noteValidator(notesValue)) {
            valid = false;
            error = getString(R.string.task_notes_error_toast);
        }

        if (!taskValidator.nameValidator(nameValue)) {
            valid = false;
            error = getString(R.string.task_name_error_toast);
        }

        if (valid) {
            Task task = populatedTask == null ? new Task() : populatedTask;

            task.setUserID(userID);
            task.setName(nameValue);
            task.setNotes(notesValue);
            task.setDeadline(taskDeadline);

            // Label with ID 0 = "no Label" option in UI
            if (label.getID() > 0) {
                task.setLabelID(label.getID());
            } else {
                task.setLabelID(null);
            }

            if (task.getDone() == null) {
                task.setDone(false);
            }

            if (populatedTask == null) {
                task.insert(this);
            } else {
                task.update(this);
            }

            return true;
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void saveLabel(View view) {
        String nameValue = nameField.getText().toString();

        Label label = populatedLabel == null ? new Label() : populatedLabel;

        label.setName(nameValue);
        label.setUserID(userID);

        if (populatedLabel == null) {
            Database.getDatabase(this).labelDao().insert(label);
        } else {
            Database.getDatabase(this).labelDao().update(label);
        }
    }

    public void delete(View view) {
        if (model.equals("task")) {
            deleteTask(view);
        } else if (model.equals("label")) {
            deleteLabel(view);
        }

        Intent data = new Intent();
        data.putExtra("DELETED", true);
        setResult(RESULT_OK, data);
        finish();
    }

    public void deleteTask(View view) {
        if (populatedTask != null) {
            populatedTask.delete(this);
        }
    }

    public void deleteLabel(View view) {
        if (populatedLabel != null) {
            populatedLabel.delete(this);
        }
    }
}