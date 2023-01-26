package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.CallBack.BaseCallBack;
import ds.app.cs24rider.Models.Maps.EndLocation;

public interface MyLocationUpdatePresenter {

    interface View extends BaseCallBack {
        void onResponse(boolean status, String message);
        void onTaskListResponse(boolean status, String message);
    }
    void giveUpdate(EndLocation myLocation);
    void getTaskChange();
    void getTaskList();
}
