package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Legs {

    @SerializedName("distance")
    private Distance distance;

    @SerializedName("duration")
    private Distance duration;

    @SerializedName("end_address")
    private String endAddress;

    @SerializedName("start_address")
    private String startAddress;

    @SerializedName("end_location")
    private EndLocation endLocation;

    @SerializedName("start_location")
    private EndLocation startLocation;

    @SerializedName("steps")
    private List<Steps> steps;

    public Legs() {}

    public Legs(Distance distance, Distance duration, String endAddress, String startAddress, EndLocation endLocation, EndLocation startLocation, List<Steps> steps) {
        this.distance = distance;
        this.duration = duration;
        this.endAddress = endAddress;
        this.startAddress = startAddress;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.steps = steps;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Distance getDuration() {
        return duration;
    }

    public void setDuration(Distance duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
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

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }
}
