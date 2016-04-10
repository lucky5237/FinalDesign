package zjut.jianlu.breakfast.base;

import android.util.Log;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import cn.smssdk.SMSSDK;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 3/9/2016.
 */
public class MyApplication extends SugarApp {

    private String TAG;

    private static Retrofit mRetrofit = null;

//    private T mService;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "onCreate() is called");
        Stetho.initializeWithDefaults(this);
        SMSSDK.initSDK(this, BreakfastConstant.SHARE_SDK_APP_KEY, BreakfastConstant.SHARE_SDK_APP_SECRET);

    }

    public static synchronized Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BreakfastConstant.URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

//    public T getService(Class<T> Clazz) {
//        mRetrofit = getRetrofitInstance();
//        mService = mRetrofit.create(Clazz);
//        return mService;
//    }
}
