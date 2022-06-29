package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.geo.location.AWSLocationGeoPlugin;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class AmplifyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//configurstion
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSPredictionsPlugin());
            Amplify.addPlugin(new AWSLocationGeoPlugin());
            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(this));
            Amplify.configure(getApplicationContext());

            Log.i("AmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("AmplifyApp", "Could not initialize Amplify", error);
        }


        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("opendMyApp")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .build();

        Amplify.Analytics.recordEvent(event);

    }

}