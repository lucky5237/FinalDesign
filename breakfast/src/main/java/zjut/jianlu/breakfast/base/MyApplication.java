package zjut.jianlu.breakfast.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.Logger;
import com.orm.SugarApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.utils.MyMessageHandler;

/**
 * Created by jianlu on 3/9/2016.
 */
public class MyApplication extends SugarApp {

    private String TAG;

    private Context appContext;

    private static Retrofit mRetrofit = null;

    private static MyApplication INSTANCE;

    public static MyApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(MyApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(MyApplication a) {
        MyApplication.INSTANCE = a;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "onCreate() is called");
        setInstance(this);
        Bmob.DEBUG=true;
        //初始化
        Logger.init("smile");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MyMessageHandler(this));
        }
        //uil初始化
        initImageLoader(this);
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

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(3);
        config.memoryCache(new WeakMemoryCache());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
