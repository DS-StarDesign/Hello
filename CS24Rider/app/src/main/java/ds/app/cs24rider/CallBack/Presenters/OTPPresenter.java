package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.CallBack.BaseCallBack;
import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 07-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public interface OTPPresenter {
    interface View extends BaseCallBack {
        void onSuccess();
        void onFailed(BaseResponse error);
        void onValidationError(String msg);
        void onValidationSuccess();
    }
    void verify_code(String email, String code);
    void resend_code(String email);
}
