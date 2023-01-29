package ds.app.cs24rider.Businesses.Maps;

import android.content.Context;

import ds.app.cs24rider.CallBack.InterActors.MapDirectionInterActor;
import ds.app.cs24rider.CallBack.Presenters.MapDirectionPresenter;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.Querys.MapQuery;
import ds.app.cs24rider.Querys.TaskComplete;
import ds.app.cs24rider.Querys.TaskCompleteOtpSend;
import ds.app.cs24rider.Utils.Threading.MainThreadImpl;
import ds.app.cs24rider.Utils.Threading.ThreadExecutor;

public class MapDirectionBusiness implements MapDirectionPresenter, MapDirectionInterActor {

    private Context mContext;
    private MapDirectionPresenter.View mView;

    public MapDirectionBusiness(Context mContext, MapDirectionPresenter.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void onSuccess(MapDirection response) {
        mView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        mView.onFailed(error);
    }

    @Override
    public void onOtpSend() {
        mView.onOtpSend();
    }

    @Override
    public void onOtpSendFailed(String msg) {
        mView.onOtpSendFailed(msg);
    }

    @Override
    public void taskCompleted() {
        mView.taskCompleted();
    }

    @Override
    public void taskCompleteFailed(String msg) {
        mView.taskCompleteFailed(msg);
    }

    @Override
    public void loadDirections(String destination, String origin, String mode, String key) {
        new MapQuery(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this, destination, origin, mode, key).execute();
    }

    @Override
    public void sendOtp(String id, String mobile) {
        new TaskCompleteOtpSend(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this, id, mobile).execute();
    }

    @Override
    public void checkOtp(String id, String code) {
        new TaskComplete(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this, id, code).execute();
    }
}
