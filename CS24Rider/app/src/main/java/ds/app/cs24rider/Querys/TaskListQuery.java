package ds.app.cs24rider.Querys;

import android.content.Context;

import ds.app.cs24rider.CallBack.MyLocationUpdate;
import ds.app.cs24rider.Models.Auths.User;
import ds.app.cs24rider.Models.Tasks.TaskResponse;
import ds.app.cs24rider.Requests.TaskServices;
import ds.app.cs24rider.Utils.RestClient;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskListQuery extends AbstractInteractor {

    private MyLocationUpdate mCallBack;
    private String currentUser;
    private TaskServices mCall;
    public TaskListQuery(Executor threadExecutor, MainThread mainThread, Context context, MyLocationUpdate mCallBack) {
        super(threadExecutor, mainThread, context);
        this.mCallBack = mCallBack;
        this.currentUser = mPrefManager.getString(new User().getKeys()[0]);
        this.mCall = RestClient.getService(mContext, TaskServices.class, true);
    }

    @Override
    public void run() {
        mCall.getAllTasks(currentUser).enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        mCallBack.onTaskListResponse(true, response.body().getListJson());
                    }else{
                        mCallBack.onTaskListResponse(false, response.body().getMsg());
                    }
                }else{
                    mCallBack.onTaskListResponse(false, response.message());
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                mCallBack.onTaskListResponse(false, t.getMessage());
            }
        });
    }
}
