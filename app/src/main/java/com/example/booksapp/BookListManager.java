package com.example.booksapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookListManager {
    private static final String PREFS_NAME = "book_prefs";

    public static void addBookToList(Context context, Book book, String listName) {
        removeBookFromAllLists(context, book); // Kitap eklenmeden önce diğer listelerden kaldırılıyor

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<Book> bookList = getBookList(context, listName);
        if (!bookList.contains(book)) { // Kitap daha önce listeye eklenmemişse eklenir
            bookList.add(book);
        }

        Gson gson = new Gson();
        String json = gson.toJson(bookList);
        editor.putString(listName, json);
        editor.apply();
    }

    public static List<Book> getBookList(Context context, String listName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(listName, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        List<Book> bookList = gson.fromJson(json, type);

        return bookList != null ? bookList : new ArrayList<Book>();
    }

    public static void removeBookFromAllLists(Context context, Book book) {
        removeBookFromList(context, book, "readBooks");
        removeBookFromList(context, book, "currentlyReadingBooks");
        removeBookFromList(context, book, "wantToReadBooks");
    }

    public static void removeBookFromList(Context context, Book book, String listName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        List<Book> bookList = getBookList(context, listName);

        if (bookList.contains(book)) {
            bookList.remove(book);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(bookList);
            editor.putString(listName, json);
            editor.apply();
        }
    }
}
