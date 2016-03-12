package zjut.jianlu.breakfast.base;

import android.util.Log;

import com.orm.SugarApp;

import cn.bmob.v3.Bmob;
import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 3/9/2016.
 */
public class MyApplication extends SugarApp {

    private String TAG;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG=this.getClass().getSimpleName();
        Log.d(TAG,"onCreate() is called");
        Bmob.initialize(this,BreakfastConstant.BOMB_APPLICATION_ID);

    }
}
