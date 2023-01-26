package ds.app.cs24rider.Views.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ds.app.cs24rider.Businesses.Maps.LocationUpdateBusiness;
import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.Models.Maps.EndLocation;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.CallingDialog;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.Utils;
import ds.app.cs24rider.Views.Home.Menu.Home;
import ds.app.cs24rider.Views.Home.Menu.Notification;
import ds.app.cs24rider.Views.Home.Menu.Profile;
import ds.app.cs24rider.Views.Map.MapsActivity;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class MainActivity extends AppCompatActivity implements LocationListener, MyLocationUpdatePresenter.View {

    private BottomNavigationView mMenu;
    private LocationRequest locationRequest;
    private LocationManager locationManager;
    private static final int REQUEST_CHECK_SETTINGS = 10001;
    private boolean isResponse = true;
    private MyLocationUpdatePresenter mPresenter;
    private double myLat = 0, myLong = 0;
    private Long timeStamp = 0L;
    private boolean isActive = false;
    private CallingDialog dialog;
    private SweetAlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = Utils.getProgress(this);

        mPresenter = new LocationUpdateBusiness(this, this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
        init();
        getMyLocation();
        dialog = new CallingDialog(MainActivity.this, mPresenter);
    }

    private void init() {
        mMenu = findViewById(R.id.bottom_menu);
        mMenu.setItemActiveIndicatorEnabled(true);
        mMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fm = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fm = getSupportFragmentManager().beginTransaction().replace(R.id.container, Home.newInstance(mPresenter)).addToBackStack(null);
                        break;
                    case R.id.notification:
                        fm = getSupportFragmentManager().beginTransaction().replace(R.id.container, Notification.newInstance()).addToBackStack(null);
                        break;
                    case R.id.profile:
                        fm = getSupportFragmentManager().beginTransaction().replace(R.id.container, Profile.newInstance()).addToBackStack(null);
                        break;
                    default:
                        fm = getSupportFragmentManager().beginTransaction().replace(R.id.container, Home.newInstance(mPresenter)).addToBackStack(null);
                }
                if (fm != null) {
                    fm.commit();
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.container, Home.newInstance(mPresenter)).addToBackStack(null).commit();
        mMenu.setSelectedItemId(R.id.home);

        timeStamp = System.currentTimeMillis() - 10000;

        IntentFilter onUpChat = new IntentFilter(); onUpChat.addAction(Constance.ON_TASK_CHANGE);
        registerReceiver(taskChangeReceiver, onUpChat);
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS is tured on", Toast.LENGTH_SHORT).show();

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS required to be tured on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Long currentTimeStamp = System.currentTimeMillis();
        Log.e("timestamp", "start: "+System.currentTimeMillis()+" End: "+(System.currentTimeMillis() + (10 * (60 * 1000))));
        Log.e("sdfhsbdgsd", "isResponse: "+isResponse+" Lat: "+(myLat != location.getLatitude()) +" Long: "+ (myLong != location.getLongitude())+" time: " +((timeStamp+5000) < currentTimeStamp));
        if(isResponse && (myLat != location.getLatitude() && myLong != location.getLongitude()) && (timeStamp+5000) < currentTimeStamp){
            timeStamp = currentTimeStamp;
            myLat = location.getLatitude();
            myLong = location.getLongitude();
            isResponse = false;
            mPresenter.giveUpdate(new EndLocation(String.valueOf(myLat), String.valueOf(myLong), String.valueOf(timeStamp)));
            Log.e("sdfhsbdgsd", "-------------------------------------------");
        }
    }

    @Override
    public void onResponse(boolean status, String message) {
        isResponse = true;
    }

    @Override
    public void onTaskListResponse(boolean status, String message) {
        Log.e("dfhgdfgdf", message);
        if(status){
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            i.putExtra("tasks", message);
            startActivity(i);
        }else{
            Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver taskChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && intent.hasExtra("status") && isActive){
                dialog.show(false);
            }
        }
    };

    @Override
    public void showProgress() {
        loading.show();
    }

    @Override
    public void hideProgress() {
        loading.dismiss();
    }

    @Override
    public void onNoInternet() {
        Snackbar.make(getWindow().getDecorView(), Constance.NO_INTERNET_CONNECTION, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        isActive = true;
        super.onResume();
    }
}