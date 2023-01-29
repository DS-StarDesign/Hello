package ds.app.cs24rider.Views.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ds.app.cs24rider.Businesses.Maps.MapDirectionBusiness;
import ds.app.cs24rider.CallBack.Presenters.MapDirectionPresenter;
import ds.app.cs24rider.CallBack.RouteSetupCallback;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.Models.Tasks.TasksModel;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.CallingDialog;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.OTPConfirmationDialog;
import ds.app.cs24rider.Utils.PrefManager;
import ds.app.cs24rider.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, RouteSetupCallback, MapDirectionPresenter.View{

    private static final int REQUEST_CODE = 250;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    protected LocationManager locationManager;
    private ImageView taskList, locator;
    private TaskListSheet bottomSheet;
    private CardView notification, endRideView;
    private ImageView callBtn, msgBtn;
    private ImageView notificationClose;
    private TextView notificationTitle, notificationSubTitle, rcvName, onOff;
    private Button taskComplete;
    private LinearLayout onlineOffline;
    private List<TasksModel> items = new ArrayList<>();
    private MapDirectionPresenter mPresenter;
    private PrefManager mPref;

    MarkerOptions myMarkerOptions = null;
    Marker myPos = null;
    private LatLng myLatLong = null;

    private static final int REQUEST_CHECK_SETTINGS = 10001;
    private Double latitude = 23.8103, longitude = 90.4125;
    private String MAP_KEY;

    public String tasks;
    private CallingDialog dialog;
    OTPConfirmationDialog otpDialog;

    private List<MapDirection> mapDirections = new ArrayList<>();

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
        this.MAP_KEY = getResources().getString(R.string.MAP_API_KEY);
        this.mPresenter = new MapDirectionBusiness(this, this);
        init();
        dialog = new CallingDialog(this);
        otpDialog = new OTPConfirmationDialog(this);
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
        locator = findViewById(R.id.my_locator);
        notification = findViewById(R.id.map_notification);
        endRideView = findViewById(R.id.end_ride_view);
        notificationClose = findViewById(R.id.map_notification_close);
        taskList = findViewById(R.id.my_task_list);
        notificationTitle = findViewById(R.id.map_notification_title);
        notificationSubTitle = findViewById(R.id.map_notification_sub_title);
        rcvName = findViewById(R.id.receiver_name);
        taskComplete = findViewById(R.id.task_complete);
        callBtn = findViewById(R.id.call_btn);
        msgBtn = findViewById(R.id.msg_btn);
        onOff = findViewById(R.id.online_offline_text);
        onlineOffline = findViewById(R.id.online_offline);
        callBtn.setOnClickListener(view -> {
            String inID = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
            if(!inID.isEmpty() && items.size() > 0){
                for (TasksModel model:items){
                    if(inID.equalsIgnoreCase(model.getInId())){
                        contact(Constance.CALL, model.getRcvMobile());
                        break;
                    }
                }
            }
        });
        msgBtn.setOnClickListener(view -> {
            String inID = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
            if(!inID.isEmpty() && items.size() > 0){
                for (TasksModel model:items){
                    if(inID.equalsIgnoreCase(model.getInId())){
                        contact(Constance.MSG, model.getRcvMobile());
                        break;
                    }
                }
            }
        });
        mPref = PrefManager.getInstance(this);
        showOnOff();
        if(!tasks.isEmpty()) {
            try {
                JSONArray array = new JSONArray(tasks);
                for (int i = 0; i < array.length(); i++) {
                    items.add(new TasksModel().prepare(array.getJSONObject(i)));
                    String destination = items.get(i).getStartLat().concat(", ").concat(items.get(i).getStartLong());
                    String origin = items.get(i).getLatitude().concat(", ").concat(items.get(i).getLongitude());
                    mPresenter.loadDirections(destination, origin, "driving", MAP_KEY);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bottomSheet = new TaskListSheet(this, items, mapDirections);
            if(!mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID).equalsIgnoreCase(items.get(0).getInId())) {
                bottomSheet.show(getSupportFragmentManager(), "task_list");
                notification.setVisibility(View.VISIBLE);
            }else{
                if(items.size() > 0){
                    openEndRideView();
                }
            }
        }
        taskList.setOnClickListener(view->{
            bottomSheet.show(getSupportFragmentManager(), "task_list");
        });
        locator.setOnClickListener(view->{
            permission_request(true);
        });
        notification.setOnClickListener(view -> {
            bottomSheet.show(getSupportFragmentManager(), "task_list");
            notification.setVisibility(View.GONE);
        });
        notificationClose.setOnClickListener(view -> {
            notification.setVisibility(View.GONE);
        });
        taskComplete.setOnClickListener(view -> {
            String id = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
            String taskID = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
            String mobile = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
            for (TasksModel item:items){
                if(id.equalsIgnoreCase(item.getInId())){
                    taskID = item.getId();
                    mobile = item.getRcvMobile();
                    break;
                }
            }
            mPresenter.sendOtp(taskID, mobile);
        });
        onlineOffline.setOnClickListener(view -> {
            setOnOff();
        });
        notificationTitle.setText(notificationTitle.getText().toString().concat(" ").concat(String.valueOf(items.size())).concat(" new delivery requests!"));
        notificationSubTitle.setText("There are ".concat(String.valueOf(items.size())).concat(" ").concat(notificationSubTitle.getText().toString()));
        taskList.setVisibility(tasks.isEmpty() ? View.GONE : View.VISIBLE);
        IntentFilter onUpChat = new IntentFilter(); onUpChat.addAction(Constance.ON_TASK_CHANGE);
        registerReceiver(taskChangeReceiver, onUpChat);
    }

    private void showOnOff() {
        if(mPref.getBoolean(PrefManager.IS_ONLINE)){
            onlineOffline.setBackground(getResources().getDrawable(R.drawable.btn_circle_back));
            onOff.setText(getResources().getString(R.string.online));
        }else{
            onlineOffline.setBackground(getResources().getDrawable(R.drawable.btn_circle_back2));
            onOff.setText(getResources().getString(R.string.offline));
        }
    }

    private void setOnOff() {
        mPref.putBoolean(PrefManager.IS_ONLINE, !mPref.getBoolean(PrefManager.IS_ONLINE));
        showOnOff();
    }

    private void openOTPConfirmationDialog() {
        if(!otpDialog.isShowing()){
            otpDialog.show(false, this);
        }
    }

    private void openEndRideView() {
        endRideView.setVisibility(View.VISIBLE);
        for(TasksModel model : items){
            if(model.getInId().equalsIgnoreCase(mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID))){
                rcvName.setText(model.getRcvName());
            }
        }
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLong, 20));
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
        if(items.size() != mapDirections.size()){
            mapDirections.add(response);
        }
        PolylineOptions lineOptions = new PolylineOptions();
        ArrayList<LatLng> allPoints = response.getPoints();
        lineOptions.addAll(allPoints);
        lineOptions.width(12);
        lineOptions.color(Color.RED);
        lineOptions.geodesic(true);
        mMap.addPolyline(lineOptions);
        mMap.addMarker(new MarkerOptions().position(new LatLng(response.getLatitudePickup(), response.getLongitudePickup())).title("Pickup"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(response.getLatitudeDestination(), response.getLongitudeDestination())).title("Delivery Point."));
        zoomOnTargetView(response);
    }

    private void zoomOnTargetView(MapDirection response) {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(response.getLatitudePickup(), response.getLongitudePickup()))
                .include(new LatLng(response.getLatitudeDestination(), response.getLongitudeDestination())).build();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, 850, 30));
    }

    @Override
    public void onStartRide(int id) {
        if(items.size() > id){
            items.get(id).setActivity(2);
            permission_request(true);
            if(bottomSheet != null){
                bottomSheet.dismiss();
                openEndRideView();
            }
        }
    }

    @Override
    public void movieCamera(int position) {
        if(position < mapDirections.size()){
            onLoad(mapDirections.get(position));
        }
    }

    @Override
    public void contact(String call, String mobile) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
        } else {
            if(Constance.CALL.equalsIgnoreCase(call)){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                startActivity(intent);
            }
            if(Constance.MSG.equalsIgnoreCase(call)){
                Uri uri = Uri.parse("smsto:"+mobile);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "Hello CS24 Rider.");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onSuccess(MapDirection response) {
        onLoad(response);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtpSubmit(String s) {
        String id = mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID);
        String taskID = "";
        for (TasksModel item:items){
            if(id.equalsIgnoreCase(item.getInId())){
                taskID = item.getId();
                break;
            }
        }
        mPresenter.checkOtp(taskID, s);
    }

    @Override
    public void onOtpSend() {
        openOTPConfirmationDialog();
    }

    @Override
    public void onOtpSendFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void taskCompleteFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void taskCompleted() {
        endRideView.setVisibility(View.GONE);
        for (TasksModel model : items){
            if(model.getInId().equalsIgnoreCase(mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID))){
                model.setActivity(3);
            }
        }
        otpDialog.dismissNow();
    }

    private BroadcastReceiver taskChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && intent.hasExtra("status") && intent.hasExtra("pickup")){
                if(!dialog.isShowing()){
                    dialog.show(false,  intent.getStringExtra("pickup"));
                }
            }
        }
    };
}