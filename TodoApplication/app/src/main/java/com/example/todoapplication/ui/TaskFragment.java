package com.example.todoapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.database.DataBaseHelper;
import com.example.todoapplication.R;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.adapter.TaskAdapter;

import java.util.List;

public class TaskFragment extends Fragment {
    private RecyclerView recyclerView;
    ImageView fabAdd;
    private List<Task> tasks;
    private TaskAdapter adapter;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        fabAdd = view.findViewById(R.id.fab_add);


        dbHelper = new DataBaseHelper(getContext());
        tasks = dbHelper.getAllTasks();

        adapter = new TaskAdapter(tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v ->
                ((MainActivity) requireActivity()).replaceFragment(new AddTaskFragment(), "AddTaskFragment"));

        return view;
    }

}