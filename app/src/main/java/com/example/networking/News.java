package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

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
        headline.setText(News_String.s);
        disc.setText(News_String.map.get(News_String.s));

        ImageView rad = findViewById(R.id.random);

        final String A = "https://api.pexels.com/v1/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(A).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    News.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            create(json,rad);
                        }
                    });
                }
            }
        });
    }
    private void create(String h, ImageView v){
        if(h == ""){
            Toast.makeText(this,"Empty Response",Toast.LENGTH_LONG).show();
        }
        try {
            JSONObject root = new JSONObject(h);
            String s = root.getString("message");
            Picasso.get().load(s).into(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}