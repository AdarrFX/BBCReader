package com.example.bbcreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    // The Base activity contains the toolbar and navigation drawer
    // All other activities are extended from this one so they also have the same components

    ListView listView;
    RssAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.id.favoritesListView);

        // Adding the help button and setting it to be clickable
        // It will access overridden methods in each activity that will build an alertdialog
        // And display it
        ImageButton helpButton = toolbar.findViewById(R.id.help_button);
        helpButton.setOnClickListener(v -> showHelpDialog());

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup the Navigation View
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // Handle home action
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class); startActivity(intent);
                } else if (id == R.id.nav_favorites) {
                    // Handle favorites action
                    Intent intent = new Intent(BaseActivity.this, FavoritesActivity.class); startActivity(intent);
                } else if (id == R.id.nav_help) {
                    // Handle About action
                    Intent intent = new Intent(BaseActivity.this, AboutActivity.class); startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    // The overridable abstract function for other activities to implement
    protected abstract void showHelpDialog();

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




}