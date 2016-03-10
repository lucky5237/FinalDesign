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
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 16/3/9.
 */
public class RegisterActivity extends BaseActivity {

    private Context mContext;

    private TimeCount mTimeCount;

    private TextWatcher mEtListener;

    private boolean mHaveRequestCode=false;
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

    @OnClick({R.id.iv_back, R.id.btn_captcha, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_captcha:
                sendCaptcha();
                mTimeCount = new TimeCount();
                mTimeCount.start();
                break;
            case R.id.btn_next:
//               checkInput();
                Intent intent=new Intent(RegisterActivity.this,SettingPasswordActivity.class);
                intent.putExtra(BreakfastConstant.MOBILE_TAG,mEtMobile.getText().toString());
                startActivity(intent);

        }
    }

    private void checkInput() {
        if (!(mEtMobile.getText().toString().length() == 11) || !isMobilePhone(mEtMobile.getText().toString())) {
            showToast("请输入合法的手机号");
            return;
        }
        if (!mHaveRequestCode){
            showToast("请先获取验证码");
            return;
        }
        if (!(mEtCaptcha.getText().toString().length() == 6)) {
            showToast("请输入长度为6位的验证码");
            return;
        }
        checkCaptcha();

    }

    private void checkCaptcha() {
        BmobSMS.verifySmsCode(mContext, mEtMobile.getText().toString().trim(), mEtCaptcha.getText().toString().trim(), new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "短信验证成功", Toast.LENGTH_SHORT).show();
                    //TODO 注册手机号验证成功
//                    User u = new User();
//                    u.setAddress("华海园");
//                    u.setUsername("jianlu");
//                    u.setBrief("我就是我，是不一样的烟火");
//                    u.setPassword("213131331");
//                    u.setGender(1);
//                    u.setType(1);
//                    u.signUp(mContext, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            showToast("服务器保存成功");
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            showToast("保存失败");
//                            Log.d("jianlu", "code is " + i + " message is " + s);
//                        }
//                    });

                } else {
                    Toast.makeText(mContext, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendCaptcha() {
        Log.d("jianlu", "正在发送验证码");
        BmobSMS.requestSMSCode(mContext, mEtMobile.getText().toString().trim(), "第一条短信测试", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    mHaveRequestCode=true;
                    showToast("正在发送短信到您手机,请注意查收");
                } else {
                    Log.d("jianlu", e.getMessage() + e.getErrorCode());
                    mTimeCount.cancel();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        ButterKnife.bind(this);
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
            mBtnCaptcha.setText(millisUntilFinished / 1000 +"s后重新获取");
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
