package ds.app.cs24rider.CallBack.InterActors;

import ds.app.cs24rider.Models.Auths.Login;
import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public interface AuthInterActor {
    void onSuccess(BaseResponse response);
    void onFailure(BaseResponse error);
}
