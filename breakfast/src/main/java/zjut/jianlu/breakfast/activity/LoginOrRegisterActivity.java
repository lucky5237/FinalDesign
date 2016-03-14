package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.entity.User;

public class LoginOrRegisterActivity extends BaseActivity {

    @Bind(R.id.btn_login)Button mLoginBtn;
    @Bind(R.id.btn_register)Button mRegisterBtn;
    @OnClick({R.id.btn_login,R.id.btn_register})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                startActivity(new Intent(LoginOrRegisterActivity.this,LoginActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginOrRegisterActivity.this,RegisterActivity.class));
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BmobUser.getCurrentUser(LoginOrRegisterActivity.this, User.class)!=null){
            //说明已经登录过了
            startActivity(new Intent(LoginOrRegisterActivity.this,MainActivicy.class));
        }



    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_or_register;
    }
}
