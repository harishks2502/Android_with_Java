package com.example.bookreviewapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookreviewapplication.R;
import com.example.bookreviewapplication.adapter.BookAdapter;
import com.example.bookreviewapplication.dao.BookDao;
import com.example.bookreviewapplication.database.AppDatabase;
import com.example.bookreviewapplication.entity.Book;

import java.util.List;
import java.util.concurrent.Executors;


public class DashboardFragment extends Fragment {

    Button addBookButton;
    private RecyclerView recyclerView;
    private List<Book> books;
    private BookAdapter bookAdapter;
    private AppDatabase db;
    private BookDao bookDao;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        assert inflater != null;
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDatabase.getInstance(requireContext());
        bookDao = db.bookDao();

        addBookButton = view.findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).replaceFragment(new AddBookFragment(), "AddBookFragment");
        });

        recyclerView = view.findViewById(R.id.booksRecyclerView);

        Executors.newSingleThreadExecutor().execute(() -> {
            books = bookDao.getAllBooks();

            requireActivity().runOnUiThread(() -> {
                bookAdapter = new BookAdapter(books, this::updateBook, this::deleteBook);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(bookAdapter);
            });

        });

    }


    private void updateBook(Book book) {
    }

    private void deleteBook(Book book) {
    }

}