package ds.app.cs24rider.Services;

import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        openDialog();
    }

    private void openDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setTitle("Notification Receive");
        alert.setMessage(" kfjskdfgs rsd ghsrd g rs gjsr gjsrg jsdg");
        alert.create().show();
    }
}
