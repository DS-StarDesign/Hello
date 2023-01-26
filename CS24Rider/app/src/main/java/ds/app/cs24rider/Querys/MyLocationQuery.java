package ds.app.cs24rider.Querys;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ds.app.cs24rider.CallBack.MyLocationUpdate;
import ds.app.cs24rider.Models.Auths.User;
import ds.app.cs24rider.Models.Maps.EndLocation;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;

public class MyLocationQuery extends AbstractInteractor {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Location");
    private MyLocationUpdate mCallBack;
    private EndLocation location;
    private String currentUser;

    public MyLocationQuery(Executor threadExecutor, MainThread mainThread, Context mContext, EndLocation location, MyLocationUpdate mCallBack) {
        super(threadExecutor, mainThread, mContext);
        this.currentUser = mPrefManager.getString(new User().getKeys()[0]);
        this.location = location;
        this.mCallBack = mCallBack;
    }

    @Override
    public void run() {
        myRef.child(currentUser).setValue(location, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e("sdfhsbdgsd", error == null ? "Success" : error.getMessage());
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onResponse(true, "Success");
                        cancel();
                    }
                });
            }
        });
    }
}
