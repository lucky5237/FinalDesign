package zjut.jianlu.breakfast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by jianlu on 16/3/26.
 * SharedPreferences工具类
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil sharedPreferencesUtil;
    private SharedPreferences sp;
    private Editor et;
    private static final String NAME = " breakfast";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";
    private static final String USERID = "userId";
    private static final String USERTYPE = "userType";


    public SharedPreferencesUtil(Context context) {

        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        et = sp.edit();
    }

    public static synchronized SharedPreferencesUtil getInstance(Context context) {
        if (sharedPreferencesUtil == null) {
            sharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return sharedPreferencesUtil;
    }

    public String getMobile() {
        return sp.getString(MOBILE, "");
    }

    public void setMobile(String mobile) {
        et.putString(MOBILE, mobile);
        et.commit();
    }

    public String getPassword() {
        return sp.getString(PASSWORD, "");
    }

    public void setPassword(String password) {
        et.putString(PASSWORD, password);
        et.commit();
    }

    public Integer getUserId() {
        return sp.getInt(USERID, 0);
    }

    public void setUserId(Integer userId) {
        et.putInt(USERID, userId);
        et.commit();
    }

    public Integer getUserType() {
        return sp.getInt(USERTYPE, -1);
    }

    public void setUserType(Integer userType) {
        et.putInt(USERTYPE, userType);
        et.commit();
    }

    public void clear() {
        et.clear();
        et.commit();
    }


}
