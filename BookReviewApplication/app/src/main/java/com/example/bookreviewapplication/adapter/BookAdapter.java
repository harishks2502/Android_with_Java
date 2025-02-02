package com.example.bookreviewapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapplication.R;
import com.example.bookreviewapplication.entity.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private final UpdateBook onUpdateBook;
    private final DeleteBook onDeleteBook;

    public interface UpdateBook {
        void onUpdateBook(Book book);
    }

    public interface DeleteBook {
        void onDeleteBook(Book book);
    }

    public BookAdapter(List<Book> books, UpdateBook updateBook, DeleteBook deleteBook) {
        this.books = books;
        this.onUpdateBook = updateBook;
        this.onDeleteBook = deleteBook;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bookTitle.setText("Book Title: " + book.getBookTitle());
        holder.authorName.setText("Author Name: " + book.getAuthorName());
        holder.bookReview.setText("Book Review: \n" + book.getBookReview());
        holder.updateBookButton.setOnClickListener(v -> {
            if (onUpdateBook != null) {
                onUpdateBook.onUpdateBook(book);
            }
        });
        holder.deleteBookButton.setOnClickListener(v -> {
            if (onDeleteBook != null) {
                onDeleteBook.onDeleteBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, authorName, bookReview;
        Button updateBookButton, deleteBookButton;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            authorName = itemView.findViewById(R.id.authorName);
            bookReview = itemView.findViewById(R.id.bookReview);
            updateBookButton = itemView.findViewById(R.id.updateBookButton);
            deleteBookButton = itemView.findViewById(R.id.deleteBookButton);
        }
    }

}
