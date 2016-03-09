package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;

public class LoginOrRegisterActivity extends BaseActivity {

    @Bind(R.id.btn_login)Button mLoginBtn;
    @Bind(R.id.btn_register)Button mRegisterBtn;
    @OnClick({R.id.btn_login,R.id.btn_register})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                Toast.makeText(mContext,"LOGIN",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginOrRegisterActivity.this,LoginActivity.class));
                break;
            case R.id.btn_register:
                Toast.makeText(mContext,"REGISTER",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);



    }
}
