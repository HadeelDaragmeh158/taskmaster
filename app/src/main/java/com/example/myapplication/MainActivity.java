package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button allTaskButton = findViewById(R.id.allTaskButton);

        addTaskButton.setOnClickListener(v -> {

            Intent addTaskIntent = new Intent(this, AddTask.class);
            startActivity(addTaskIntent);

        });

        allTaskButton.setOnClickListener(v -> {

            Intent allTaskIntent = new Intent(this, AllTasks.class);
            startActivity(allTaskIntent);

        });

    }


    @Override
    protected void onStart() {
            super.onStart();
            Log.i(TAG, "onStart: called");
            }

    @Override
    protected void onRestart() {
            super.onRestart();
            Log.i(TAG, "onRestart: called");
            }

    @Override
    protected void onResume() {
            Log.i(TAG, "onResume: called - The App is VISIBLE");
            super.onResume();
            }

    @Override
    protected void onPause() {
            Log.i(TAG, "onPause: called");
            super.onPause();
            }

    @Override
    protected void onStop() {
            Log.i(TAG, "onStop: called");
            super.onStop();
            }

    @Override
    protected void onDestroy() {
            Log.i(TAG, "onDestroy: called");
            super.onDestroy();
            }
}