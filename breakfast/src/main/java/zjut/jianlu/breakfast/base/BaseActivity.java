package zjut.jianlu.breakfast.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.db.UserDB;
import zjut.jianlu.breakfast.utils.SharedPreferencesUtil;

/**
 * Created by jianlu on 16/3/8.
 */
public abstract class BaseActivity extends FragmentActivity {

    private String TAG;
    public static Context mContext;
    protected Dialog dialog;
    private static List<Activity> mActivityList = new ArrayList<Activity>();

    public Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
        TAG = this.getClass().getSimpleName();
        this.setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        Log.d(TAG, "onCreate() is called");
    }

    public void Toast(String content) {
        if (content != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }


    public void ToastCenter(String content) {
        if (content != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public abstract int getLayoutId();

    /**
     * 显示进度弹出框
     */
    public void showMyDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
            view.findViewById(R.id.pd_base).startAnimation(AnimationUtils.loadAnimation(this, R.anim.progress));
            dialog = new Dialog(this, R.style.dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view, new WindowManager.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        dialog.show();

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    finish();
                }
                return false;
            }
        });
    }

    /**
     * 关闭进度弹出框
     */
    public void dismissMyDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 验证手机号是否合法
     *
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

    public static Integer getCurrentUserType() {

        return SharedPreferencesUtil.getInstance(mContext).getUserType();
    }

    public static Integer getCurrentUserID() {

        return SharedPreferencesUtil.getInstance(mContext).getUserId();

    }

    public static String getCurrentUserMobile() {

        return SharedPreferencesUtil.getInstance(mContext).getMobile();

    }

    public static UserDB getCurrentUser() {
        UserDB userDB = null;
        if (UserDB.count(UserDB.class) > 0) {
            userDB = UserDB.first(UserDB.class);
        }
        return userDB;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }

    public static void exit() {
        if (mActivityList != null && mActivityList.size() > 0) {
            for (Activity activity : mActivityList) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }

        }
        System.exit(0);
    }
}
