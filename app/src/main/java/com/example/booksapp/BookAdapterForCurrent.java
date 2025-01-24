package com.example.booksapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapterForCurrent extends RecyclerView.Adapter<BookAdapterForCurrent.BookViewHolder> {

    private Context context;
    private List<Book> bookList;
    private static final String PREFS_NAME = "book_prefs";

    public BookAdapterForCurrent(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookadapterforcurrent, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthors());

        // SharedPreferences'ten mevcut sayfa numarasını al
        int currentPage = getCurrentPage(book.getTitle());
        holder.currentPage.setText(String.valueOf(currentPage));

        int bookPageCount = book.getPageCount();
        int percent = 0;
        if (bookPageCount > 0) {
            percent = (currentPage * 100) / bookPageCount;
        }
        holder.progressBar.setProgress(percent);
        holder.progressBarText.setText("%" + percent);

        holder.updatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPageStr = holder.currentPage.getText().toString();
                if (!currentPageStr.isEmpty()) {
                    int currentPage = Integer.parseInt(currentPageStr);
                    int bookPageCount = book.getPageCount();
                    if (bookPageCount > 0) {
                        if( currentPage <=bookPageCount){
                            int percent = (currentPage * 100) / bookPageCount;
                            holder.progressBar.setProgress(percent);
                            holder.progressBarText.setText("%" + percent);

                            // SharedPreferences'a mevcut sayfa numarasını kaydet
                            saveCurrentPage(book.getTitle(), currentPage);

                        }
                        else{
                            Toast.makeText(context, "Invalid Page", Toast.LENGTH_SHORT).show();


                        }

                    }
                }
            }
        });

        Picasso.get().load(book.getThumbnail()).into(holder.bookThumbnail);

        holder.bookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthors());
                intent.putExtra("thumbnail", book.getThumbnail());
                intent.putExtra("pageCount", book.getPageCount());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private void saveCurrentPage(String bookTitle, int currentPage) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(bookTitle, currentPage);
        editor.apply();
    }

    private int getCurrentPage(String bookTitle) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(bookTitle, 0);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookThumbnail;
        ProgressBar progressBar;
        TextView progressBarText;
        EditText currentPage;
        Button updatePage;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookThumbnail = itemView.findViewById(R.id.bookThumbnail);
            progressBar = itemView.findViewById(R.id.progress_bar);
            progressBarText = itemView.findViewById(R.id.progressText);
            currentPage = itemView.findViewById(R.id.currentPage);
            updatePage = itemView.findViewById(R.id.updateButton);
        }
    }
}
