package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocodedWayPoints {

    @SerializedName("geocoder_status")
    private String geocoderStatus;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("types")
    private List<String> types;

    public GeocodedWayPoints() {}

    public GeocodedWayPoints(String geocoderStatus, String placeId, List<String> types) {
        this.geocoderStatus = geocoderStatus;
        this.placeId = placeId;
        this.types = types;
    }

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
