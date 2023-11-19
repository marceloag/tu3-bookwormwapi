package com.example.bookwormwapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private CustomAdapter adapter;
    RecyclerView BookList;
    FloatingActionButton btn_add_book;
    ArrayList<String> book_id, titulo, autor, isbn;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACTION_MY_ACTIVITY_RESUMED")) {
                refreshList();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookList = findViewById(R.id.BookList);
        BookList.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter adapter = new CustomAdapter(MainActivity.this,this, book_id, titulo, autor, isbn);
        BookList.setAdapter(adapter);

        btn_add_book = findViewById(R.id.btn_add_book);
        btn_add_book.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, addBookActivity.class);
            startActivity(intent);
        });
        registerReceiver(myReceiver, new IntentFilter("ACTION_MY_ACTIVITY_RESUMED"));
    }

    @Override
    protected void onResume()  {
        super.onResume();
        refreshList();
    }

    public void refreshList() {
        adapter = new CustomAdapter(MainActivity.this,this, book_id, titulo, autor, isbn);
        BookList.setAdapter(adapter);
    }


}