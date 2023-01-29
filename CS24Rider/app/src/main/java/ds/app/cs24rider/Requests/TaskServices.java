package ds.app.cs24rider.Requests;

import ds.app.cs24rider.Models.BaseResponse;
import ds.app.cs24rider.Models.Tasks.TaskResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TaskServices {
    @GET("Tasks")
    Call<TaskResponse> getAllTasks(@Query("rider_id") String riderID);

    @FormUrlEncoded
    @POST("Tasks/sendotp")
    Call<BaseResponse> send_otp(@Field("task_id") String taskID, @Field("rcv_mobile") String mobile);

    @FormUrlEncoded
    @POST("Tasks/task_complete")
    Call<BaseResponse> task_complete(@Field("task_id") String taskID, @Field("otp") String otp);
}
