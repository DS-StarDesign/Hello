package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.Models.Maps.MapDirection;

public interface MapDirectionPresenter {
    interface View {
        void onSuccess(MapDirection response);
        void onFailed(String error);
        void onOtpSubmit(String s);

        void onOtpSend();

        void onOtpSendFailed(String msg);

        void taskCompleteFailed(String msg);

        void taskCompleted();
    }
    void loadDirections(String destination, String origin, String mode, String key);
    void sendOtp(String id, String mobile);
    void checkOtp(String id, String code);
}
