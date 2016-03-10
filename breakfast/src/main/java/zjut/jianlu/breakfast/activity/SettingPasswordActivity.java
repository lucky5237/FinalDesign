package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 3/10/2016.
 */
public class SettingPasswordActivity extends BaseActivity {

    private String mEtPasswordContent;
    private String mEtPasswordAgainContent;
    private static String mFromWhichActivity;
    private static final String FROM_RESET_PASSWORD_TAG = "LoginActivity";
    private boolean isResetPassword = false;
    private String mobile;//从注册页面传过来的手机号
//    private static final String FROM__TAG = "ResetPasswoedActivity";

    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_password_agin)
    EditText mEtpasswordAgain;
    @Bind(R.id.btn_next)
    Button mBtnNext;
    @Bind(R.id.tv_topbar)
    TextView mTvTopbar;


    @OnClick({R.id.btn_next, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next:
                mEtPasswordContent = mEtPassword.getText().toString();
                mEtPasswordAgainContent = mEtpasswordAgain.getText().toString();
                if (TextUtils.isEmpty(mEtPasswordContent) || TextUtils.isEmpty(mEtPasswordAgainContent)) {
                    showToast("密码或确认密码不能为空");
                    return;
                }
                if (mEtPasswordContent.length() < 6) {
                    showToast("密码长度至少为6位");
                    return;
                }
                if (!mEtPasswordContent.equals(mEtPasswordAgainContent)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                if (isResetPassword) {//从忘记密码跳转过来的
                    // TODO: 3/10/2016 重置密码成功！
                    showToast("密码重置成功!");
                    startActivity(new Intent(SettingPasswordActivity.this,LoginActivity.class));

                } else {//注册时候设置密码的
                    // TODO: 3/10/2016 密码设置成功，跳转到个人信息设置
                    Intent intent = new Intent(SettingPasswordActivity.this, UserInfoActivity.class);
                    intent.putExtra(BreakfastConstant.PASSWORD_TAG, mEtPasswordContent);
                    intent.putExtra(BreakfastConstant.MOBILE_TAG, mobile);
                    startActivity(intent);

                }
                break;

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            mFromWhichActivity = getIntent().getStringExtra(BreakfastConstant.FROM_WHICH_ACTIVITTY_TAG);
            if (!TextUtils.isEmpty(mFromWhichActivity) && mFromWhichActivity.equals(FROM_RESET_PASSWORD_TAG)) {
                isResetPassword = true;//从首页的重置密码跳转过来
                mTvTopbar.setText("重置密码");
                mBtnNext.setText("马上重置");
            } else {//注册手机号验证通过跳转过来
                mobile = getIntent().getStringExtra(BreakfastConstant.MOBILE_TAG);
            }
        }

    }
}
