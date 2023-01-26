package ds.app.cs24rider.Views.Auths;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ds.app.cs24rider.Businesses.Auths.ForgotPasswordBusinesses;
import ds.app.cs24rider.CallBack.Presenters.ForgotPasswordPresenter;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.Utils;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class ForgotPasswordView extends AppCompatActivity implements ForgotPasswordPresenter.View {

    private TextInputEditText etEmail, etPassword, etCfmPassword;
    private TextInputLayout emailView;
    private FloatingActionButton loginBtn;
    private LinearLayout errorView, passwordEntry;
    private TextView errorMsg;
    private SweetAlertDialog loading;
    private ImageView backBtn;

    private ForgotPasswordPresenter mPresenter;

    private String code = "";
    private final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_view);

        etEmail = findViewById(R.id.input_email);
        emailView = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.input_password);
        etCfmPassword = findViewById(R.id.input_cfm_password);
        loginBtn = findViewById(R.id.input_submit);
        passwordEntry = findViewById(R.id.password_entry_view);
        errorView = findViewById(R.id.error_view);
        errorMsg = findViewById(R.id.error_msg);
        backBtn = findViewById(R.id.back_btn);

        loading = Utils.getProgress(this);

        mPresenter = new ForgotPasswordBusinesses(this, this);

        loginBtn.setOnClickListener(view->{
            if(code.isEmpty()){
                mPresenter.verify_email(etEmail.getText().toString());
            }else{
                mPresenter.change_password(etPassword.getText().toString(), etCfmPassword.getText().toString(), code);
            }
        });

        backBtn.setOnClickListener(view -> {finish();});
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

    @Override
    public void onSuccess() {
        if(code.isEmpty()){
            Intent intent = new Intent(ForgotPasswordView.this, OTPView.class);
            intent.putExtra(Constance.EMAIL, etEmail.getText().toString());
            startActivityForResult(intent, REQUEST_CODE);
        }else{

        }
    }

    @Override
    public void onFailed(BaseResponse error) {

    }

    @Override
    public void onValidationError(String msg, String type) {
        errorView.setVisibility(VISIBLE);
        errorMsg.setText(msg);
        if (type.equalsIgnoreCase(Constance.PASSWORD)) {
            etPassword.setError(msg);
        } else if (type.equalsIgnoreCase(Constance.CFM_PASSWORD)) {
            etCfmPassword.setError(msg);
        }else {
            etEmail.setError(msg);
        }
    }

    @Override
    public void onValidationSuccess() {
        errorView.setVisibility(INVISIBLE);
        errorMsg.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == REQUEST_CODE && data != null){
            if(!data.getStringExtra(Constance.OTP).isEmpty()){
                passwordEntry.setVisibility(VISIBLE);
                emailView.setVisibility(GONE);
                code = data.getStringExtra(Constance.OTP);
            }
        }
        hideProgress();
    }
}