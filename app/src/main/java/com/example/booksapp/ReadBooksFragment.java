package com.example.booksapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReadBooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private TextView numReadBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_books, container, false);
        numReadBooks=view.findViewById(R.id.NumberOfBooksReadTextView);
        recyclerView = view.findViewById(R.id.readBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookList = BookListManager.getBookList(getActivity(), "readBooks");
        bookAdapter = new BookAdapter(getActivity(), bookList);
        recyclerView.setAdapter(bookAdapter);
        numReadBooks.setText(String.valueOf(bookList.size()));
        return view;
    }
}
