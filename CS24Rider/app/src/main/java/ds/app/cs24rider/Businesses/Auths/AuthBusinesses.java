package ds.app.cs24rider.Businesses.Auths;

import android.content.Context;

import ds.app.cs24rider.CallBack.InterActors.AuthInterActor;
import ds.app.cs24rider.CallBack.Presenters.AuthPresenter;
import ds.app.cs24rider.Models.Auths.Login;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.Querys.LoginQuery;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.NetworkStatus;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import ds.app.cs24rider.Utils.Threading.MainThreadImpl;
import ds.app.cs24rider.Utils.Threading.ThreadExecutor;
import ds.app.cs24rider.Utils.Validators;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class AuthBusinesses implements AuthPresenter, AuthInterActor {

    private Context mContext;
    private AuthPresenter.View mView;
    private Executor mExecutor = ThreadExecutor.getInstance();
    private MainThread mMainThread = MainThreadImpl.getInstance();

    public AuthBusinesses(Context mContext, AuthPresenter.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void login(String email, String password) {
        BaseResponse emailResponse = Validators.AuthValidation(email, Constance.USERNAME);
        BaseResponse passwordResponse =  Validators.AuthValidation(password, Constance.PASSWORD);
        if(emailResponse.isStatus()){
            if(passwordResponse.isStatus()){
                if(NetworkStatus.hasNetwork(mContext)) {
                    mView.showProgress();
                    mView.onValidationSuccess();
                    new LoginQuery(mExecutor, mMainThread, mContext, email, password, this).execute();
                }else{
                    mView.onNoInternet();
                }
            }else{
                mView.onValidationError(passwordResponse.getMsg(), Constance.PASSWORD);
            }
        }else{
            mView.onValidationError(emailResponse.getMsg(), Constance.USERNAME);
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mView.hideProgress();
                mView.onLoginSuccess();
            }
        });
    }

    @Override
    public void onFailure(BaseResponse error) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mView.hideProgress();
                mView.onLoginFailed(error);
            }
        });
    }
}
