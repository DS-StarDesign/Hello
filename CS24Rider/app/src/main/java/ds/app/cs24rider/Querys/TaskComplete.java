package ds.app.cs24rider.Querys;

import android.content.Context;

import ds.app.cs24rider.CallBack.InterActors.MapDirectionInterActor;
import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.Requests.TaskServices;
import ds.app.cs24rider.Utils.RestClient;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskComplete extends AbstractInteractor {
    private TaskServices mCall;
    private String taskId;
    private String code;
    private MapDirectionInterActor mCallBack;
    public TaskComplete(Executor instance, MainThread instance1, Context mContext, MapDirectionInterActor mCallBack, String id, String code) {
        super(instance, instance1, mContext);
        this.taskId = id;
        this.code = code;
        this.mCallBack = mCallBack;
        this.mCall = RestClient.getService(mContext, TaskServices.class, true);
    }

    @Override
    public void run() {
        mCall.task_complete(taskId, code).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        mMainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallBack.taskCompleted();
                            }
                        });
                    }else{
                        mCallBack.taskCompleteFailed(response.body().getMsg());
                    }
                }else{
                    mCallBack.taskCompleteFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mCallBack.taskCompleteFailed(t.getMessage());
            }
        });
    }
}
