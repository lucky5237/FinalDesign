package zjut.jianlu.breakfast.utils;

import android.util.Log;

/**
 * Created by jianlu on 16/4/6.
 */
public class LogUtil {

    private static final String TAG = "jianlu";

    private static int VERBOSE = 0;
    private static int DEBUG = 1;
    private static int INFO = 2;
    private static int WARN = 3;
    private static int ERROR = 4;
    private static int currentLevel = 1;

    public static void v(String message) {
        if (currentLevel >= VERBOSE) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (currentLevel >= DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if (currentLevel >= INFO) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (currentLevel >= WARN) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (currentLevel >= ERROR) {
            Log.e(TAG, message);
        }
    }


}
