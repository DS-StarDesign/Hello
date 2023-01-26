package ds.app.cs24rider.Models.Maps;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MapDirection {
    private double latitudePickup, longitudePickup, latitudeDestination, longitudeDestination;
    private ArrayList<LatLng> points;

    public MapDirection() {
    }

    public MapDirection(double latitudePickup, double longitudePickup, double latitudeDestination, double longitudeDestination, ArrayList<LatLng> points) {
        this.latitudePickup = latitudePickup;
        this.longitudePickup = longitudePickup;
        this.latitudeDestination = latitudeDestination;
        this.longitudeDestination = longitudeDestination;
        this.points = points;
    }

    public double getLatitudePickup() {
        return latitudePickup;
    }

    public void setLatitudePickup(double latitudePickup) {
        this.latitudePickup = latitudePickup;
    }

    public double getLongitudePickup() {
        return longitudePickup;
    }

    public void setLongitudePickup(double longitudePickup) {
        this.longitudePickup = longitudePickup;
    }

    public double getLatitudeDestination() {
        return latitudeDestination;
    }

    public void setLatitudeDestination(double latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public double getLongitudeDestination() {
        return longitudeDestination;
    }

    public void setLongitudeDestination(double longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }
}
