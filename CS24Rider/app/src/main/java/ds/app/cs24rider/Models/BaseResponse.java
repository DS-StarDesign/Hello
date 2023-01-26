package ds.app.cs24rider.Models;

import com.google.gson.annotations.SerializedName;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class BaseResponse {
    private boolean status;

    @SerializedName("message")
    private String msg;

    @SerializedName("code")
    private int code;

    public BaseResponse() {}

    public BaseResponse(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
