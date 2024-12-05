package com.example.bbcreader;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.widget.ListView;
import android.widget.TextView;

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

        TextView titleText = findViewById(R.id.section_title);
        titleText.setText(R.string.mainMenuToolbarText);

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

    @Override
    protected void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle("Help").setMessage(R.string.MainHelp)
                .setPositiveButton(android.R.string.ok, null).show();
    }

}