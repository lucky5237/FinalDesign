package zjut.jianlu.breakfast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.User;

/**
 * Created by jianlu on 3/9/2016.
 */
public class LoginActivity extends BaseActivity {

    private static String TAG;

    private Context mContext;

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
                Intent intent = new Intent(LoginActivity.this, SettingPasswordActivity.class);
                intent.putExtra(BreakfastConstant.FROM_WHICH_ACTIVITTY_TAG, TAG);
                startActivity(intent);
                break;
            case R.id.btn_login:
                BmobUser.loginByAccount(this, mEtMobile.getText().toString(), mEtPassword.getText().toString(), new LogInListener<User>() {

                    @Override
                    public void done(User user, BmobException e) {
                        if(user!=null){
                            Log.d("jianlu","用户登陆成功"+user.toString());
                            // TODO: 3/10/2016 登陆成功
                        }else{
                            Log.d("jianlu","用户登陆失败"+e.getErrorCode()+e.getMessage());
                            // TODO: 3/10/2016 处理登录错误 
                        }
                    }
                });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        TAG = LoginActivity.this.getClass().getSimpleName();
    }
}
