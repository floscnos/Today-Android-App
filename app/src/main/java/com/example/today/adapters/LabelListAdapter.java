package com.example.today.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today.R;
import com.example.today.models.Label;

import java.util.List;

public class LabelListAdapter extends RecyclerView.Adapter<LabelListAdapter.ViewHolder> {
    private List<Label> data;
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Label label = data.get(position);
        holder.label_list_row_name.setText(label.getName());
        holder.open_detail_of_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(label.getID());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.labels_list_row, parent, false);
        return new ViewHolder(view);
    }

    public LabelListAdapter(List<Label> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView label_list_row_name;
        private ImageButton open_detail_of_label;
        private ActivityResultLauncher<Intent> launcher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label_list_row_name = itemView.findViewById(R.id.labelsListRow_name_tv_id);
            open_detail_of_label = itemView.findViewById(R.id.labelsListRow_info_icon_id);
        }
    }
}
