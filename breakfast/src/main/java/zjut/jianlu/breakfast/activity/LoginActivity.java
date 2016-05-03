package zjut.jianlu.breakfast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.MyUser;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.db.UserDB;
import zjut.jianlu.breakfast.entity.model.UserModel;
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
                doLogin();

        }
    }

    private void doLogin() {
        showMyDialog();
        Call<BaseResponse<MyUser>> call = userService.login(new LoginBody(mobile, password));
        call.enqueue(new BaseCallback<MyUser>() {
            @Override
            public void onFinally() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissMyDialog();
                    }
                }, 1000);
            }

            @Override
            public void onNetFailure(Throwable t) {
                ToastCenter("网络连接出错，请重试");
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<MyUser>> call, final Response<BaseResponse<MyUser>> response) {

                UserModel.getInstance().login(mobile, password, new LogInListener() {

                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            if (UserDB.count(UserDB.class) > 0) {
                                UserDB.deleteAll(UserDB.class);
                            }
                            MyUser myUser = response.body().getData();
                            UserDB userDB = new UserDB(myUser);
                            userDB.save();
                            sharedPreferencesUtil.setUserId(myUser.getId().intValue());
                            sharedPreferencesUtil.setUserType(myUser.getType());
                            sharedPreferencesUtil.setMobile(myUser.getMobile());
                            sharedPreferencesUtil.setPassword(myUser.getPassword());
                            User user = (User) o;
                            BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar()));
                            startActivity(new Intent(mContext, MainActivity.class));

                        } else {
                            Toast(e.getLocalizedMessage());
                        }
                    }
                });

            }


            @Override
            public void onBizFailure(Call<BaseResponse<MyUser>> call, Response<BaseResponse<MyUser>> response) {
                Toast(response.body().getMessage());

            }
        });
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
