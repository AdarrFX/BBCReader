package com.example.bbcreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;



public class AboutActivity extends BaseActivity {

    private EditText editMessage;
    private TextView textStoredMessage;
    private SharedPreferences sharedPreferences;

    //Setup the parameters to store the message
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_MESSAGE = "stored_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about, findViewById(R.id.content_frame));

        // Initialize views
        editMessage = findViewById(R.id.edit_message);
        Button buttonSubmit = findViewById(R.id.button_submit);
        textStoredMessage = findViewById(R.id.text_stored_message);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Load the previously stored message
        loadStoredMessage();

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage();
            }
        });
    }

    private void saveMessage() {
        String message = editMessage.getText().toString();

        // Save the message in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MESSAGE, message);
        editor.apply();

        // Update the displayed stored message
        loadStoredMessage();

        // With help from https://www.geeksforgeeks.org/how-to-programmatically-hide-android-soft-keyboard/
        // Hide the keyboard on submit
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    // Load the stored shared prefs message into the text box
    private void loadStoredMessage() {
        String storedMessage = sharedPreferences.getString(KEY_MESSAGE, "No message stored");
        textStoredMessage.setText("Stored Message: " + storedMessage);
    }

    @Override
    protected void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle("Help").setMessage(R.string.AboutHelp)
                .setPositiveButton(android.R.string.ok, null).show();
    }
}