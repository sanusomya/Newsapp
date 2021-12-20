package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView headline = findViewById(R.id.news_headline);
        TextView disc = findViewById(R.id.news_discription);
        TextView url = findViewById(R.id.news_url);
        headline.setText(News_String.s);
        ArrayList<String> li =  News_String.map.get(News_String.s);
        if(li.get(0).length() == 0) disc.setVisibility(View.INVISIBLE);
        disc.setText(li.get(0));
        if(li.get(2).length() == 0) disc.setVisibility(View.INVISIBLE);
        ImageView rad = findViewById(R.id.random);
        create(li.get(1),rad);
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = li.get(2);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(s));
                startActivity(i);
            }
        });

    }
    private void create(String h, ImageView v){
        if(h == ""){
            Toast.makeText(this,"Empty Response",Toast.LENGTH_LONG).show();
        }
        Picasso.get().load(h).into(v);
    }
}