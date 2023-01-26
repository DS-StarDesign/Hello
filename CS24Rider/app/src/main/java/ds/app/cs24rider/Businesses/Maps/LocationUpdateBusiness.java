package ds.app.cs24rider.Businesses.Maps;

import android.content.Context;

import ds.app.cs24rider.CallBack.MyLocationUpdate;
import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.Models.Maps.EndLocation;
import ds.app.cs24rider.Querys.MyLocationQuery;
import ds.app.cs24rider.Querys.TaskListQuery;
import ds.app.cs24rider.Querys.TaskPlaceActivityQuery;
import ds.app.cs24rider.Utils.NetworkStatus;
import ds.app.cs24rider.Utils.Threading.MainThreadImpl;
import ds.app.cs24rider.Utils.Threading.ThreadExecutor;

public class LocationUpdateBusiness implements MyLocationUpdatePresenter, MyLocationUpdate {
    private Context mContext;
    private MyLocationUpdatePresenter.View mView;
    private MyLocationQuery query;
    private TaskPlaceActivityQuery getQuery;
    private TaskListQuery getTasksQuery;

    public LocationUpdateBusiness(Context mContext, MyLocationUpdatePresenter.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void giveUpdate(EndLocation myLocation) {
        if(query == null){
            query =  new MyLocationQuery(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, myLocation, this);
        }
       if(NetworkStatus.hasNetwork(mContext)) {
           query.execute();
       }else{
            mView.onNoInternet();
        }
    }

    @Override
    public void getTaskChange() {
        if(getQuery == null){
            getQuery =  new TaskPlaceActivityQuery(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this);
        }
        if(NetworkStatus.hasNetwork(mContext)) {
            getQuery.execute();
        }else{
            mView.onNoInternet();
        }
    }

    @Override
    public void getTaskList() {
        if(getTasksQuery == null){
            getTasksQuery =  new TaskListQuery(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this);
        }
        if(NetworkStatus.hasNetwork(mContext)) {
            mView.showProgress();
            getTasksQuery.execute();
        }else{
            mView.onNoInternet();
        }
    }

    @Override
    public void onResponse(boolean status, String message) {
        mView.onResponse(status, message);
    }

    @Override
    public void onTaskListResponse(boolean status, String message) {
        mView.hideProgress();
        mView.onTaskListResponse(status, message);
    }
}
