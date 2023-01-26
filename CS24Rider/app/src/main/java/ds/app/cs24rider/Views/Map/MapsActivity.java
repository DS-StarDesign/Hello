package ds.app.cs24rider.Views.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import ds.app.cs24rider.Businesses.Maps.LocationUpdateBusiness;
import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.CallBack.RouteSetupCallback;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.CallingDialog;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Views.Home.MainActivity;
import ds.app.cs24rider.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, RouteSetupCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    protected LocationManager locationManager;
    private ImageView taskList, locator;
    private TaskListSheet bottomSheet;

    MarkerOptions myMarkerOptions = null;
    Marker myPos = null;
    private LatLng myLatLong = null;

    private static final int REQUEST_CHECK_SETTINGS = 10001;
    private Double latitude = 23.8103, longitude = 90.4125;

    public String tasks;
    private CallingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasks = getIntent() != null ? getIntent().hasExtra("tasks") ? getIntent().getStringExtra("tasks") : "" : "";

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        permission_request(false);
        init();
        dialog = new CallingDialog(this);
    }

    private void permission_request(boolean type){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getMyLocation(type);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void init(){
        taskList = findViewById(R.id.my_task_list);
        locator = findViewById(R.id.my_locator);
        taskList.setOnClickListener(view->{
            bottomSheet.show(getSupportFragmentManager(), "task_list");
        });
        locator.setOnClickListener(view->{
            permission_request(true);
        });
        taskList.setVisibility(tasks.isEmpty() ? View.GONE : View.VISIBLE);
        if(!tasks.isEmpty()){
            bottomSheet = new TaskListSheet(this, tasks);
            bottomSheet.show(getSupportFragmentManager(), "task_list");
        }
        IntentFilter onUpChat = new IntentFilter(); onUpChat.addAction(Constance.ON_TASK_CHANGE);
        registerReceiver(taskChangeReceiver, onUpChat);
    }

    private void open_location(){
        LocationRequest locationRequest = LocationRequest.create();
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
                    Toast.makeText(MapsActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
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
    }

    private void getMyLocation(boolean type) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        open_location();
        if(myLatLong != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLong, 15));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myMarkerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title("I am Here").icon(bitmapDescriptorFromVector(this, R.drawable.my_pos));
        myPos = mMap.addMarker(myMarkerOptions);
        myPos.setVisible(false);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        myLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        myPos.setPosition(myLatLong);
        myPos.setVisible(true);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
    public void onLoad(MapDirection response) {
        PolylineOptions lineOptions = new PolylineOptions();
        ArrayList<LatLng> allPoints = response.getPoints();
        lineOptions.addAll(allPoints);
        lineOptions.width(12);
        lineOptions.color(Color.RED);
        lineOptions.geodesic(true);
        mMap.addPolyline(lineOptions);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(response.getLatitudePickup(), response.getLongitudePickup()))
                .include(new LatLng(response.getLatitudeDestination(), response.getLongitudeDestination())).build();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        mMap.addMarker(new MarkerOptions().position(new LatLng(response.getLatitudePickup(), response.getLongitudePickup())).title("Pickup"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(response.getLatitudeDestination(), response.getLongitudeDestination())).title("Delivery Point."));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, 850, 30));
    }

    private BroadcastReceiver taskChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && intent.hasExtra("status")){
                dialog.show(false);
            }
        }
    };
}