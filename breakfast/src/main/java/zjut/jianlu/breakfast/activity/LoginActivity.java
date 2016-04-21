package zjut.jianlu.breakfast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.db.UserDB;
import zjut.jianlu.breakfast.entity.requestBody.LoginBody;
import zjut.jianlu.breakfast.service.UserService;
import zjut.jianlu.breakfast.utils.SharedPreferencesUtil;

/**
 * Created by jianlu on 3/9/2016.
 */
public class LoginActivity extends BaseActivity {

    private static String TAG;

    private Context mContext;

    private Retrofit mRetrofit;

    private UserService userService;

    private String mobile;

    private String password;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.et_mobile)
    EditText mEtMobile;
    @Bind(R.id.et_password)


    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_forget_password)
    TextView mTvForgetPassword;


    @OnClick({R.id.iv_back, R.id.btn_login, R.id.tv_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_forget_password:
                Intent intent = new Intent(LoginActivity.this, CheckMobileActivity.class);
                intent.putExtra(BreakfastConstant.TAG_IS_CHANGEPASSWORD, true);
                startActivity(intent);
                break;
            case R.id.btn_login:
                mobile = mEtMobile.getText().toString().trim();
                password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)) {
                    Toast("手机号或密码不能为空");
                    return;
                } else if (mobile.length() < 11 || !isMobilePhone(mobile)) {
                    Toast("请输入合法的手机号");
                    return;

                } else if (password.length() < 6) {
                    Toast("密码的长度至少为6位");
                    return;
                }
                showMyDialog();
                Call<BaseResponse<User>> call = userService.login(new LoginBody(mobile, password));
                call.enqueue(new BaseCallback<User>() {
                    @Override
                    public void onFinally() {
                        dismissMyDialog();
                    }

                    @Override
                    public void onNetFailure(Throwable t) {
                        ToastCenter("网络连接出错，请重试");
                    }

                    @Override
                    public void onBizSuccess(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        if (UserDB.count(UserDB.class) > 0) {
                            UserDB.deleteAll(UserDB.class);
                        }
                        User user = response.body().getData();
                        UserDB userDB = new UserDB(user);
                        userDB.save();
                        sharedPreferencesUtil.setUserId(user.getId().intValue());
                        sharedPreferencesUtil.setUserType(user.getType());
                        sharedPreferencesUtil.setMobile(user.getMobile());
                        sharedPreferencesUtil.setPassword(user.getPassword());
                        startActivity(new Intent(mContext, MainActivicy.class));
                        Toast("登录成功");
                        // TODO: 16/3/25 登录成功处理

                    }

                    @Override
                    public void onBizFailure(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        Toast(response.body().getMessage());

                    }
                });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = LoginActivity.this.getClass().getSimpleName();
        mRetrofit = MyApplication.getRetrofitInstance();
        userService = mRetrofit.create(UserService.class);
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(mContext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
