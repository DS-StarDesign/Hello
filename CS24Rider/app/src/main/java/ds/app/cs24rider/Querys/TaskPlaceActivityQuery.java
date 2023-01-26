package ds.app.cs24rider.Querys;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ds.app.cs24rider.CallBack.MyLocationUpdate;
import ds.app.cs24rider.Models.Auths.User;
import ds.app.cs24rider.Models.Maps.EndLocation;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;

public class TaskPlaceActivityQuery extends AbstractInteractor {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("TaskPlace");
    private MyLocationUpdate mCallBack;
    private String currentUser;

    public TaskPlaceActivityQuery(Executor threadExecutor, MainThread mainThread, Context context, MyLocationUpdate mCallBack) {
        super(threadExecutor, mainThread, context);
        this.mCallBack = mCallBack;
        this.currentUser = mPrefManager.getString(new User().getKeys()[0]);
    }

    @Override
    public void run() {
        myRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mMainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallBack.onResponse(true, snapshot.getValue().toString());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mCallBack.onResponse(false, error.getMessage());
            }
        });
    }
}
