package com.example.bbcreader;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FavoritesActivity extends BaseActivity {
    private DatabaseHelper dbHelper;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> favoriteList;
    private ArrayList<Integer> favoriteIds;

    // Variables for the snackbar
    private RssItem recentlyDeletedItem;
    private int recentlyDeletedItemId;
    private int recentlyDeletedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favorites, findViewById(R.id.content_frame));

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.favoritesListView);
        favoriteList = new ArrayList<>();
        favoriteIds = new ArrayList<>();

        TextView titleText = findViewById(R.id.section_title);

        titleText.setText(R.string.favMenuToolbarText);

        loadFavorites();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = favoriteIds.get(position);
                Cursor cursor = dbHelper.getAllFavorites();

                if (cursor.moveToPosition(position)) {
                    // SuppressLint is to get rid of the warning about potential
                    // negative numbers from getColumnIndex below.
                    @SuppressLint("Range")
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    @SuppressLint("Range")
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    @SuppressLint("Range")
                    String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
                    @SuppressLint("Range")
                    String link = cursor.getString(cursor.getColumnIndex("link"));
                    Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("pubDate", pubDate);
                    intent.putExtra("link", link);
                    intent.putExtra("from_favorites", true); // Add this flag
                    startActivity(intent);
                }
                cursor.close();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            @SuppressLint("Range")
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
                builder.setMessage(R.string.del_confirm);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recentlyDeletedItemPosition = position;
                        recentlyDeletedItemId = favoriteIds.get(position);

                        Cursor cursor = dbHelper.getAllFavorites();
                        if (cursor.moveToPosition(position)) {
                            recentlyDeletedItem = new RssItem();
                            recentlyDeletedItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                            recentlyDeletedItem.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                            recentlyDeletedItem.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
                            recentlyDeletedItem.setLink(cursor.getString(cursor.getColumnIndex("link")));
                        }
                        cursor.close();

                        dbHelper.deleteFavorite(recentlyDeletedItemId);
                        loadFavorites();
                        showUndoSnackbar();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    // This is to get rid of the warning about potential negative numbers from getColumnIndex below.
    @SuppressLint("Range")

    private void loadFavorites() {
        favoriteList.clear();
        favoriteIds.clear();
        Cursor cursor = dbHelper.getAllFavorites();
        if (cursor.moveToFirst()) {
            do {
                favoriteIds.add(cursor.getInt(cursor.getColumnIndex("id")));
                favoriteList.add(cursor.getString(cursor.getColumnIndex("title")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteList);
        listView.setAdapter(adapter);
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), "Item deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addFavorite(recentlyDeletedItem);
                loadFavorites();
            }
        });
        snackbar.show();
    }

    @Override
    protected void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle("Help").setMessage("This is your article favourites list." +
                        "From here, you can go to and tap any article to view it. Press and hold on a favourite" +
                        "to delete it.")
                .setPositiveButton(android.R.string.ok, null).show();
    }

}
