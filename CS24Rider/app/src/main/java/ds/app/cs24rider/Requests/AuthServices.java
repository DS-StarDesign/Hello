package ds.app.cs24rider.Requests;

import ds.app.cs24rider.Models.Auths.Login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public interface AuthServices {

    @FormUrlEncoded
    @POST("Auths/login")
    Call<Login> login(@Field("username") String username, @Field("password") String password);
}
