package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import zjut.jianlu.breakfast.entity.requestBody.ChangePasswordBody;
import zjut.jianlu.breakfast.service.UserService;

/**
 * Created by jianlu on 16/4/21.
 */
public class ChangePasswordActvity extends BaseActivity {
    private String mEtOldPasswordContent;
    private String mEtPasswordContent;
    private String mEtPasswordAgainContent;
    private Retrofit retrofit;
    private UserService userService;
    private boolean isChangePwd = false;

    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_password_agin)
    EditText mEtpasswordAgain;
    @Bind(R.id.btn_next)
    Button mBtnNext;
    @Bind(R.id.et_old_password)
    EditText mEtOldPassword;

    @OnClick({R.id.btn_next, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next:
                mEtPasswordContent = mEtPassword.getText().toString();
                mEtPasswordAgainContent = mEtpasswordAgain.getText().toString();
                mEtOldPasswordContent = mEtOldPassword.getText().toString();
                if (TextUtils.isEmpty(mEtOldPasswordContent)) {
                    Toast("旧密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mEtPasswordContent)) {
                    Toast("新密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mEtPasswordAgainContent)) {
                    Toast("确认密码不能为空");
                    return;
                }
                if (mEtOldPasswordContent.length() < 6) {
                    Toast("旧密码长度至少为6位");
                    return;
                }
                if (mEtPasswordContent.length() < 6) {
                    Toast("新密码长度至少为6位");
                    return;
                }
                if (!mEtPasswordContent.equals(mEtPasswordAgainContent)) {
                    Toast("两次新密码输入不一致");
                    return;
                }
                showMyDialog();
                ChangePasswordBody body = new ChangePasswordBody(getCurrentUserMobile(), mEtPasswordContent);
                body.setOldPassword(mEtOldPasswordContent);
                Call<BaseResponse<String>> call = userService.changePassword(body);
                call.enqueue(new BaseCallback<String>() {
                    @Override
                    public void onFinally() {
                        dismissMyDialog();
                    }

                    @Override
                    public void onNetFailure(Throwable t) {
                        Toast(BreakfastConstant.NO_NET_MESSAGE);
                    }

                    @Override
                    public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        Toast("密码修改成功");
                        finish();
                    }

                    @Override
                    public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        Toast(response.body().getMessage());

                    }
                });
                break;

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }
}
