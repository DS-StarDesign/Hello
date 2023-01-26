package ds.app.cs24rider.Utils;

import ds.app.cs24rider.Models.BaseResponse;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class Validators {
    public static BaseResponse AuthValidation(String value, String type){
        BaseResponse response = new BaseResponse(true, "");
        if(type.equalsIgnoreCase(Constance.EMAIL)){
            if(value.trim().isEmpty()){
                response.setStatus(false);
                response.setMsg(Constance.EMAIL_EMPTY);
            } else if(!value.matches(Constance.EMAIL_PATTERN)){
                response.setStatus(false);
                response.setMsg(Constance.EMAIL_INVALID);
            }else{
                response.setMsg(value);
            }
        }
        if(type.equalsIgnoreCase(Constance.USERNAME)){
            if(value.trim().isEmpty()){
                response.setStatus(false);
                response.setMsg(Constance.USERNAME_EMPTY);
            } else if(!(value.length() > 3)){
                response.setStatus(false);
                response.setMsg(Constance.USERNAME_INVALID);
            }else{
                response.setMsg(value);
            }
        }
        if(type.equalsIgnoreCase(Constance.PASSWORD)){
            if(value.trim().isEmpty()){
                response.setStatus(false);
                response.setMsg(Constance.PASSWORD_EMPTY);
            } else if(!(value.length() > 5)){
                response.setStatus(false);
                response.setMsg(Constance.PASSWORD_INVALID);
            }else{
                response.setMsg(value);
            }
        }
        return response;
    }
}
