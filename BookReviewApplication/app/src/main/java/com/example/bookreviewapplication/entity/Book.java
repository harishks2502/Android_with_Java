package com.example.bookreviewapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books_table")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "book_title")
    private String bookTitle;

    @ColumnInfo(name = "author_name")
    private String authorName;

    @ColumnInfo(name = "book_review")
    private String bookReview;

    public Book(String bookTitle, String authorName, String bookReview) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookReview = bookReview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookReview() {
        return bookReview;
    }

    public void setBookReview(String bookReview) {
        this.bookReview = bookReview;
    }

}
