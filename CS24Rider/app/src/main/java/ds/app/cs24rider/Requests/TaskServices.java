package ds.app.cs24rider.Requests;

import ds.app.cs24rider.Models.Tasks.TaskResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskServices {
    @GET("Tasks")
    Call<TaskResponse> getAllTasks(@Query("rider_id") String riderID);
}
