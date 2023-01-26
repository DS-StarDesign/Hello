package ds.app.cs24rider.Utils;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class Constance {
    public static final String BASE_URL = "http://192.168.0.101/Services/api/"; //"http://courierservice24.com/mobile_api/api/";
    public static final String MAP_API_URL = "https://maps.googleapis.com/maps/api/";
    public static final String EMAIL = "Email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "Password";
    public static final String CFM_PASSWORD = "Cfm_Password";

    /*Validation*/
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\\\/%§\"&“|`´}{°><:.;#')(@_$\"!?*=^-]).{6,}$";
    public static final String EMAIL_EMPTY = "Please enter your email!";
    public static final String USERNAME_EMPTY = "Please enter your username!";
    public static final String EMAIL_INVALID = "Invalid email!";
    public static final String USERNAME_INVALID = "Invalid username!";
    public static final String PASSWORD_EMPTY = "Please enter your password!";
    public static final String CFM_PASSWORD_EMPTY = "Please enter confirm password!";
    public static final String PASSWORD_MIS_MATCH = "Confirm password doesn't match!";
    public static final String PASSWORD_INVALID = "Invalid password! (Minimum 6 character, At least one number, one special character, one uppercase letter, one lowercase letter.)";
    public static final String OTP= "otp";
    public static final String OTP_ERROR = "Please enter 6 digit valid code!";

    public static final String NO_INTERNET_CONNECTION = "No internet connection.";

    /*Broadcast*/
    public static final String ON_TASK_CHANGE = "change_task_response";
}
