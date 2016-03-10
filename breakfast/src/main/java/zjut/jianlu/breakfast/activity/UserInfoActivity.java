package zjut.jianlu.breakfast.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.User;

/**
 * Created by jianlu on 3/10/2016.
 */
public class UserInfoActivity extends BaseActivity {

    private AlertDialog mGenderDialog;
    private AlertDialog mTypeDialog;
    private int mGenderSelected = -1;
    private int mTypeSelected = -1;
    private String[] genders;
    private String[] types;
    private String mobile;
    private String password;
    private String address;
    private String userName;


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.sex_input)
    Button sexInput;
    @Bind(R.id.gender_ImageButton)
    ImageButton genderImageButton;
    @Bind(R.id.type_input)
    Button typeInput;
    @Bind(R.id.type_ImageButton)
    ImageButton typeImageButton;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.address_Linear)
    LinearLayout mLlAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        genders=getResources().getStringArray(R.array.gender);
        types=getResources().getStringArray(R.array.type);
        initView();
        Intent intent = getIntent();
        if (intent!=null){
            mobile=intent.getStringExtra(BreakfastConstant.MOBILE_TAG);
            password=intent.getStringExtra(BreakfastConstant.PASSWORD_TAG);
        }
    }

    private void initView() {
        mGenderDialog = new AlertDialog.Builder(UserInfoActivity.this).setAdapter(new ArrayAdapter<String>(UserInfoActivity.this, R.layout.adapter_dialog_item, genders), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sexInput.setText(genders[which]);
                sexInput.setTextColor(getResources().getColor(R.color.color_black));
                mGenderSelected=which;
                mGenderDialog.dismiss();
            }
        }).create();
        mGenderDialog.setCancelable(true);
        mGenderDialog.setCanceledOnTouchOutside(true);

        mTypeDialog = new AlertDialog.Builder(UserInfoActivity.this).setAdapter(new ArrayAdapter<String>(UserInfoActivity.this, R.layout.adapter_dialog_item, types), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeInput.setText(types[which]);
                typeInput.setTextColor(getResources().getColor(R.color.color_black));
                mTypeSelected=which;
                mTypeDialog.dismiss();
                if (which==0){
                    mLlAddress.setVisibility(View.GONE);
                }else{
                    mLlAddress.setVisibility(View.VISIBLE);
                }
            }
        }).create();
        mTypeDialog.setCancelable(true);
        mTypeDialog.setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.sex_input, R.id.gender_ImageButton, R.id.type_input, R.id.type_ImageButton, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.sex_input:
            case R.id.gender_ImageButton:
                mGenderDialog.show();

                break;
            case R.id.type_input:
            case R.id.type_ImageButton:
                mTypeDialog.show();
                break;
            case R.id.btn_next:
                checkInput();
//                showToast("点击下一个按钮");
                break;
        }
    }

    private void checkInput() {
        userName=etUsername.getText().toString().trim();
        address=etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            showToast("用户名不能为空");
            return;
        }
        if (mGenderSelected == -1){
            showToast("请先选择你的性别");
            return;
        }
        if (mTypeSelected == -1){
            showToast("请先选择你的用户类型");
            return;
        }
        if (mTypeSelected == 1){
            if (TextUtils.isEmpty(address)){
                showToast("请输入你的收货地址");
                return;
            }
        }
        saveUserInfo();
    }

    private void saveUserInfo() {
        User user=new User();
        user.setGender(mGenderSelected);
        user.setType(mTypeSelected);
        user.setAddress(address);
        user.setUsername(userName);
        user.setMobilePhoneNumber(mobile);
        user.setPassword(password);
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("个人信息保存成功");
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("个人信息保存失败");
                Log.d("jianlu","i is "+i+" s is "+s);
            }
        });



    }
}
