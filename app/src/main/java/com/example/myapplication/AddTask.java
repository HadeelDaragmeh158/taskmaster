package com.example.myapplication;

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

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

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




        addTask_addTaskPageButton.setOnClickListener(v->{

            EditText   myTask = findViewById(R.id.myTask);
            String  myTaskStr = myTask.getText().toString();

            EditText   doSomthing = findViewById(R.id.myTask);
            String  doSomthingStr = myTask.getText().toString();

            String state = taskStateSelector.getSelectedItem().toString();

            com.amplifyframework.datastore.generated.model.Task addnewTask = com.amplifyframework.datastore.generated.model.Task.builder()
                    .title(myTaskStr).body(doSomthingStr).status(state).build();

            Amplify.API.query(ModelMutation.create(addnewTask),
                    success-> Log.i(TAG, "create new task"),
                    error -> Log.e(TAG,"error",error)
            );
            Amplify.DataStore.save(addnewTask,
                    success -> {
                        Log.i(TAG, "onCreate: saving in datastore succeed ");
                    } ,
                    fail->{
                        Log.i(TAG, "onCreate: saving in datastore failed ");
                    }

            );

            // saves to the backend
            Amplify.API.mutate(ModelMutation.create(newTask)
                    , success -> {
                        Log.i(TAG, "onCreate: saving in API mutate");

                    }
                    , fail -> {
                        Log.i(TAG, "onCreate: NOT saving in API mutate");

                    }
            );

            Amplify.DataStore.observe(com.amplifyframework.datastore.generated.model.Task.class,
                    started -> {
                        Log.i(TAG, "onCreate: observation began");
                    },
                    change -> {
                        Log.i(TAG, change.item().toString());

                        Bundle bundle = new Bundle();
                        bundle.putString("data",change.item().toString());
                        Message message = new Message();
                        message.setData(bundle);

                        handler.sendMessage(message);
                    },
                    failure -> {
                        Log.e(TAG, "onCreate: Observation failed");
                    },
                    () -> {
                        Log.i(TAG, "onCreate: Observation complete");
                    }
            );


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
}