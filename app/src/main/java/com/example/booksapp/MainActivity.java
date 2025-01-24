package com.example.booksapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private RequestQueue requestQueue;
    private Button profileButton;
    private Button scanQRButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
         searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
       profileButton = findViewById(R.id.profileButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this,bookList);
        recyclerView.setAdapter(bookAdapter);

        requestQueue = Volley.newRequestQueue(this);
       scanQRButton = findViewById(R.id.scanQRButton);
        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QRScanActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    searchBooks(query);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });




    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String qrContent = data.getStringExtra("SCAN_RESULT");
                searchBooks(qrContent);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "QR Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchBooks(String query) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("API Response", response.toString());
                        try {
                            JSONArray items = response.getJSONArray("items");
                            bookList.clear();
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                                String title = volumeInfo.getString("title");
                                String authors = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").join(", ") : "Unknown author";
                                String thumbnail = null;
                                String smallThumbnailUrl = null;
                                int pageCount = volumeInfo.has("pageCount") ? volumeInfo.getInt("pageCount") : 0;
                                if (volumeInfo.has("imageLinks")) {
                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    if (imageLinks.has("thumbnail")) {
                                        thumbnail = imageLinks.getString("thumbnail");
                                        smallThumbnailUrl = imageLinks.getString("smallThumbnail");
                                        Log.d("Thumbnail URL", smallThumbnailUrl); // Thumbnail URL'sini günlüğe kaydedin
                                    }
                                }
                                bookList.add(new Book(title, authors, smallThumbnailUrl,pageCount));
                            }
                            bookAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }


}
