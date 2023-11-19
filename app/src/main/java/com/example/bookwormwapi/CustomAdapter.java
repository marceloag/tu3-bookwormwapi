package com.example.bookwormwapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> book_id, titulo, autor, isbn;
    Activity activity;
    public CustomAdapter(Activity activity, Context context, ArrayList<String> book_id, ArrayList<String> titulo, ArrayList<String> autor, ArrayList<String> isbn) {
        this.activity = activity;
        this.context = context;
        this.book_id = new ArrayList<>();
        this.titulo = new ArrayList<>();
        this.autor = new ArrayList<>();
        this.isbn = new ArrayList<>();
        fetchData();
    }

    private void fetchData() {
        String url = "https://c023-179-9-146-22.ngrok-free.app/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response and add the data to the ArrayLists
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject book = response.getJSONObject(i);
                                book_id.add(book.getString("id"));
                                titulo.add(book.getString("titulo"));
                                autor.add(book.getString("autor"));
                                isbn.add(book.getString("isbn"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view from XML and return the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_id_txt.setText(book_id.get(holder.getAdapterPosition()));
        holder.titulo_txt.setText(titulo.get(holder.getAdapterPosition()));
        holder.autor_txt.setText(autor.get(holder.getAdapterPosition()));
        holder.isbn_txt.setText(isbn.get(holder.getAdapterPosition()));

        // Enviar informacion del libro clickeado
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_ = String.valueOf(book_id.get(holder.getAdapterPosition()));
                Log.i("BookId", "Value: " + id_);
                Intent intent = new Intent(context, EditBookActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(holder.getAdapterPosition())));
                intent.putExtra("titulo", String.valueOf(titulo.get(holder.getAdapterPosition())));
                intent.putExtra("autor", String.valueOf(autor.get(holder.getAdapterPosition())));
                intent.putExtra("isbn", String.valueOf(isbn.get(holder.getAdapterPosition())));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, titulo_txt, autor_txt, isbn_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            titulo_txt = itemView.findViewById(R.id.titulo_txt);
            autor_txt = itemView.findViewById(R.id.autor_txt);
            isbn_txt = itemView.findViewById(R.id.isbn_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}