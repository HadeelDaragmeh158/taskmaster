package com.example.myapplication;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.InitializationStatus;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "Tagtest";

    private String[] mState = new String[]{"New",  "In progress","Assigned", "complete"};
    private String[] mTeams = new String[]{"Team1", "Team2", "Team3"};

    private Handler handler ;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    @SuppressLint("MissingPermission")


    public static final String TASK_ID = "taskId";
    public static final String TEAMNAME = "teamName";
    public static final String DATA = "data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Spinner taskStateSelector = findViewById(R.id.stateSpinner);
        //-------------------33
        Spinner teamsSelector  = findViewById(R.id.teams);
        String selectedTeam = teamsSelector.getSelectedItem().toString();
        //-----------------------------

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
/////////////////////////////////////////////
        Button addTask_addTaskPageButton = findViewById(R.id.addTask_addTaskPage);
        TextView submittdText= findViewById(R.id.submittdText);



        configurAmplify();

        addTask_addTaskPageButton.setOnClickListener(v->{

            EditText   myTask = findViewById(R.id.myTask);
            String  myTaskStr = myTask.getText().toString();

            EditText   doSomthing = findViewById(R.id.myTask);
            String  doSomthingStr = myTask.getText().toString();

            String state = taskStateSelector.getSelectedItem().toString();


            Task item = Task.builder()
                    .title(myTaskStr)
                    .description(doSomthingStr )
                    .status(state)
                    .build();

            Amplify.API.mutate(
                ModelMutation.create(item),
                success -> Log.i(TAG, "Saved item: " + success.getData().getTitle()),
                error -> Log.e(TAG, "Could not save item to API", error)
        );
//___________________________________________________________________
//_________________________42________________________________________
//___________________________________________________________________


/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
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
//___________________________________________________________________
//_________________________33________________________________________
//___________________________________________________________________


        List<Team> teamArrayList = new ArrayList<>();


        Amplify.API.query(
                ModelQuery.list(Team.class),

                success -> {
                    for (Team team : success.getData()) {
                        teamArrayList.add(team);
                    }
                    handler = new Handler(Looper.getMainLooper(), msg -> {
                        ArrayAdapter<CharSequence> spinnerAdapterTeam = new ArrayAdapter<CharSequence>(
                                this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                mTeams
                        );
                        teamsSelector.setAdapter(spinnerAdapterTeam);
                        return true;

                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("TeamTaskID", success.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> Log.e(
                        TAG,
                        "ERROR in API Query",
                        error
                )
        );
        addTask_addTaskPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText addTitle = findViewById(R.id.myTask);
                EditText AddBody = findViewById(R.id.doSomthing);
                Spinner stateSelector = findViewById(R.id.stateSpinner);
                Spinner teamSelector = findViewById(R.id.teams);

                String taskName = addTitle.getText().toString();
                String doSomth = AddBody.getText().toString();
                String state = stateSelector.getSelectedItem().toString();
                String teamName = teamSelector.getSelectedItem().toString();

                Amplify.API.query(
                        ModelQuery.list(Team.class),
                        response -> {
                            for (Team team : response.getData()) {
                                if (teamName.equals(team.getName())) {
                                    Task task = Task.builder()
                                            .title(taskName)
                                            .description(doSomth)
                                            .status(state)
                                            .teamTasksId(team.getId())
                                            .build();

                                    Amplify.API.mutate(
                                            ModelMutation.create(task),
                                            success -> Log.i(
                                                    TAG,
                                                    "Saved Successfully " + success.getData().getTitle()),
                                            error -> Log.e(
                                                    TAG,
                                                    "fatal",
                                                    error
                                            )
                                    );
                                }
                            }
                        },
                        error -> Log.e(TAG, "Query Error", error)
                );


                Toast.makeText(getApplicationContext(), "Submitted!" + getTitle(), Toast.LENGTH_SHORT).show();


                Intent BackToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(BackToMain);

            }
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


    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////42//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
}