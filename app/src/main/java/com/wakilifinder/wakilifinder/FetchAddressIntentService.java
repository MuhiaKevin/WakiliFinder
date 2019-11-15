package com.wakilifinder.wakilifinder;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static androidx.constraintlayout.widget.Constraints.TAG;

//https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
// https://stackoverflow.com/questions/54783076/you-must-use-an-api-key-to-authenticate-each-request-to-google-maps-platform-api

import androidx.annotation.Nullable;

public class FetchAddressIntentService extends IntentService {

    private static final int SUCCESS_RESULT = 0;
    private static final int FAILURE_RESULT = 1;
    private static final int SUCCESS_RESULT_USING_GOOGLE_MAPS = 2;
    private static final String PACKAGE_NAME = "com.wakilifinder.wakilifinder";
    private static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    private static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    private static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    protected ResultReceiver mReceiver;
    private boolean geoCoderSuccessful;
    protected String response;
    protected boolean addressFound;
    private boolean testAPI = false; //set to true only when you want to test the google maps API


    public FetchAddressIntentService() {
        super("name");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //get the location passed to this service through extras

        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(RECEIVER);

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

        }
        catch (IOException e) {
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, e);
            e.printStackTrace();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            //catch invalid latitude and longitude
            errorMessage = "Invalid latitude and longitude used";
            Log.e(TAG, errorMessage + ". " + "Latitude = " + location.getLatitude() + ", Longitude = " + location.getLongitude());
        }
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Log.e(TAG, errorMessage);
            }
            deliverResultsToReceiver(FAILURE_RESULT, errorMessage);
        }else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            //Fetch the address lines using getAddressLine
            //join them and send them to the MainActivity

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, "Address Found");
            addressFound = true;
            deliverResultsToReceiver(SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }

    }

    private void deliverResultsToReceiver(int failureResult, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_DATA_KEY, message);
        mReceiver.send(failureResult, bundle);
    }
}


