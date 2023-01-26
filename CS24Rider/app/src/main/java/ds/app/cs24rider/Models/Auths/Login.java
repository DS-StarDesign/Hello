package ds.app.cs24rider.Models.Auths;

import com.google.gson.annotations.SerializedName;

import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class Login extends BaseResponse {

    @SerializedName("user_info")
    private User userInfo;

    @SerializedName("token_info")
    private Token tokenInfo;

    public Login() {}

    public Login(User userInfo, Token tokenInfo) {
        this.userInfo = userInfo;
        this.tokenInfo = tokenInfo;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public Token getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(Token tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
}
