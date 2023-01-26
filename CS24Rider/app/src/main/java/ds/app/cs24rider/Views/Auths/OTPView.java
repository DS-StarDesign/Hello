package ds.app.cs24rider.Views.Auths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ds.app.cs24rider.Businesses.Auths.OTPBusinesses;
import ds.app.cs24rider.CallBack.Presenters.OTPPresenter;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.Utils;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class OTPView extends AppCompatActivity implements OTPPresenter.View {

    private PinView pinView;
    private FloatingActionButton submitBtn;
    private TextView resendBtn, counterView;
    private LinearLayout errorView;
    private TextView errorMsg;

    private SweetAlertDialog loading;

    private OTPPresenter mPresenter;
    private String email = "";

    private int counter = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpview);
        if(getIntent() == null || getIntent().getStringExtra(Constance.EMAIL) == null){ finish(); }
        email = getIntent().getStringExtra(Constance.EMAIL);
        init(); count();
    }

    private void init(){
        pinView = findViewById(R.id.pin_view);
        submitBtn = findViewById(R.id.input_submit);
        resendBtn = findViewById(R.id.resend_btn);
        counterView = findViewById(R.id.remaining_time_sec);
        errorView = findViewById(R.id.error_view);
        errorMsg = findViewById(R.id.error_msg);
        counterView.setText(counter+" Sec");

        loading = Utils.getProgress(this);

        mPresenter = new OTPBusinesses(this, this);

        submitBtn.setOnClickListener(view->{
            mPresenter.verify_code(email, pinView.getText().toString());
        });

        resendBtn.setOnClickListener(view->{
            resendBtn.setEnabled(false);
            resendBtn.setClickable(false);
            resendBtn.setAlpha(0.3f);
            mPresenter.resend_code(email);
        });
    }

    @Override
    public void showProgress() {
        submitBtn.setClickable(false);
        submitBtn.setEnabled(false);
        loading.show();
    }

    @Override
    public void hideProgress() {
        submitBtn.setClickable(true);
        submitBtn.setEnabled(true);
        loading.dismiss();
    }

    @Override
    public void onNoInternet() {
        Snackbar.make(getWindow().getDecorView(), Constance.NO_INTERNET_CONNECTION, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        setResult(200, new Intent().putExtra(Constance.OTP, pinView.getText().toString()));
        finish();
    }

    @Override
    public void onFailed(BaseResponse error) {
        errorView.setVisibility(View.VISIBLE);
        errorMsg.setText(error.getMsg());
    }

    @Override
    public void onValidationError(String msg) {
        errorView.setVisibility(View.VISIBLE);
        errorMsg.setText(msg);
    }

    @Override
    public void onValidationSuccess() {
        errorView.setVisibility(View.GONE);
    }

    private void count(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(counter >= 0){
                    counterView.setText(counter+" Sec");
                    counter--;
                    count();
                }else{
                    resendBtn.setEnabled(true);
                    resendBtn.setClickable(true);
                    resendBtn.setAlpha(1f);
                    counter = 0;
                }
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}