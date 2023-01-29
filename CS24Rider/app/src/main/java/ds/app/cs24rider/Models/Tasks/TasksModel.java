package ds.app.cs24rider.Models.Tasks;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class TasksModel {
    @SerializedName("id")
    private String id;

    @SerializedName("orderID")
    private String orderID;

    @SerializedName("rider_id")
    private String riderId;

    @SerializedName("in_id")
    private String inId;

    @SerializedName("address")
    private String address;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("start_lat")
    private String startLat;

    @SerializedName("start_long")
    private String startLong;

    @SerializedName("timestamp")
    private Long timestamp;

    @SerializedName("activity")
    private int activity;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("receiver_name")
    private String rcvName;

    @SerializedName("rcv_mobile")
    private String rcvMobile;

    public TasksModel(String id, String orderID, String riderId, String inId, String address, String latitude, String longitude, String startLat, String startLong, Long timestamp, int activity, String pickupAddress, String rcvName, String rcvMobile) {
        this.id = id;
        this.orderID = orderID;
        this.riderId = riderId;
        this.inId = inId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startLat = startLat;
        this.startLong = startLong;
        this.timestamp = timestamp;
        this.activity = activity;
        this.pickupAddress = pickupAddress;
        this.rcvName = rcvName;
        this.rcvMobile = rcvMobile;
    }

    public TasksModel() {
    }

    public String getRcvName() {
        return rcvName;
    }

    public void setRcvName(String rcvName) {
        this.rcvName = rcvName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getInId() {
        return inId;
    }

    public void setInId(String inId) {
        this.inId = inId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getRcvMobile() {
        return rcvMobile;
    }

    public void setRcvMobile(String rcvMobile) {
        this.rcvMobile = rcvMobile;
    }

    public String getJsonString(){
        String result = "{";
        result = result.concat("\"id\":").concat(getId() == null ? "\"\"" : "\""+getId()+"\"").concat(",");
        result = result.concat("\"orderID\":").concat(getOrderID() == null ? "\"\"" : "\""+getOrderID()+"\"").concat(",");
        result = result.concat("\"rider_id\":").concat(getRiderId() == null ? "\"\"" : "\""+getRiderId()+"\"").concat(",");
        result = result.concat("\"in_id\":").concat(getInId() == null ? "\"\"" : "\""+getInId()+"\"").concat(",");
        result = result.concat("\"address\":").concat(getAddress() == null ? "\"\"" : "\""+getAddress()+"\"").concat(",");
        result = result.concat("\"latitude\":").concat(getLatitude() == null ? "\"\"" : "\""+getLatitude()+"\"").concat(",");
        result = result.concat("\"longitude\":").concat(getLongitude() == null ? "\"\"" : "\""+getLongitude()+"\"").concat(",");
        result = result.concat("\"start_lat\":").concat(getStartLat() == null ? "\"\"" : "\""+getStartLat()+"\"").concat(",");
        result = result.concat("\"start_long\":").concat(getStartLong() == null ? "\"\"" : "\""+getStartLong()+"\"").concat(",");
        result = result.concat("\"timestamp\":").concat(getTimestamp() == null ? "\"\"" : "\""+getTimestamp()+"\"").concat(",");
        result = result.concat("\"activity\":").concat(getActivity() < 0 ? "\"\"" : "\""+getActivity()+"\"").concat(",");
        result = result.concat("\"receiver_name\":").concat(getRcvName() == null ? "\"\"" : "\""+getRcvName()+"\"").concat(",");
        result = result.concat("\"rcv_mobile\":").concat(getRcvMobile() == null ? "\"\"" : "\""+getRcvMobile()+"\"").concat(",");
        result = result.concat("\"pickup_address\":").concat(getPickupAddress() == null ? "\"\"" : "\""+getPickupAddress()+"\"");
        return result.concat("}");
    }

    public TasksModel prepare(JSONObject o) {
        TasksModel model = new TasksModel();
        try {
            model.setId(o.getString("id"));
            model.setOrderID(o.getString("orderID"));
            model.setRiderId(o.getString("rider_id"));
            model.setInId(o.getString("in_id"));
            model.setAddress(o.getString("address"));
            model.setLatitude(o.getString("latitude"));
            model.setLongitude(o.getString("longitude"));
            model.setStartLat(o.getString("start_lat"));
            model.setStartLong(o.getString("start_long"));
            model.setActivity(o.getInt("activity"));
            model.setPickupAddress(o.getString("pickup_address"));
            model.setRcvName(o.getString("receiver_name"));
            model.setRcvMobile(o.getString("rcv_mobile"));
            model.setTimestamp(o.getLong("timestamp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }
}
