package ds.app.cs24rider.Businesses.Auths;

import android.content.Context;

import ds.app.cs24rider.CallBack.Presenters.ForgotPasswordPresenter;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.NetworkStatus;
import ds.app.cs24rider.Utils.Validators;
/*
*  Created By MD. OLI ULLAH DEWAN 07-01-2023
*  DEWAN SOFTWARE LTD.
* */
public class ForgotPasswordBusinesses implements ForgotPasswordPresenter {
    private Context mContext;
    private ForgotPasswordPresenter.View mView;

    public ForgotPasswordBusinesses(Context mContext, ForgotPasswordPresenter.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void verify_email(String email) {
        if(NetworkStatus.hasNetwork(mContext)){
            BaseResponse emailResponse = Validators.AuthValidation(email, Constance.EMAIL);
            if(emailResponse.isStatus()){
                mView.onValidationSuccess();
                mView.showProgress();
                mView.onSuccess();
            }else{
                mView.onValidationError(emailResponse.getMsg(), Constance.EMAIL);
            }
        }else{
            mView.onNoInternet();
        }
    }

    @Override
    public void change_password(String password, String cmfPassword, String code) {
        if(NetworkStatus.hasNetwork(mContext)){
            BaseResponse passwordResponse = Validators.AuthValidation(password, Constance.PASSWORD);
            if(passwordResponse.isStatus()){
                if(cmfPassword.trim().isEmpty()){
                    if(cmfPassword.equals(cmfPassword)){
                        if(code.isEmpty() || code.length() != 6){
                            mView.onValidationError(Constance.OTP_ERROR, Constance.OTP);
                        }else{
                            mView.onValidationSuccess();
                            mView.showProgress();
                        }
                    }else{
                        mView.onValidationError(Constance.PASSWORD_MIS_MATCH, Constance.CFM_PASSWORD);
                    }
                }else{
                    mView.onValidationError(Constance.CFM_PASSWORD_EMPTY, Constance.CFM_PASSWORD);
                }
            }else{
                mView.onValidationError(passwordResponse.getMsg(), Constance.PASSWORD);
            }
        }else{
            mView.onNoInternet();
        }
    }
}
