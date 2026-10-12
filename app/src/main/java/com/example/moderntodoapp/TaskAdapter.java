package com.example.moderntodoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    ArrayList<TaskModel> list;

    TextView totalTask, completedTask;

    public TaskAdapter(Context context,
                       ArrayList<TaskModel> list,
                       TextView totalTask,
                       TextView completedTask) {

        this.context = context;
        this.list = list;
        this.totalTask = totalTask;
        this.completedTask = completedTask;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.task_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaskModel model = list.get(position);

        holder.taskTitle.setText(model.getTitle());

        holder.checkBox.setChecked(model.isCompleted());

        if (model.isCompleted()) {

            holder.taskTitle.setPaintFlags(
                    holder.taskTitle.getPaintFlags() |
                            Paint.STRIKE_THRU_TEXT_FLAG
            );

        } else {

            holder.taskTitle.setPaintFlags(0);
        }

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            model.setCompleted(isChecked);

            notifyDataSetChanged();

            updateStats();
        });

        // DELETE
        holder.deleteBtn.setOnClickListener(v -> {

            list.remove(position);

            notifyDataSetChanged();

            updateStats();
        });

        // UPDATE
        holder.updateBtn.setOnClickListener(v -> {

            EditText editText = new EditText(context);

            editText.setText(model.getTitle());

            new AlertDialog.Builder(context)
                    .setTitle("Update Task")
                    .setView(editText)

                    .setPositiveButton("Update", (dialog, which) -> {

                        model.setTitle(editText.getText().toString());

                        notifyDataSetChanged();
                    })

                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateStats() {

        int completed = 0;

        for (TaskModel model : list) {

            if (model.isCompleted()) {
                completed++;
            }
        }

        totalTask.setText(String.valueOf(list.size()));

        completedTask.setText(String.valueOf(completed));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;

        ImageView deleteBtn, updateBtn;

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.taskTitle);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);

            updateBtn = itemView.findViewById(R.id.updateBtn);

            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}