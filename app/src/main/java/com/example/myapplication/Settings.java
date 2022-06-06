package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    public static final String USERNAME ="username" ;
    private static final String TAG = Settings.class.getSimpleName();
    private EditText editUsername;

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
    }

    private void saveUsername() {
        String username = editUsername.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        preferenceEditor.putString(USERNAME, username);
        preferenceEditor.apply();

        Toast.makeText(this, "Username Saved", Toast.LENGTH_SHORT).show();
    }

}