package com.example.booksapp;

import java.util.Objects;

public class Book {
    private String title;
    private String authors;
    private String thumbnail;
    private int pageCount;


    public Book(String title, String authors, String thumbnail, int pageCount) {
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
        this.pageCount = pageCount;
    }





    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return title + " by " + authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authors);
    }
}
