package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

public class EndLocation {

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("timestamp")
    private String timestamp;

    public EndLocation() {}

    public EndLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public EndLocation(String lat, String lng, String timestamp) {
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
