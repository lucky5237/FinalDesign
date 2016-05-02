package zjut.jianlu.breakfast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.requestBody.CheckMobileBody;
import zjut.jianlu.breakfast.service.UserService;
import zjut.jianlu.breakfast.utils.LogUtil;

/**
 * Created by jianlu on 16/3/9.
 */
public class CheckMobileActivity extends BaseActivity {

    private Context mContext;

    private TimeCount mTimeCount;

    private TextWatcher mEtListener;

    private Retrofit retrofit;

    private UserService userService;

    private String mobile;

    private boolean mHaveRequestCode = false;

    private boolean isChangePwd = false;//是否是忘记密码页面跳转的

    private EventHandler eh;

    private static final int SHOW_TOAST_TAG = 1;

    private class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_TAG:
                    Toast(msg.obj.toString());
                    break;
            }
        }
    }

    private Myhandler myhandler = new Myhandler();


    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.et_mobile)
    EditText mEtMobile;
    @Bind(R.id.et_captcha)
    EditText mEtCaptcha;
    @Bind(R.id.btn_captcha)
    Button mBtnCaptcha;
    @Bind(R.id.btn_next)
    Button mBtnNext;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @OnClick({R.id.iv_back, R.id.btn_captcha, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_captcha:
                mobile = mEtMobile.getText().toString().trim();
                checkMobile();
                break;
            case R.id.btn_next:
                checkInput();


        }
    }

    private void checkMobile() {
        showMyDialog();
        Call<BaseResponse<String>> call = userService.checkMobile(new CheckMobileBody(mobile, isChangePwd));
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onFinally() {
                dismissMyDialog();
            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                sendCaptcha();
                mTimeCount = new TimeCount();
                mTimeCount.start();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());

            }
        });
//
    }

    private void checkInput() {
        if (!(mEtMobile.getText().toString().length() == 11) || !isMobilePhone(mEtMobile.getText().toString())) {
            Toast("请输入合法的手机号");
            return;
        }
        if (!mHaveRequestCode) {
            Toast("请先获取验证码");
            return;
        }
        if (!(mEtCaptcha.getText().toString().length() == 4)) {
            Toast("请输入长度为4位的验证码");
            return;
        }
        checkCaptcha();

    }

    private void checkCaptcha() {
        SMSSDK.submitVerificationCode("86", mEtMobile.getText().toString().trim(), mEtCaptcha.getText().toString().trim());
    }

    private void sendCaptcha() {
        Log.d("jianlu", "正在发送验证码");
        SMSSDK.getVerificationCode("86", mobile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    mBtnCaptcha.setBackgroundResource(R.drawable.login_confirm_orange);
                    mBtnCaptcha.setEnabled(true);
                } else {
                    mBtnCaptcha.setBackgroundResource(R.drawable.login_confirm_gray);
                    mBtnCaptcha.setEnabled(false);
                }
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            isChangePwd = intent.getBooleanExtra(BreakfastConstant.TAG_IS_CHANGEPASSWORD, false); //判断来自哪个注册还是忘记密码
            if (isChangePwd) {
                //忘记密码
                mTvTitle.setText("找回密码");
            }
        }
        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Intent intent = new Intent(CheckMobileActivity.this, SettingPasswordActivity.class);
                        intent.putExtra(BreakfastConstant.MOBILE_TAG, mEtMobile.getText().toString());
                        intent.putExtra(BreakfastConstant.TAG_IS_CHANGEPASSWORD, isChangePwd);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mHaveRequestCode = true;
                        Message msg = new Message();
                        msg.what = SHOW_TOAST_TAG;
                        msg.obj = BreakfastConstant.SEND_SMS_SUC;
                        myhandler.sendMessage(msg);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    }
                } else {
                    Throwable throwable = (Throwable) data;
                    LogUtil.d(throwable.getMessage());
                    JSONObject object = null;
                    try {
                        object = new JSONObject(throwable.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String des = object.optString("detail");//错误描述
                    int status = object.optInt("status");//错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        Message msg = new Message();
                        msg.what = SHOW_TOAST_TAG;
                        msg.obj = des;
                        myhandler.sendMessage(msg);
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_check_mobile;
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount() {
            super(60000, 1000);//一共60s每隔一秒执行ontick
        }

        @Override
        public void onFinish() {
            mBtnCaptcha.setText("获取验证码");
            mBtnCaptcha.setBackgroundResource(R.drawable.button_orange);
            mBtnCaptcha.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnCaptcha.setEnabled(false);
            mBtnCaptcha.setBackgroundResource(R.drawable.login_confirm_gray);
            mBtnCaptcha.setText(millisUntilFinished / 1000 + "s后重新获取");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeCount != null) {
            mTimeCount.cancel();
        }
        SMSSDK.unregisterEventHandler(eh);
    }
}
