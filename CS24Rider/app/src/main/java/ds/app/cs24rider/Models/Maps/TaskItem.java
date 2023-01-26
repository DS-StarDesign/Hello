package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

public class TaskItem {
    @SerializedName("end_location")
    private EndLocation endLocation;

    @SerializedName("start_location")
    private EndLocation startLocation;

    public TaskItem() {}

    public TaskItem(EndLocation endLocation, EndLocation startLocation) {
        this.endLocation = endLocation;
        this.startLocation = startLocation;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public EndLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(EndLocation startLocation) {
        this.startLocation = startLocation;
    }
}
