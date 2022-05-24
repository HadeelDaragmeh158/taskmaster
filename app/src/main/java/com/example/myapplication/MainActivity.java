package com.example.myapplication;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<String> titles = new ArrayList<>();
    private Task[] tasks = new Task[]{
            new Task("Task1","basic of Android ","New"),
            new Task("Task2","Data in task master ","Assigned"),
            new Task("Task3","RecyclerViews for Displaying Lists of Data ","Complete"),
            new Task("Task4","Room ","New")
    };


    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button addTaskButton    = findViewById(R.id.addTaskButton);
        Button allTaskButton    = findViewById(R.id.allTaskButton);
        Button sittingButton    = findViewById(R.id.sittingButton);
//        Button buttonTask1      = findViewById(R.id.buttonTask1);
//        Button buttonTask2      = findViewById(R.id.buttonTask2);
//        Button buttonTask3      = findViewById(R.id.buttonTask3);

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

//
//        buttonTask1.setOnClickListener(v->{
//
//            Log.i(TAG, "Button buttonTask1");
//
//            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
//            taskDetailIntent.putExtra("Title",buttonTask1.getText().toString());
//            startActivity(taskDetailIntent);
//
//        });
//
//
//        buttonTask2.setOnClickListener(v->{
//
//            Log.i(TAG, "Button buttonTask2");
//
//            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
//            taskDetailIntent.putExtra("Title",buttonTask2.getText().toString());
//            startActivity(taskDetailIntent);
//
//        });
//
//
//        buttonTask3.setOnClickListener(v->{
//
//            Log.i(TAG, "Button buttonTask3");
//
//            Intent taskDetailIntent = new Intent(this,TaskDetail.class);
//            taskDetailIntent.putExtra("Title",buttonTask3.getText().toString());
//            startActivity(taskDetailIntent);
//
//        });

        List<Task> tasks = AppDatabase.getInstance(getApplicationContext()).userDao().getAllTasks();

        ListView ListTasks = findViewById(R.id.tasks);
        ArrayAdapter<Task> taskArrayAdapter = new ArrayAdapter<Task>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text2,
                tasks
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView title = (TextView) view.findViewById(android.R.id.text1);
                    TextView body = (TextView) view.findViewById(android.R.id.text2);

                    title.setText(tasks.get(position).getTitle());
                    body.setText(tasks.get(position).getBody());

                    return view;
            }
        };
//        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent taskIntent = new Intent(getApplicationContext(),TaskDetail.class);
//                taskIntent.putExtra("title",tasks[i].getTitle());
//                taskIntent.putExtra("body",tasks[i].getBody());
//                taskIntent.putExtra("state",tasks[i].getState());
//                startActivity(taskIntent);
//            }
//        });
        ListTasks.setAdapter(taskArrayAdapter);

        ListTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent taskIntent = new Intent(getApplicationContext(),TaskDetail.class);
                taskIntent.putExtra("title",tasks.get(position).getTitle());
                taskIntent.putExtra("body",tasks.get(position).getBody());
                taskIntent.putExtra("state",tasks.get(position).getState());
                startActivity(taskIntent);
            }
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