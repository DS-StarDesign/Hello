package ds.app.cs24rider.Views.Home.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ds.app.cs24rider.R;

public class Notification extends Fragment {

    public Notification() {}
    public static Notification newInstance() {
        return new Notification();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }
}