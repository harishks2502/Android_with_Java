package com.example.bookreviewapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookreviewapplication.R;
import com.example.bookreviewapplication.adapter.BookAdapter;
import com.example.bookreviewapplication.dao.BookDao;
import com.example.bookreviewapplication.database.AppDatabase;
import com.example.bookreviewapplication.entity.Book;

import java.util.concurrent.Executors;


public class AddBookFragment extends Fragment {

    EditText bookTitle, authorName, bookReview;
    Button addButton;


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        assert inflater != null;
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookTitle = view.findViewById(R.id.bookTitle);
        authorName = view.findViewById(R.id.authorName);
        bookReview = view.findViewById(R.id.bookReview);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String book = bookTitle.getText().toString();
            String author = authorName.getText().toString();
            String review = bookReview.getText().toString();

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getInstance(this.requireContext());
                BookDao bookDao = db.bookDao();
                Book b = new Book(book, author, review);
                bookDao.insertBook(b);
                requireActivity().runOnUiThread(() -> {
                    ((MainActivity) requireActivity()).replaceFragment(new DashboardFragment(), "DashboardFragment");
                });
            });
        });

    }

}