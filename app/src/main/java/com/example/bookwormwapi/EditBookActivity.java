package com.example.bookwormwapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class EditBookActivity extends AppCompatActivity {

    EditText tituloInputUd, autorInputUd, isbnInputUd;
    String id, titulo, autor, isbn;
    Button button_updateBook, buttonDeleteBook;
    FloatingActionButton getBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        tituloInputUd = findViewById(R.id.tituloInputUd);
        autorInputUd = findViewById(R.id.autorInputUd);
        isbnInputUd = findViewById(R.id.isbnInputUd);
        button_updateBook = findViewById(R.id.button_updateBook);
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook);

        String API_URL = "https://c023-179-9-146-22.ngrok-free.app/";

        getIntentData();

        button_updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update book using PUT request to API
                String url = API_URL;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", id);
                    jsonObject.put("titulo", tituloInputUd.getText().toString());
                    jsonObject.put("autor", autorInputUd.getText().toString());
                    jsonObject.put("isbn", isbnInputUd.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Update successful
                                Toast.makeText(EditBookActivity.this, "Se actualizó correctamente el libro", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(EditBookActivity.this, "Error actualizando el libro", Toast.LENGTH_SHORT).show();
                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(EditBookActivity.this);
                queue.add(request);
                finish();
            }
        });


        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete book using DELETE request to API
                String url = API_URL + "?id=";

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", id);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + id, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Delete successful
                                Toast.makeText(EditBookActivity.this, "Libro eliminado correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Delete failed
                                Toast.makeText(EditBookActivity.this, "Error eliminando libro", Toast.LENGTH_SHORT).show();
                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(EditBookActivity.this);
                queue.add(request);
                finish();
            }
        });

    }
    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("titulo") && getIntent().hasExtra("autor") && getIntent().hasExtra("isbn")){
            // Obtener datos del intent
            id = getIntent().getStringExtra("id");
            titulo = getIntent().getStringExtra("titulo");
            autor = getIntent().getStringExtra("autor");
            isbn = getIntent().getStringExtra("isbn");
            // Establecer datos en form
            tituloInputUd.setText(titulo);
            autorInputUd.setText(autor);
            isbnInputUd.setText(isbn);

        }else{
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }
}