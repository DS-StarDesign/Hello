package ds.app.cs24rider.Businesses.Auths;

import android.content.Context;
import android.os.Handler;

import ds.app.cs24rider.CallBack.Presenters.OTPPresenter;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.NetworkStatus;
/*
 *  Created By MD. OLI ULLAH DEWAN 07-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class OTPBusinesses implements OTPPresenter{
    private Context mContext;
    private OTPPresenter.View mView;

    public OTPBusinesses(Context mContext, OTPPresenter.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void verify_code(String email, String code) {
        if(NetworkStatus.hasNetwork(mContext)){
            if(code.isEmpty() || code.length() != 6){
                mView.onValidationError(Constance.OTP_ERROR);
            }else{
                mView.onValidationSuccess();
                mView.showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.onSuccess();
                    }
                }, 3000);
            }
        }else{
            mView.onNoInternet();
        }
    }

    @Override
    public void resend_code(String email) {
        if(NetworkStatus.hasNetwork(mContext)){

        }else{
            mView.onNoInternet();
        }
    }
}
