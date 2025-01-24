package com.example.booksapp;

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

public class CurrentlyReadingFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapterForCurrent bookAdapter;
    private List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currently_reading, container, false);

        recyclerView = view.findViewById(R.id.currentlyReadingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookList = BookListManager.getBookList(getActivity(), "currentlyReadingBooks");
        bookAdapter = new BookAdapterForCurrent(getActivity(), bookList);
        recyclerView.setAdapter(bookAdapter);


        return view;
    }
}
