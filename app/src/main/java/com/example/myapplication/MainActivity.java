package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.myapplication.data.TaskData;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getCanonicalName();
    private List<String> titles = new ArrayList<>();
    List<Task> allTasks = new ArrayList<>();


    private Handler handler;
    private String TITLE = "title";
    private String TASKID = "taskId";
    private String TEAMID = "teamid";

    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;


    private View.OnClickListener addClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mTaskName.setText("My Tasks");
            mTaskName.setAllCaps(true);

            Intent addTaskIntent = new Intent(getApplicationContext(), AddTask.class);
            startActivity(addTaskIntent);
        }
    };
    private View.OnClickListener mClickTask1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mTask.setText("Task1");
            mTask.setAllCaps(true);

            Intent startTaskDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
            startTaskDetailsIntent.putExtra("Title", "Task1");

            startActivity(startTaskDetailsIntent);

        }
    };
    private View.OnClickListener mClickInterstitialAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            interstitial_Ad();

            Intent startAdActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startAdActivity);

        }
    };

    private View.OnClickListener mClickTask = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mTask.setText("Task1");
            mTask.setAllCaps(true);

            Intent startTaskDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
            startTaskDetailsIntent.putExtra("Title", "Task1");

            startActivity(startTaskDetailsIntent);

        }
    };

    private View.OnClickListener mClickSettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mSettings.setText("settings");
            mSettings.setAllCaps(true);

            Intent startAllTasksIntent = new Intent(getApplicationContext(), Settings.class);
            startActivity(startAllTasksIntent);

        }
    };
    private TextView mTaskName;
    private TextView mSettings;
    private TextView mAllTasks;
    private TextView mUserName;
    private TextView mTeamName;
    private TextView mTask;

    List<Task> tasksListAdap = new ArrayList();//TODO


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
//


        mUserName = findViewById(R.id.username);
        mTeamName = findViewById(R.id.teamName);

        //addtask Button

        Button addTask = findViewById(R.id.addTaskButton);
        mTaskName = findViewById(R.id.titleMyTask);

        addTask.setOnClickListener(addClickListner);
        //-------------------

        //Sitting btn

        Button stiingBtn = findViewById(R.id.sittingButton);
        mSettings = findViewById(R.id.sittingButton);

        stiingBtn.setOnClickListener(mClickSettings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String teamId = sharedPreferences.getString("teamId", "");

        List<Task> tasksList = new ArrayList();
        //------------------------

        handler = new Handler(getMainLooper(), msg -> {
            AuthUser logedInUser = Amplify.Auth.getCurrentUser();
            String username = logedInUser.getUsername();
            mUserName.setText(username);
            authSessionUserName(username);
            return true;
        });

        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("OpenedMyApp")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .build();

        Amplify.Analytics.recordEvent(event);

        //ADView
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button interstitialAdBtn = findViewById(R.id.Interstitial_Ad);
        interstitial_Ad();
        interstitialAdBtn.setOnClickListener(view -> {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d(TAG, "Error Insertion");
            }
        });


        Button rewardAdBtn = findViewById(R.id.Rewarded_Ad);
        rewardedAd();

        rewardAdBtn.setOnClickListener(view -> {
            if (mRewardedAd != null) {
                Activity activityContext = MainActivity.this;
                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
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
        setUserAndTeamName();
        getTaskByTeam();
        getTasks();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menue, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sittingButton:
                navigateToSettings();
                return true;
            case R.id.logout:
                logout();
                break;
            case R.id.reset:
                // TODO: 5/25/22 Implement reset password
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

    private void setUserAndTeamName() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserName.setText(sharedPreferences.getString(Settings.USERNAME, "No Username Set"));

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mTeamName.setText(sharedPreferences.getString(Settings.TEAMNAME, "No Team Set"));

    }



    private void setTeams()
    {
        Team teamOne = Team.builder().name("Team 1").build();
        Team teamTwo = Team.builder().name("Team 2").build();
        Team teamThree = Team.builder().name("Team 3").build();

        Amplify.API.mutate(
                ModelMutation.create(teamOne),
                item -> {},
                error -> { }
        );


        Amplify.API.mutate(
                ModelMutation.create(teamTwo),
                item -> { },
                error -> { }
        );
        Amplify.API.mutate(
                ModelMutation.create(teamThree),
                item -> { },
                error -> { }
        );

    }


    private void getTaskByTeam()
    {
        handler = new Handler(Looper.getMainLooper(), msg -> {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);

            TaskRecyclerViewAdapter taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(
                    tasksListAdap, position -> {
                Toast.makeText(
                        MainActivity.this,
                        "The Task clicked => " + tasksListAdap.get(position).getTitle(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
                intent.putExtra("id", tasksListAdap.get(position).getId());
                intent.putExtra("Title", tasksListAdap.get(position).getTitle());
                intent.putExtra("Description", tasksListAdap.get(position).getDescription());
                intent.putExtra("State", tasksListAdap.get(position).getStatus().toString());
//                intent.putExtra("image", tasksListAdap.get(position).getImage().toString());
                startActivity(intent);

            });

            recyclerView.setAdapter(taskRecyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            return true;
        });
    }


    private void getTasks() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String teamName = sharedPreferences.getString(Settings.TEAMNAME, "Team");

        String[] teamId = new String[1];
        Amplify.API.query(ModelQuery.list(Team.class, Team.NAME.eq(teamName)),
                detect -> {
                    for (Team team : detect.getData()) {
                        teamId[0] = team.getId();
                    }
                    Amplify.API.query(ModelQuery.list(Task.class),
                            item -> {
//                                        List <Task> helper = item.getData();
                                if (item.hasData()) {
                                    for (Task task : item.getData()) {
                                        if (task.getTeamTasksId().equals(teamId[0])) {
                                            tasksListAdap.add(task);
                                        }
                                    }
                                }

                                Bundle bundle = new Bundle();
                                bundle.putString("TeamTaskID", detect.toString());

                                Message message = new Message();
                                message.setData(bundle);

                                handler.sendMessage(message);
                            },
                            error -> {
                            }
                    );
                },
                error -> {}
        );

    }


    private void deleteTask(List<TaskData> taskList)
    {
        for (int i = 0; i < taskList.size(); i++) {
//            AppDatabase.getInstance(getApplicationContext()).UserDao().delete(taskList.get(i));
        }
    }


    private void authSessionUserName(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i(TAG, result.toString());
                    Toast.makeText(this, "${Amplify.Auth.currentUser.userId} is logged in", Toast.LENGTH_LONG).show();
                },
                error -> Log.e(TAG, error.toString())
        );
    }


    private void authSession(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i(TAG, "Auth Session => " + method + result.toString());
                    if(result.isSignedIn())
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("username", "username");

                        Message message = new Message();
                        message.setData(bundle);

//                        handler.sendMessage(message);
                    }
                },

                error -> Log.e(TAG, error.toString())
        );
    }


    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    authSession("logout");
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }

    private void interstitial_Ad()
    {


        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "Interstitial => onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d(TAG, "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d(TAG, "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d(TAG, "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    private void rewardedAd()
    {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                    }
                });

    }


}
