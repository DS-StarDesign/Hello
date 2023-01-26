package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapDirectionResponse {

    @SerializedName("geocoded_waypoints")
    private List<GeocodedWayPoints> geocodedWayPoints;

    @SerializedName("routes")
    private List<Routes> routes;

    @SerializedName("status")
    private String status;

    public MapDirectionResponse() {}

    public MapDirectionResponse(List<GeocodedWayPoints> geocodedWayPoints, List<Routes> routes, String status) {
        this.geocodedWayPoints = geocodedWayPoints;
        this.routes = routes;
        this.status = status;
    }

    public List<GeocodedWayPoints> getGeocodedWayPoints() {
        return geocodedWayPoints;
    }

    public void setGeocodedWayPoints(List<GeocodedWayPoints> geocodedWayPoints) {
        this.geocodedWayPoints = geocodedWayPoints;
    }

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
