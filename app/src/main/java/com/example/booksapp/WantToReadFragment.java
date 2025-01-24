package com.example.booksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WantToReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_want_to_read, container, false);

        recyclerView = view.findViewById(R.id.wantToReadRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookList = BookListManager.getBookList(getActivity(), "wantToReadBooks");
        bookAdapter = new BookAdapter(getActivity(), bookList);
        recyclerView.setAdapter(bookAdapter);


        return view;

    }

}
