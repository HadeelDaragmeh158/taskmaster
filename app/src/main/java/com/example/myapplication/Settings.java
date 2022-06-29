package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private static final String TAG = Settings.class.getSimpleName();

    public static final String USERNAME ="username";
    public static final String TEAMNAME = "teamName";
    private EditText editUsername;
//////////////////////////33
    private Spinner  sellectTeam = findViewById(R.id.sellectTeam);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        
            editUsername = findViewById(R.id.editTextTextPersonName);
            Button saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(view -> {
                Log.i(TAG, "Save button Clicked");

                if (editUsername.getText().toString().length() >= 3) {
                    saveUsername();
                } else {
                    Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                }


                View view2 = this.getCurrentFocus();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }

            });
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }



            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: the text is : " + charSequence);
            }

           

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: the text is : " + editable.toString());
                if (!saveButton.isEnabled()) {
                    saveButton.setEnabled(true);
                }

                if (editable.toString().length() == 0){
                    saveButton.setEnabled(false);
                }
            }
        });


        //////////////////33
        List<Team> teams = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),

                success->{
                        for(Team team : success.getData()){
                            teams.add(team);
                        }
                    runOnUiThread(() -> {
                        String[] teamName = new String[teams.size()];

                        for (int i = 0; i < teams.size(); i++) {
                            teamName[i] = teams.get(i).getName();
                        }
                        //Adapter
                        ArrayAdapter<CharSequence> adapterTeams = new ArrayAdapter<CharSequence>(
                                this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                teamName
                        );
                        adapterTeams.notifyDataSetChanged();
                        sellectTeam.setAdapter(adapterTeams);
                    });
                            },
                    error->
                            Log.i(
                            TAG,
                            "Error in Api query",
                            error
                    )
        );
    }

    private void saveUsername() {
        String username = editUsername.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        preferenceEditor.putString(USERNAME, username);
        preferenceEditor.apply();

        Toast.makeText(this, "Username Saved", Toast.LENGTH_SHORT).show();


        ///////////33
        String nameOfTheTeam = sellectTeam.getSelectedItem().toString();
        preferenceEditor.putString(TEAMNAME, nameOfTheTeam);
        preferenceEditor.apply();

    }

}