package zjut.jianlu.breakfast.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jianlu on 16/3/8.
 */
public class BaseActivity extends Activity {

    private String TAG;
    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
        mContext=this;
        Log.d(TAG,"onCreate() is called");
    }

    public void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }
}
