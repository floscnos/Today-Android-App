package com.example.today.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today.R;
import com.example.today.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<Task> data;
    private ClickListener detailClickListener;
    private ClickListener doneClickListener;

    public void setDetailClickListener(ClickListener detailClickListener) {
        this.detailClickListener = detailClickListener;
    }

    public void setDoneClickListener(ClickListener doneClickListener) {
        this.doneClickListener = doneClickListener;
    }

    @Override
    public int getItemCount() { return data.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = data.get(position);
        holder.open_tasks_row_title.setText(task.getName());

        if (task.getDone()) {
            holder.task_done_button.setImageResource(R.drawable.circle_checked);
        }

        holder.open_detail_of_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailClickListener.onClick(task.getID());
            }
        });

        // Onlick listener for done button
        holder.task_done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneClickListener.onClick(task.getID());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.tasks_list_task_row, parent, false);
        return new ViewHolder(view);
    }

    public TaskListAdapter(List<Task> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView open_tasks_row_title;
        private ImageButton task_done_button;
        private ImageButton open_detail_of_task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            open_tasks_row_title = itemView.findViewById(R.id.tasksListRow_name_tv_id);
            task_done_button = itemView.findViewById(R.id.tasksListRow_done_btn_id);
            open_detail_of_task = itemView.findViewById(R.id.tasksListRow_info_icon_id);
        }
    }
}
