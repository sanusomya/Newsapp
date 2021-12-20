package com.example.networking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

public class Sports extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    static final String A = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=65b60fe858bc4714b99452ec6d4f7414";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView li = findViewById(R.id.news_box);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
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
                    Sports.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            create(json);
                            li.setAdapter(adapter);
                        }
                    });
                }
            }
        });
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News_String.s = list.get(i);
                startActivity(new Intent(getApplicationContext(),News.class));
                overridePendingTransition(0,0);
            }
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_sports);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_sports:
                        break;
                    case R.id.bottom_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.bottom_business:
                        startActivity(new Intent(getApplicationContext(),Business.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }
    private void create(String h){
        if(h == ""){
            Toast.makeText(this,"Empty Response",Toast.LENGTH_LONG).show();
        }
        try {
            JSONObject root = new JSONObject(h);
            JSONArray arr = root.getJSONArray("articles");
            for(int i = 0; i<arr.length(); i++){
                JSONObject temp = arr.getJSONObject(i);
                list.add(temp.getString("title"));
                Map<String,ArrayList<String>> map = News_String.map;
                ArrayList<String> li = new ArrayList<>();
                li.add(temp.getString("description"));
                li.add(temp.getString("urlToImage"));
                li.add(temp.getString("url"));
                map.put(temp.getString("title"),li);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}