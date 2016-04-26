package zjut.jianlu.breakfast.base;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.orm.SugarApp;

import cn.smssdk.SMSSDK;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.utils.BreakfastUtils;

/**
 * Created by jianlu on 3/9/2016.
 */
public class MyApplication extends SugarApp {

    private String TAG;

    private Context appContext;

    private static Retrofit mRetrofit = null;

    // private T mService;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = BreakfastUtils.getAppName(appContext, pid);
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        EMClient.getInstance().init(appContext, options);
        EMClient.getInstance().setDebugMode(true);
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "onCreate() is called");
        Stetho.initializeWithDefaults(this);
        SMSSDK.initSDK(this, BreakfastConstant.SHARE_SDK_APP_KEY, BreakfastConstant.SHARE_SDK_APP_SECRET);

    }

    public static synchronized Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BreakfastConstant.URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

    // public T getService(Class<T> Clazz) {
    // mRetrofit = getRetrofitInstance();
    // mService = mRetrofit.create(Clazz);
    // return mService;
    // }
}
