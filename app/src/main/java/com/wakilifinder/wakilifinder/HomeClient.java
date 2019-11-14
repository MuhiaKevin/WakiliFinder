package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Fragments.HistoryFragment;
import com.wakilifinder.wakilifinder.Fragments.LawyersFragment;
import com.wakilifinder.wakilifinder.Model.UserLawyer;
import com.wakilifinder.wakilifinder.Model.Userclient;

import java.util.ArrayList;

public class HomeClient extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // REVERSE GEOCODING
    private static final int SUCCESS_RESULT = 0;
    private static final int SUCCESS_RESULT_USING_GOOGLE_MAPS = 2;
    private static final String PACKAGE_NAME = "com.wakilifinder.wakilifinder";
    private static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    private static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    private static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    private static final String TAG = "connection failed";
    private GoogleApiClient googleApiClient;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private LocationRequest locationRequest;
    private Double myLatitude;
    private Double myLongitude;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastKnownLocation;
    private AddressResultReceiver mResultReceiver;
    private String mAddressOutput;


    private RecyclerView recyclerView;
    private ArrayList<UserLawyer> mUsers;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private EditText search_users;
    private CircleImageView profile_image;
    private TextView username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);

        // make activity hide the keyboard everytime the activty starts
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // set up toolabr display
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        fetchAddressButtonHandler();

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Clients").child(firebaseUser.getUid());



        // set user information on the top bar of  activity
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Userclient user = dataSnapshot.getValue(Userclient.class);
                username.setText(user.getUsername());
                profile_image.setImageResource(R.mipmap.ic_launcher);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LawyersFragment(),"Lawyers");
        viewPagerAdapter.addFragment(new HistoryFragment(),"Conversations");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void fetchAddressButtonHandler() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    mLastKnownLocation = location;

                    // in some rare cases the location returns null
                    if (mLastKnownLocation == null) {
                        return;
                    }

                    if (!Geocoder.isPresent()) {
                        Toast.makeText(HomeClient.this, "No Geocoder present", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Start service and update UI
                    startIntentService();
                }
            });
        }
    }

    private void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        mResultReceiver = new AddressResultReceiver (new Handler());
        intent.putExtra (RECEIVER, mResultReceiver);
        intent.putExtra(LOCATION_DATA_EXTRA, mLastKnownLocation);
        startService (intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            //ask for permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());

    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        // show
//        Toast.makeText(getApplicationContext(), "Latitude  : " + String.valueOf(myLatitude) +  "Longitude  : " + String.valueOf(myLongitude)  , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission is granted - do nothing
            } else {
                Toast.makeText(getApplicationContext(), "This app requires permissions to be granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    // set menu layout on the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // if log out button on the appbar then the app logs the user out
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // log out button
            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeClient.this, ClientLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }

        return false;
    }




    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            //Display the address string
            //or error message sent from Intent service
            mAddressOutput = resultData.getString(RESULT_DATA_KEY);
            displayAddressOutput();

            if (resultCode == SUCCESS_RESULT) {
                Toast.makeText(getApplicationContext(), "Address Found", Toast.LENGTH_SHORT).show();
            } else if (resultCode == SUCCESS_RESULT_USING_GOOGLE_MAPS) {
                Toast.makeText(getApplicationContext(), "Address Found Using Google Maps API", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void displayAddressOutput() {
        // SEND TO LAWYER FRAGMENT
        Toast.makeText(getApplicationContext(), "Address Found : " + mAddressOutput, Toast.LENGTH_SHORT).show();
    }
}
