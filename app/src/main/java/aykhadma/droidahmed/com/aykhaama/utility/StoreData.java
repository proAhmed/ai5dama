package aykhadma.droidahmed.com.aykhaama.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ahmed on 7/14/2016.
 */
public class StoreData {
    String DATABASE_NAME = "sand.ubicall";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public StoreData(Context ctx) {
        super();
        this.context = ctx;

        sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }
    public String getToken() {
        return  sharedPreferences.getString("token","");
    }


    public void saveData(String category) {
        editor.putString("data", category);
        editor.apply();
    }
    public String getData() {
        return sharedPreferences.getString("data", "");
    }
    public String getDeviceId (){
        return sharedPreferences.getString("deviceId", "1");
    }
    public void setDeviceId(String deviceId) {
        editor.putString("deviceId", deviceId);
        editor.commit();
    }

    public void setPic(String pic) {
        editor.putString("pic", pic);
        editor.commit();
    }
    public String getPic() {
        return  sharedPreferences.getString("pic","");
    }
    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }
    public String getEmail() {
        return  sharedPreferences.getString("email","");
    }

    public void setMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.commit();
    }
    public String getMobile() {
        return  sharedPreferences.getString("mobile","");
    }


    public void setFullName(String fullName) {
        editor.putString("fullName", fullName);
        editor.commit();
    }
    public String getFullName() {
        return  sharedPreferences.getString("fullName","");
    }

    public void setCity(String city) {
        editor.putString("city", city);
        editor.apply();
    }
    public String getCountry() {
        return  sharedPreferences.getString("country","");
    }
    public void setCountry(String city) {
        editor.putString("country", city);
        editor.apply();
    }
    public String getCity() {
        return  sharedPreferences.getString("city","");
    }
    public void setCityId(int city) {
        editor.putInt("city_id", city);
        editor.apply();
    }
    public int getCityId() {
        return  sharedPreferences.getInt("city_id", 0);
    }

    public void setMyId(String myId) {
        editor.putString("my_id", myId);
        editor.commit();
    }
    public String getMyId() {
        return  sharedPreferences.getString("my_id", "");
    }
    public void setActivate(int activate) {
        editor.putInt("activate", activate);
        editor.commit();
    }
    public int getActivate() {
        return  sharedPreferences.getInt("activate",0);
    }


    public String getLang() {
        return  sharedPreferences.getString("lang","en");
    }
    public void setLang(String lang) {
        editor.putString("lang", lang);
        editor.apply();
    }

}
