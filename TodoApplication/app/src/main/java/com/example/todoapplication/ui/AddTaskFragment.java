package com.example.todoapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapplication.database.DataBaseHelper;
import com.example.todoapplication.R;
import com.example.todoapplication.model.Task;

public class AddTaskFragment extends Fragment {

    EditText taskText, taskDescriptionText;
    Button addButton;
    String taskTitle, descriptionTitle;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        taskText = view.findViewById(R.id.taskText);
        taskDescriptionText = view.findViewById(R.id.taskDescriptionText);
        addButton = view.findViewById(R.id.addButton);

        dbHelper = new DataBaseHelper(getContext());

        addButton.setOnClickListener(v -> {
            taskTitle = taskText.getText().toString().trim();
            descriptionTitle = taskDescriptionText.getText().toString();

            if (TextUtils.isEmpty(taskTitle)) {
                taskText.setError("Task cannot be empty!");
            } else {
                if (getActivity() instanceof MainActivity) {
                    Task task = new Task(taskTitle, descriptionTitle);
                    dbHelper.addTask(task);
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }
        });

        return view;
    }

}