package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTaskButton    = findViewById(R.id.addTaskButton);
        Button allTaskButton    = findViewById(R.id.allTaskButton);
        Button sittingButton    = findViewById(R.id.sittingButton);
        Button buttonTask1      = findViewById(R.id.buttonTask1);
        Button buttonTask2      = findViewById(R.id.buttonTask2);
        Button buttonTask3      = findViewById(R.id.buttonTask3);

        addTaskButton.setOnClickListener(v -> {

            Log.i(TAG, "Button addTaskButton");
            Intent addTaskIntent = new Intent(this, AddTask.class);
            startActivity(addTaskIntent);

        });

        allTaskButton.setOnClickListener(v -> {

            Log.i(TAG, "Button allTaskButton");
            Intent allTaskIntent = new Intent(this, AllTasks.class);
            startActivity(allTaskIntent);

        });


        sittingButton.setOnClickListener(v->{

            Log.i(TAG, "Button sittingButton");
            Intent sittingIntent = new Intent(this, Settings.class);
            startActivity(sittingIntent);

        });


        buttonTask1.setOnClickListener(v->{

            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
            taskDetailIntent.putExtra("Title",buttonTask1.getText().toString());
            startActivity(taskDetailIntent);

        });
        buttonTask2.setOnClickListener(v->{

            Log.i(TAG, "Button buttonTask2");
            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
            taskDetailIntent.putExtra("Title",buttonTask2.getText().toString());
            startActivity(taskDetailIntent);

        });


        buttonTask3.setOnClickListener(v->{

            Log.i(TAG, "Button buttonTask3");

            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
            taskDetailIntent.putExtra("Title",buttonTask3.getText().toString());
            startActivity(taskDetailIntent);
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