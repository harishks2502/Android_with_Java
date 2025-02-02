package com.example.bookreviewapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookreviewapplication.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);

    @Query("SELECT * FROM books_table")
    List<Book> getAllBooks();

}
