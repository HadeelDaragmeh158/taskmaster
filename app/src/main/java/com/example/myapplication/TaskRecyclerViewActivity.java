package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerViewActivity  extends AppCompatActivity {

    List<Task> taskDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_recycler_view_adapter);




        // get the recycler view object

        RecyclerView recyclerView = findViewById(R.id.recycler_view);



        // create an adapter -> TaskRecyclerViewAdapterActivity

        TaskRecyclerViewAdapter taskRecyclerViewAdapterActivity = new TaskRecyclerViewAdapter(
                taskDataList, position -> {
            Toast.makeText(
                    TaskRecyclerViewActivity.this,
                    "The Task clicked => " + taskDataList.get(position).getTitle(),Toast.LENGTH_SHORT).show();

        });


        // set adapter on recycler view
        recyclerView.setAdapter(taskRecyclerViewAdapterActivity);


        // set other important properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}