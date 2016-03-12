package zjut.jianlu.breakfast.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

/**
 * Created by jianlu on 16/3/8.
 */
public  abstract class BaseActivity extends FragmentActivity {

    private String TAG;
    public Context mContext;
    public Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        this.setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        Log.d(TAG, "onCreate() is called");
    }

    public void showToast(String content) {
        if (content != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }

    public abstract int getLayoutId();

    /**
     * 验证手机号是否合法
     * @param phone
     * @return
     */

    public static boolean isMobilePhone(String phone) {
        Pattern pattern = Pattern
                .compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {

            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}
