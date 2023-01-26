package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("northeast")
    private EndLocation northeast;

    @SerializedName("southwest")
    private EndLocation southwest;

    public Bounds() {}

    public Bounds(EndLocation northeast, EndLocation southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public EndLocation getNortheast() {
        return northeast;
    }

    public void setNortheast(EndLocation northeast) {
        this.northeast = northeast;
    }

    public EndLocation getSouthwest() {
        return southwest;
    }

    public void setSouthwest(EndLocation southwest) {
        this.southwest = southwest;
    }
}
