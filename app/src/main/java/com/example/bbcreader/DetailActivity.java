package com.example.bbcreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends BaseActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        getLayoutInflater().inflate(R.layout.activity_detail, findViewById(R.id.content_frame));

        dbHelper = new DatabaseHelper(this);

        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        TextView pubDate = findViewById(R.id.pubDate);
        TextView linkTextView = findViewById(R.id.linkTextView);
        Button linkButton = findViewById(R.id.linkButton);
        Button favoriteButton = findViewById(R.id.favoriteButton);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        pubDate.setText(intent.getStringExtra("pubDate"));
        String link = intent.getStringExtra("link");
        boolean fromFavorites = intent.getBooleanExtra("from_favorites", false);

        TextView titleText = findViewById(R.id.section_title);
        titleText.setText(R.string.detailMenuToolbarText);

        if (fromFavorites) {
            favoriteButton.setVisibility(View.GONE);
        }

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });

        // With help from
        //https://stackoverflow.com/questions/43025993/how-do-i-open-a-browser-on-clicking-a-text-link-in-textview

        linkTextView.setText(link);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        });

        favoriteButton.setOnClickListener(v -> {
            RssItem item = new RssItem();
            item.setTitle(title.getText().toString());
            item.setDescription(description.getText().toString());
            item.setPubDate(pubDate.getText().toString());
            item.setLink(link);
            dbHelper.addFavorite(item);
            favoriteButton.setEnabled(false); // Disable the button
            favoriteButton.setText(R.string.favourited);
            Toast.makeText(DetailActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle("Help").setMessage(R.string.DetailHelp)
                .setPositiveButton(android.R.string.ok, null).show();
    }

}