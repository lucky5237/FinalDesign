package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;

/**
 * Created by jianlu on 3/9/2016.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.et_mobile)
    EditText mEtMobile;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @OnClick({R.id.iv_back, R.id.btn_login})
    public void onClick(View view) {

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
