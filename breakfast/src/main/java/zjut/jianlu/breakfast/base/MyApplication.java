package zjut.jianlu.breakfast.base;

import android.util.Log;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import cn.bmob.v3.Bmob;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 3/9/2016.
 */
public class MyApplication<T> extends SugarApp {

    private String TAG;

    private static Retrofit mRetrofit = null;

    private T mService;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "onCreate() is called");
        Stetho.initializeWithDefaults(this);
        Bmob.initialize(this, BreakfastConstant.BOMB_APPLICATION_ID);

    }

    public static synchronized Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BreakfastConstant.URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

    public T getService(Class<T> Clazz) {
        mRetrofit = getRetrofitInstance();
        mService = mRetrofit.create(Clazz);
        return mService;
    }
}
