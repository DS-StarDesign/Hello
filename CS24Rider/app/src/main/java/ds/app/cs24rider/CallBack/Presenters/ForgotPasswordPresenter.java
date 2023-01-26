package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.CallBack.BaseCallBack;
import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 07-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public interface ForgotPasswordPresenter {
    interface View extends BaseCallBack {
        void onSuccess();
        void onFailed(BaseResponse error);
        void onValidationError(String msg, String type);
        void onValidationSuccess();
    }
    void verify_email(String email);
    void change_password(String password, String cmfPassword, String code);
}
