package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;


public class TaskDetail extends AppCompatActivity {
    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView title = findViewById(R.id.title);
        String titleName = getIntent().getStringExtra("Title");
        title.setText(titleName);


            changeTaskName();
            changeTaskBody();
            changeTaskState();
        }

        private void changeTaskName() {
            TextView mTitle = findViewById(R.id.title);
            String title = getIntent().getStringExtra("title");
            mTitle.setText(title);
        }
        private void changeTaskBody() {
            TextView mBody = findViewById(R.id.lorem);
            String body = getIntent().getStringExtra("lorem");
            mBody.setText(body);
        }
        private void changeTaskState() {
            TextView mState = findViewById(R.id.state);
            String state = getIntent().getStringExtra("state");
            mState.setText(state);
        }

    @SuppressLint("SetText")
    private void title(){
        TextView mUsernameHeader = findViewById(R.id.title);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsernameHeader.setText(sharedPreferences.getString(Settings.USERNAME,"My")+ " Tasks");
        Log.i(TAG, "Main ->setUsername : "+mUsernameHeader);
    }

}