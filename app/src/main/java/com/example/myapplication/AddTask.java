package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "Tagtest";
//
//    private String[] mState = new String[]{"New",  "In progress","Assigned", "complete"};
//    private String[] mTeams = new String[]{"Team1", "Team2", "Team3"};
//    private Handler handler ;
//
//    public static final String TASK_ID = "taskId";
//    public static final String TEAMNAME = "teamName";
//    public static final String DATA = "data";
//

    private String URL;
    public static final int REQUEST_CODE = 123;
    private String[] mStates = new String[]{"New", "In-Progress", "Complete", "Assigned"};

    private String[] mTeams = new String[]{"TeamOne", "TeamTwo", "TeamThree"};

    public static final String TASK_ID = "taskId";
    public static final String TEAMNAME = "teamName";
    public static final String DATA = "data";
    private String imageName = "";
    private Handler handler;

    private EditText taskTitle;


    private int PERMISSION_ID = 44;

    private double latitude;
    private double longitude;
    private FusedLocationProviderClient mFusedLocationClient;

    private Button uploadButton;

    private Button toggle;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        taskTitle = findViewById(R.id.myTask);


        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("OpenedMyApp")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .build();

        Amplify.Analytics.recordEvent(event);

        sharedImage();

        uploadButton = findViewById(R.id.upload_img);

        uploadButton.setOnClickListener(view -> pictureUpload());


        Spinner stateSelector = findViewById(R.id.stateSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.state,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);


        stateSelector.setAdapter(spinnerAdapter);
        stateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        AddTask.this,
                        "The item selected is => " + mStates[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        List<Team> teamsList = new ArrayList<>();
        Spinner teamSelector = findViewById(R.id.teamSpinner);


        Amplify.API.query(
                ModelQuery.list(Team.class),
                detect -> {
                    for (Team team : detect.getData()) {
                        teamsList.add(team);
                    }
                    handler = new Handler(Looper.getMainLooper(), msg -> {
//                    runOnUiThread(() -> {
                        // create adapter

                        ArrayAdapter<CharSequence> spinnerAdapterTeam = new ArrayAdapter<CharSequence>(
                                this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                mTeams
                        );

                        // set adapter
                        teamSelector.setAdapter(spinnerAdapterTeam);
                        return true;
                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("TeamTaskID", detect.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                });


        Button button = findViewById(R.id.addTaskButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText AddBody = findViewById(R.id.doSomthing);
                Spinner stateSelector = findViewById(R.id.stateSpinner);
                Spinner teamSelector = findViewById(R.id.teamSpinner);

                String name = taskTitle.getText().toString();
                String body = AddBody.getText().toString();
                String state = stateSelector.getSelectedItem().toString();
                String teamName = teamSelector.getSelectedItem().toString();


                Amplify.API.query(
                        ModelQuery.list(Team.class),
                        item -> {
                            for (Team team : item.getData()) {
                                if (teamName.equals(team.getName())) {
//                            saveTasks(team);
                                    Task task = Task.builder()
                                            .title(name)
                                            .description(body)
                                            .status(state)
                                            .teamTasksId(team.getId())
//                                            .image(imageName) //URL
//                                            .latitude(latitude)
//                                            .longitude(longitude)
                                            .build();

//                                    Log.i(TAG, "api: " + task.getImage());
                                    Log.i(TAG, "api: " + task.getTitle());

                                    Amplify.API.mutate(
                                            ModelMutation.create(task),
                                            success -> {
                                            },
                                            error -> {
                                            });
                                }
                            }
                        },
                        error -> {
                        }
                );


                Toast.makeText(getApplicationContext(), "Submitted!" + getTitle(), Toast.LENGTH_SHORT).show();


                Intent BackToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(BackToMain);

            }
        });


        ///////////////////*****************      LOCATION                    ******************** //////////////


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
    }
//                 Log.i(TAG, "==== Task ====");



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
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

//                                LatLng coordinate = new LatLng(latitude, longitude);
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show(); //ACTION_LOCATION_SOURCE_SETTINGS

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }

    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        String name = taskTitle.getText().toString();
        switch (requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();

                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    File file = new File(getApplicationContext().getFilesDir(), "image.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();


                    Amplify.Storage.uploadFile(
//                            name + ".jpg",
                            currentUri.getLastPathSegment(),
                            file,
                            result -> {
                                Log.i(TAG, "Successfully uploaded: " + result.getKey());
                                imageName = result.getKey();
                            },
                            storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {

        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }


    public void sharedImage()
    {

        Intent intent = getIntent();
        String name = taskTitle.getText().toString();
        Uri imgUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);



        if(imgUri != null) {
            try {
                Bitmap bitmap = getBitmapFromUri(imgUri);

                File file = new File(getApplicationContext().getFilesDir(), "image.jpg");
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();


                Amplify.Storage.uploadFile(
//                            name + ".jpg",
                        imgUri.getLastPathSegment(),
                        file,
                        result -> {
                            Log.i(TAG, "Successfully uploaded: " + result.getKey());
                            imageName = result.getKey();
                        },
                        storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void pictureUpload() {

        // Launches photo picker in single-select mode.
        // This means that the user can select one photo or video.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE);


    }
    private void configurAmplify() {
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialised Amplify");
        } catch (AmplifyException e) {
            Log.i(TAG, "Could not initialize Amplify");
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////37//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void addImagetoS3(){

    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////42//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////



}