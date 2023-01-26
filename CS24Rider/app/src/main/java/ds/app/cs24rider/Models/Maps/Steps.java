package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

public class Steps {

    @SerializedName("distance")
    private Distance distance;

    @SerializedName("duration")
    private Distance duration;

    @SerializedName("end_location")
    private EndLocation endLocation;

    @SerializedName("html_instructions")
    private String htmlInstructions;

    @SerializedName("start_location")
    private EndLocation startLocation;

    @SerializedName("travel_mode")
    private String travelMode;

    @SerializedName("polyline")
    private OverviewPolyline polyline;

    public Steps() {}

    public Steps(Distance distance, Distance duration, EndLocation endLocation, String htmlInstructions, EndLocation startLocation, String travelMode, OverviewPolyline polyline) {
        this.distance = distance;
        this.duration = duration;
        this.endLocation = endLocation;
        this.htmlInstructions = htmlInstructions;
        this.startLocation = startLocation;
        this.travelMode = travelMode;
        this.polyline = polyline;
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

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public EndLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(EndLocation startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public OverviewPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(OverviewPolyline polyline) {
        this.polyline = polyline;
    }
}
