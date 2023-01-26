package ds.app.cs24rider.Models.Tasks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ds.app.cs24rider.Models.BaseResponse;

public class TaskResponse extends BaseResponse {

    @SerializedName("data")
    private List<TasksModel> tasks;

    public TaskResponse() {
    }

    public TaskResponse(List<TasksModel> tasks) {
        this.tasks = tasks;
    }

    public List<TasksModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksModel> tasks) {
        this.tasks = tasks;
    }

    public String getListJson(){
        String result = "[";
        for (TasksModel task : tasks){
            if(result.length() > 2){
                result = result.concat(", ");
            }
            result = result.concat(task.getJsonString());
        }
        return result.concat("]");
    }
}
