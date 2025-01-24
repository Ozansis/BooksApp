package com.example.booksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView authorsTextView;
    private ImageView thumbnailImageView;
    private TextView pageCountTextView;
    private Button addToReadButton;
    private Button addToCurrentlyReadingButton;
    private Button addToWantToReadButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.bookTitle);
        authorsTextView = findViewById(R.id.bookAuthors);
        thumbnailImageView = findViewById(R.id.bookThumbnail);
        pageCountTextView = findViewById(R.id.bookPageCount);
        addToReadButton = findViewById(R.id.addToReadButton);
        addToCurrentlyReadingButton = findViewById(R.id.addToReadingButton);
        addToWantToReadButton = findViewById(R.id.addToWantToReadButton);

        // Intent'ten verileri al
        String title = getIntent().getStringExtra("title");
        String authors = getIntent().getStringExtra("authors");
        String thumbnailUrl = getIntent().getStringExtra("thumbnail");
        int pageCount = getIntent().getIntExtra("pageCount", 0);

        // Verileri görünümde ayarla
        titleTextView.setText(title);
        authorsTextView.setText(authors);
        pageCountTextView.setText(String.valueOf(pageCount) + " pages");
        Picasso.get().load(thumbnailUrl).into(thumbnailImageView);

        addToReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(title, authors, thumbnailUrl, pageCount);
                BookListManager.addBookToList(BookDetailActivity.this, book, "readBooks");
                Toast.makeText(BookDetailActivity.this, "Book added to Read list", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addToCurrentlyReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(title, authors, thumbnailUrl, pageCount);
                BookListManager.addBookToList(BookDetailActivity.this, book, "currentlyReadingBooks");
                Toast.makeText(BookDetailActivity.this, "Book added to Currently Reading list", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addToWantToReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(title, authors, thumbnailUrl, pageCount);
                BookListManager.addBookToList(BookDetailActivity.this, book, "wantToReadBooks");
                Toast.makeText(BookDetailActivity.this, "Book added to Want to Read list", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
