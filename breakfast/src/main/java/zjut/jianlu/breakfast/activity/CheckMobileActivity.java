package zjut.jianlu.breakfast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
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
        Call<BaseResponse<String>> call = userService.checkMobile(new CheckMobileBody(mobile, isChangePwd));
        call.enqueue(new BaseCallback<String>() {
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
        if (!(mEtCaptcha.getText().toString().length() == 6)) {
            Toast("请输入长度为6位的验证码");
            return;
        }
        checkCaptcha();

    }

    private void checkCaptcha() {
        BmobSMS.verifySmsCode(mContext, mEtMobile.getText().toString().trim(), mEtCaptcha.getText().toString().trim(), new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //TODO 注册手机号验证成功
                    Intent intent = new Intent(CheckMobileActivity.this, SettingPasswordActivity.class);
                    intent.putExtra(BreakfastConstant.MOBILE_TAG, mEtMobile.getText().toString());
                    intent.putExtra(BreakfastConstant.TAG_IS_CHANGEPASSWORD, isChangePwd);
                    startActivity(intent);
                } else {
                    Toast("验证码错误");
                }
            }
        });
    }

    private void sendCaptcha() {
        Log.d("jianlu", "正在发送验证码");
        BmobSMS.requestSMSCode(mContext, mobile, "第一条短信测试", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    mHaveRequestCode = true;
                    Toast("正在发送短信到您手机,请注意查收");
                } else {
                    Log.d("jianlu", e.getMessage() + e.getErrorCode());
                    Toast(e.getMessage());
                    mTimeCount.cancel();
                }
            }
        });
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
                    mBtnCaptcha.setBackgroundResource(R.drawable.index_login);
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
            mBtnCaptcha.setBackgroundResource(R.drawable.index_login);
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
    }
}
