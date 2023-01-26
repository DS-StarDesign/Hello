package ds.app.cs24rider.Models.Auths;

import com.google.gson.annotations.SerializedName;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class Token {
    @SerializedName("token")
    private String token;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_type")
    private int userType;

    @SerializedName("expired_from")
    private String expiredFrom;

    @SerializedName("expired_to")
    private String expiredTo;

    @SerializedName("device")
    private String device;

    @SerializedName("type")
    private int type;

    @SerializedName("status")
    private int status;

    public Token() {}

    public Token(String token, String userId, int userType, String expiredFrom, String expiredTo, String device, int type, int status) {
        this.token = token;
        this.userId = userId;
        this.userType = userType;
        this.expiredFrom = expiredFrom;
        this.expiredTo = expiredTo;
        this.device = device;
        this.type = type;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getExpiredFrom() {
        return expiredFrom;
    }

    public void setExpiredFrom(String expiredFrom) {
        this.expiredFrom = expiredFrom;
    }

    public String getExpiredTo() {
        return expiredTo;
    }

    public void setExpiredTo(String expiredTo) {
        this.expiredTo = expiredTo;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String[] getKeys() {
        String[] keys = {"token", "user_id", "user_type", "expired_from", "expired_to", "device", "type", "status" };
        return keys;
    }

    public String getValueByKey(String key){
        String value = "";
        switch (key){
            case "token": value = getToken(); break;
            case "user_id": value = getUserId(); break;
            case "user_type": value = String.valueOf(getUserType()); break;
            case "expired_from": value = getExpiredFrom(); break;
            case "expired_to": value = getExpiredTo(); break;
            case "device": value = getDevice(); break;
            case "type": value = String.valueOf(getType()); break;
            case "status": value = String.valueOf(getStatus()); break;
            default: value = "";
        }
        return value;
    }
}
