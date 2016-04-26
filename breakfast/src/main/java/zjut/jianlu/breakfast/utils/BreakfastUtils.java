package zjut.jianlu.breakfast.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 16/3/12.
 */
public class BreakfastUtils {

    private static final String IMAGE_FOLDER_NAME = "static/image/";

    private static final String AVATAR_FOLDER_NAME = "static/avatar/";


    public synchronized static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public static String getAbsImageUrlPath(String imageName) {

        return BreakfastConstant.URL + IMAGE_FOLDER_NAME + imageName + ".jpg";
    }

    public static String getAbsAvatarUrlPath(String userName) {

        return BreakfastConstant.URL + AVATAR_FOLDER_NAME + userName + ".jpg";
    }


    public static int getResourseIdByName(Context context, String resourseType, String resourceName) {

        return context.getResources().getIdentifier(resourceName, resourseType, context.getPackageName());
    }

    public static int getDrawableIdByName(Context context, String resourceName) {

        return getResourseIdByName(context, "mipmap", resourceName);
    }

    public static boolean isNetworkAvailable(Context context) {
        // Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getAppName(Context context,int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
