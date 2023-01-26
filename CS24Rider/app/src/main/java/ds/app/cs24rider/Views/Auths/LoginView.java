package ds.app.cs24rider.Views.Auths;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ds.app.cs24rider.Businesses.Auths.AuthBusinesses;
import ds.app.cs24rider.CallBack.Presenters.AuthPresenter;
import ds.app.cs24rider.Utils.PrefManager;
import ds.app.cs24rider.Views.Home.MainActivity;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.Utils;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class LoginView extends AppCompatActivity implements AuthPresenter.View{

    private TextInputEditText etEmail, etPassword;
    private FloatingActionButton loginBtn;
    private LinearLayout errorView;
    private TextView errorMsg, forgotPass;
    private SweetAlertDialog loading;

    private AuthPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        init();
    }

    private void init(){
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.input_submit);
        errorView = findViewById(R.id.error_view);
        errorMsg = findViewById(R.id.error_msg);
        forgotPass = findViewById(R.id.forgot_pass_view);

        loading = Utils.getProgress(this);

        mPresenter = new AuthBusinesses(this, this);

        loginBtn.setOnClickListener(view->{
            mPresenter.login(etEmail.getText().toString(), etPassword.getText().toString());
        });

        forgotPass.setOnClickListener(view->{
            startActivity(new Intent(this, ForgotPasswordView.class));
        });
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));finish();
    }

    @Override
    public void onLoginFailed(BaseResponse error) {
        errorView.setVisibility(VISIBLE);
        errorMsg.setText(error.getMsg());
    }

    @Override
    public void onValidationError(String msg, String type) {
        errorView.setVisibility(VISIBLE);
        errorMsg.setText(msg);
        if (type.equalsIgnoreCase(Constance.PASSWORD)) {
            etPassword.setError(msg);
        } else {
            etEmail.setError(msg);
        }
    }

    @Override
    public void onValidationSuccess() {
        errorView.setVisibility(INVISIBLE);
        errorMsg.setText("");
    }

    @Override
    public void showProgress() {
        loginBtn.setClickable(false);
        loginBtn.setEnabled(false);
        loading.show();
    }

    @Override
    public void hideProgress() {
        loginBtn.setClickable(true);
        loginBtn.setEnabled(true);
        loading.dismiss();
    }

    @Override
    public void onNoInternet() {
        Snackbar.make(getWindow().getDecorView(), Constance.NO_INTERNET_CONNECTION, Snackbar.LENGTH_SHORT).show();
    }
}