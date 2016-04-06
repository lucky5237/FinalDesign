package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.requestBody.ChangePasswordBody;
import zjut.jianlu.breakfast.service.UserService;

/**
 * Created by jianlu on 3/10/2016.
 */
public class SettingPasswordActivity extends BaseActivity {

    private String mEtPasswordContent;
    private String mEtPasswordAgainContent;
    private String mobile;//从注册页面传过来的手机号
    private Retrofit retrofit;
    private UserService userService;
    private boolean isChangePwd = false;

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
                    Toast("密码或确认密码不能为空");
                    return;
                }
                if (mEtPasswordContent.length() < 6) {
                    Toast("密码长度至少为6位");
                    return;
                }
                if (!mEtPasswordContent.equals(mEtPasswordAgainContent)) {
                    Toast("两次密码输入不一致");
                    return;
                }
                if (isChangePwd) {//从忘记密码跳转过来的
                    // TODO: 3/10/2016 重置密码成功！
                    Call<BaseResponse<String>> call = userService.changePassword(new ChangePasswordBody(mobile, mEtPasswordContent));
                    call.enqueue(new Callback<BaseResponse<String>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getCode().equals("ACK")) {
                                    Toast(response.body().getMessage());
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(mContext, LoginActivity.class));
                                        }
                                    }, 1000);

                                } else {
                                    Toast(response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                        }
                    });


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
        if (getIntent() != null) {
            mobile = getIntent().getStringExtra(BreakfastConstant.MOBILE_TAG);
            isChangePwd = getIntent().getBooleanExtra(BreakfastConstant.TAG_IS_CHANGEPASSWORD, false);

            if (isChangePwd) {
                mTvTopbar.setText("重置密码");
                mBtnNext.setText("马上重置");
            }
        }
        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_password;
    }
}
