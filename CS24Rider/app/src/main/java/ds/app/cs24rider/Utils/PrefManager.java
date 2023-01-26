package ds.app.cs24rider.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class PrefManager {

    public static final String sPrefManagerName = "ds_app_cs24rider";
    public static final String ACCESS_TOKEN = "access_token";

    private final SharedPreferences mSharedPreferences;
    private static PrefManager sPrefManager;

    private PrefManager(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(sPrefManagerName, MODE_PRIVATE);
    }

    public static PrefManager getInstance(Context context) {
        if (sPrefManager == null) {
            sPrefManager = new PrefManager(context);
        }
        return sPrefManager;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void removeString(String key){
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.remove(key);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0L);
    }

    public void clear(){
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void remove(){
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.remove(sPrefManagerName);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return getString("token") != null && !getString("token").isEmpty() && getString("email") != null && !getString("email").isEmpty();
    }

    public boolean logout(){
        SharedPreferences.Editor editor;
        editor = mSharedPreferences.edit();
        editor.remove("token");
        editor.remove("email");
        editor.apply();
        return isLoggedIn();
    }
}
