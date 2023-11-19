package com.example.bookwormwapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class addBookActivity extends AppCompatActivity {

    EditText tituloInput, autorInput, isbnInput;
    Button button_addBook;
    FloatingActionButton getBackHome;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        String API_URL = "https://c023-179-9-146-22.ngrok-free.app/";

        tituloInput = findViewById(R.id.tituloInput);
        autorInput = findViewById(R.id.autorInput);
        isbnInput = findViewById(R.id.isbnInput);
        button_addBook = findViewById(R.id.button_addBook);
        getBackHome = findViewById(R.id.backHome);


        button_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String url = API_URL;

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", null);
                        jsonObject.put("titulo", tituloInput.getText().toString());
                        jsonObject.put("autor", autorInput.getText().toString());
                        jsonObject.put("isbn", isbnInput.getText().toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Update successful
                                    Toast.makeText(addBookActivity.this, "Libro creado exitosamente", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(addBookActivity.this, "Error creando libro", Toast.LENGTH_SHORT).show();
                                }
                            });

                    RequestQueue queue = Volley.newRequestQueue(addBookActivity.this);
                    queue.add(request);
                    tituloInput.setText("");
                    autorInput.setText("");
                    isbnInput.setText("");
                }
        });

        getBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}