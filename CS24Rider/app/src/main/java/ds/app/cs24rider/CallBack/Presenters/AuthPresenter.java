package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.CallBack.BaseCallBack;
import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public interface AuthPresenter {
    interface View extends BaseCallBack{
        void onLoginSuccess();
        void onLoginFailed(BaseResponse error);
        void onValidationError(String msg, String type);
        void onValidationSuccess();
    }
    void login(String email, String password);
}
