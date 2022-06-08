package com.example.myapplication;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "Tagtest";

    private Handler handler ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final String[] mState = new String[]{"New",  "In progress","Assigned", "complete"};

        Spinner taskStateSelector = findViewById(R.id.stateSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this ,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                mState
        );
        taskStateSelector.setAdapter(spinnerAdapter);
        taskStateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button addTask_addTaskPageButton = findViewById(R.id.addTask_addTaskPage);
        TextView submittdText= findViewById(R.id.submittdText);



        configurAmplify();

        addTask_addTaskPageButton.setOnClickListener(v->{

            EditText   myTask = findViewById(R.id.myTask);
            String  myTaskStr = myTask.getText().toString();

            EditText   doSomthing = findViewById(R.id.myTask);
            String  doSomthingStr = myTask.getText().toString();

            String state = taskStateSelector.getSelectedItem().toString();


            Task item = com.amplifyframework.datastore.generated.model.Task.builder()
                    .title(myTaskStr)
                    .description(doSomthingStr )
                    .status(state)
                    .build();

            Amplify.API.mutate(
                ModelMutation.create(item),
                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle()),
                error -> Log.e(TAG, "Could not save item to API", error)
        );

//            Amplify.API.query(
//                    ModelQuery.list(Task.class, Task.TITLE.contains("Task")),
//                    response -> {
//                        for(Task task : response.getData())
//                            Log.i(TAG," ------->>>>>>> " + task.getTitle());
//                    },
//                    error ->{
//                        Log.e(TAG,"ERROR", error);
//                    }
//
//            );
//            Amplify.DataStore.query(Task.class,
//                tasks -> {
//                    while (tasks.hasNext()) {
//                        Task task = tasks.next();
//
//                        Log.i(TAG, "==== Task ====");
//                        Log.i(TAG, "Name: " + task.getTitle());
//                    }
//                },
//                failure -> Log.e(TAG, "Could not query DataStore", failure)
//        );

//            Task task = new Task(myTaskStr , doSomthingStr , "");
//            Long newTaskID =  AppDatabase.getInstance(getApplicationContext()).userDao().addTask(task);
//            submittdText.setText("Submitted!!");
//
//            Toast.makeText(this, "Task Submitted : "+task.getBody(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void configurAmplify(){
        try{
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG,"Initialised Amplify");
        } catch (AmplifyException e) {
            Log.i(TAG, "Could not initialize Amplify");
        }
    }
}