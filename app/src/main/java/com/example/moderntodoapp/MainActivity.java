package com.example.moderntodoapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;

    ArrayList<TaskModel> list;
    TaskAdapter adapter;

    TextView totalTask, completedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAdd);

        totalTask = findViewById(R.id.totalTask);
        completedTask = findViewById(R.id.completedTask);

        list = new ArrayList<>();

        list.add(new TaskModel("Complete Project"));
        list.add(new TaskModel("Study DBMS"));
        list.add(new TaskModel("Prepare Presentation"));

        adapter = new TaskAdapter(this, list, totalTask, completedTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateStats();

        fab.setOnClickListener(v -> {

            EditText editText = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Add New Task")
                    .setView(editText)

                    .setPositiveButton("Add", (dialog, which) -> {

                        String task = editText.getText().toString();

                        if (!task.isEmpty()) {

                            list.add(new TaskModel(task));

                            adapter.notifyDataSetChanged();

                            updateStats();
                        }

                    })

                    .setNegativeButton("Cancel", null)
                    .show();

        });
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
}