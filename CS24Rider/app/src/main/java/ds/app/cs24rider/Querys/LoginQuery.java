package ds.app.cs24rider.Querys;

import android.content.Context;
import android.util.Log;

import ds.app.cs24rider.CallBack.InterActors.AuthInterActor;
import ds.app.cs24rider.Models.Auths.Login;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.Requests.AuthServices;
import ds.app.cs24rider.Utils.PrefManager;
import ds.app.cs24rider.Utils.RestClient;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class LoginQuery extends AbstractInteractor {
    private String email, password;
    private AuthInterActor callBack;
    private AuthServices mCall;

    public LoginQuery(Executor executor, MainThread mainThread, Context mContext, String email, String password, AuthInterActor callBack) {
        super(executor, mainThread, mContext);
        this.email = email;
        this.password = password;
        this.callBack = callBack;
        this.mCall = RestClient.getService(mContext, AuthServices.class, true);
    }

    @Override
    public void run() {
        mCall.login(email, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        save_data(response.body());
                        callBack.onSuccess(response.body());
                    }else{
                        callBack.onFailure(response.body());
                    }
                }else{
                    callBack.onFailure(new BaseResponse(false, "Some error occurs. Please try again."));
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                callBack.onFailure(new BaseResponse(false, "Network Error."));
            }
        });
    }

    private void save_data(Login body) {
        for (String key : body.getUserInfo().getKeys()){
            mPrefManager.putString(key, body.getUserInfo().getValueByKey(key));
        }
        for (String key : body.getTokenInfo().getKeys()){
            mPrefManager.putString(key, body.getTokenInfo().getValueByKey(key));
        }
        mPrefManager.putString(PrefManager.ACCESS_TOKEN, body.getTokenInfo().getToken());
    }
}
