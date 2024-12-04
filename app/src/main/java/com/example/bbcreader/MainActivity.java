package com.example.bbcreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    ListView listView;
    RssAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, findViewById(R.id.content_frame));


        // ListViewSetup
        listView = findViewById(R.id.mainListView);
        adapter = new RssAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        new FetchFeedTask(adapter).execute();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RssItem item = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("pubDate", item.getPubDate());
            intent.putExtra("link", item.getLink());
            startActivity(intent);
        });

    }
}