package com.example.today;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.today.adapters.ClickListener;
import com.example.today.adapters.LabelListAdapter;
import com.example.today.models.Label;

import java.util.List;

public class LabelsListActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;
    private RecyclerView recyclerView;
    private List<Label> labels;

    private SharedPreferences preferences;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labels_list);

        preferences = getSharedPreferences("com.example.today", Context.MODE_PRIVATE);
        userID = preferences.getInt("userId", 0);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        labels.clear();
                        labels.addAll(Label.getAll(getApplicationContext(), userID));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });

        recyclerView = findViewById(R.id.labelsList_labels_rv_id);

        labels = Label.getAll(this, userID);

        LabelListAdapter adapter = new LabelListAdapter(labels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        adapter.setClickListener(new ClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("LABEL", true);
                intent.putExtra("LABELID", id);
                launcher.launch(intent);
            }
        });
    }

    public void gotoEditActivity(View view) {
        // For creating new tasks
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("LABEL", true);
        launcher.launch(intent);
    }
}