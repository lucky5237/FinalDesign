package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.utils.SharedPreferencesUtil;

public class LoginOrRegisterActivity extends BaseActivity {

    @Bind(R.id.btn_login)
    Button mLoginBtn;
    @Bind(R.id.btn_register)
    Button mRegisterBtn;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(LoginOrRegisterActivity.this, LoginActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginOrRegisterActivity.this, CheckMobileActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(mContext);
        if (!TextUtils.isEmpty(sharedPreferencesUtil.getMobile()) && !TextUtils.isEmpty(sharedPreferencesUtil.getPassword())) {
            //登录过的用户直接跳转首页
            startActivity(new Intent(mContext, MainActivity.class));
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_or_register;
    }
}
