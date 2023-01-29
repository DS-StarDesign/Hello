package ds.app.cs24rider;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import ds.app.cs24rider.Businesses.Maps.LocationUpdateBusiness;
import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.PrefManager;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class MainApplication extends MultiDexApplication implements MyLocationUpdatePresenter.View{
    private static final String LOG_TAG = "CrashCatch";
    public static final String TAG = MainApplication.class
            .getSimpleName();

    private static MainApplication mInstance;
    private static PrefManager sPrefManager;
    private MyLocationUpdatePresenter mPresenter;
    private boolean isFirstTime = false;
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        mInstance = this;
        sPrefManager = PrefManager.getInstance(this);

        FirebaseMessaging.getInstance().subscribeToTopic("rider")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        mPresenter = new LocationUpdateBusiness(this, this);
        if(PrefManager.getInstance(this).isLoggedIn()){
            mPresenter.getTaskChange();
        }
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onResponse(boolean status, String message) {
        if(isFirstTime){
            sendBroadcast(new Intent().setAction(Constance.ON_TASK_CHANGE).putExtra("status", status).putExtra("pickup", message));
        }else{
            isFirstTime = true;
        }

    }

    @Override
    public void onTaskListResponse(boolean status, String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onNoInternet() {

    }
}

