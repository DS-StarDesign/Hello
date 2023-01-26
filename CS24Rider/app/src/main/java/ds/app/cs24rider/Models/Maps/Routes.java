package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {

    @SerializedName("bounds")
    private Bounds bounds;

    @SerializedName("copyrights")
    private String copyrights;

    @SerializedName("summary")
    private String summary;

    @SerializedName("legs")
    private List<Legs> legs;

    @SerializedName("overview_polyline")
    private OverviewPolyline overviewPolyline;

    public Routes() {}

    public Routes(Bounds bounds, String copyrights, String summary, List<Legs> legs, OverviewPolyline overviewPolyline) {
        this.bounds = bounds;
        this.copyrights = copyrights;
        this.summary = summary;
        this.legs = legs;
        this.overviewPolyline = overviewPolyline;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
