package ds.app.cs24rider.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.PrefManager;
import ds.app.cs24rider.Views.Auths.LoginView;
import ds.app.cs24rider.Views.Home.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(PrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginView.class));
        }
        finish();
    }
}