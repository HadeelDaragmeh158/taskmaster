package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private static final String TAG = "MainActivity";
    private List<String> titles = new ArrayList<>();
    List<Task>  allTasks = new ArrayList<>();

    private String TITLE = "title";
    private String ID = "taskId";
    private Handler handler;
//    private Task[] tasks = new Task[]{
//            Task.builder().title("Task1").description("").build(),
//            new Task("Task2","Data in task master ","Assigned"),
//            new Task("Task3","RecyclerViews for Displaying Lists of Data ","Complete"),
//            new Task("Task4","Room ","New")
//    };


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
//
        configurAmplify();

//        Task item = com.amplifyframework.datastore.generated.model.Task.builder()
//                .title(myTaskStr)
//                .description(doSomthingStr )
//                .status(state)
//                .build();

        handler = new Handler(Looper.getMainLooper(), msg -> {
            String data = msg.getData().getString(TITLE);
            String taskId = msg.getData().getString(ID);


            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(ID, taskId);

            startActivity(intent);
            return true;
        });
//         Data store query
        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();

                        Log.i(TAG, "==== Task ====");
                        Log.i(TAG, "Name: " + task.getTitle());
                    }
                },
                failure -> Log.e(TAG, "Could not query DataStore", failure)
        );
//


        Amplify.API.query(
                ModelQuery.list(Task.class, Task.TITLE.contains("Task")),
                response -> {
                    if (response.hasData()) {
                        for (Task task : response.getData())
                            allTasks.add(task);
                         }
                    },
                error ->{
                    Log.e(TAG,"ERROR", error);
                }
//
        );

        Button addTaskButton    = findViewById(R.id.addTaskButton);
        Button allTaskButton    = findViewById(R.id.allTaskButton);
        Button sittingButton    = findViewById(R.id.sittingButton);


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
        List<Task> allTtasks = new ArrayList<>();

        ListView listView = findViewById(R.id.tasks);
        ArrayAdapter<Task> taskArrayAdapter = new ArrayAdapter<Task>(
                this,
                android.R.layout.simple_list_item_1,
                allTtasks
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView title = (TextView) view.findViewById(android.R.id.text1);
                TextView body = (TextView) view.findViewById(android.R.id.text2);

                title.setText(allTtasks.get(position).getTitle());
                body.setText(allTtasks.get(position).getDescription());
                return  null;
            };
        };


        listView.setAdapter(taskArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent taskIntent = new Intent(getApplicationContext(),TaskDetail.class);
                taskIntent.putExtra("title",allTtasks.get(position).getTitle());
                taskIntent.putExtra("body",allTtasks.get(position).getDescription());
                taskIntent.putExtra("state",allTtasks.get(position).getStatus());
                startActivity(taskIntent);
            }
        });

    }

private void configurAmplify(){

        try{
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG,"Initialised Amplify");
        } catch (AmplifyException e) {
            Log.i(TAG, "Could not initialize Amplify");
        }
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
