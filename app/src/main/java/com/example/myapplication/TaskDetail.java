package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.LanguageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TaskDetail extends AppCompatActivity {

    private static final String TAG = "test";


    private final MediaPlayer mp = new MediaPlayer();
    TextView mState = findViewById(R.id.state);
    String state = getIntent().getStringExtra("state");
    String body = getIntent().getStringExtra("lorem");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView title = findViewById(R.id.title);
        String titleName = getIntent().getStringExtra("Title");
        title.setText(titleName);

        Button translateLorem = findViewById(R.id.translater);
        translateLorem.setOnClickListener(view->{
            translate();
        });

        Button readText = findViewById(R.id.speaker);
        readText.setOnClickListener(view->{
            ConvertToSpeak();
        });
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

            mBody.setText(body);
        }
        private void changeTaskState() {

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

    private void translate ()
    {
        Amplify.Predictions.translateText(
                body,
                LanguageType.ENGLISH,
                LanguageType.ARABIC,
                result -> {
                    Log.i("MyAmplifyApp", result.getTranslatedText());
                    mState.setText(result.getTranslatedText());
        },
                error -> Log.e("MyAmplifyApp", "Translation failed", error)
        );

    }

    public void ConvertToSpeak(){

        Amplify.Predictions.convertTextToSpeech(
                body,
                result -> playAudio(result.getAudioData()),
                error -> Log.e("MyAmplifyApp", "Conversion failed", error)
        );

    }
    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }
}