package zjut.jianlu.breakfast.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.requestBody.RegisterBody;
import zjut.jianlu.breakfast.listener.OnWheelScrollListener;
import zjut.jianlu.breakfast.service.UserService;
import zjut.jianlu.breakfast.utils.CropImageUtil;
import zjut.jianlu.breakfast.utils.PictureUtil;
import zjut.jianlu.breakfast.widget.ActionSheetDialog;
import zjut.jianlu.breakfast.widget.ActionSheetDialog.OnSheetItemClickListener;
import zjut.jianlu.breakfast.widget.ActionSheetDialog.SheetItemColor;
import zjut.jianlu.breakfast.widget.WheelView;

/**
 * Created by jianlu on 3/10/2016.
 */
public class UserInfoActivity extends BaseActivity implements OnWheelScrollListener {

    private AlertDialog mGenderDialog;
    private AlertDialog mTypeDialog;
    private int mGenderSelected = -1;
    private int mTypeSelected = -1;
    private String[] genders;
    private String[] types;
    private String mobile;
    private String password;
    private String address;
    private static String userName;
    public static File tempFile = new File(Environment.getExternalStorageDirectory(), userName + ".jpg");
    private String mCurrentPhotoPath;
    private String mCurrentSmallPhotoPath;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String mAddress;
    private static final int LOCATION_RESULT_TAG = 1;
    private UserService userService;
    private Retrofit retrofit;

    @Override
    public void onScrollingStarted(WheelView wheel) {

    }

    @Override
    public void onScrollingFinished(WheelView wheel) {

    }

    private class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOCATION_RESULT_TAG:
                    address = (String) msg.obj;
                    etAddress.setText(address);
            }

        }
    }


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
    @Bind(R.id.iv_location)
    ImageView mLv_location;
    @Bind(R.id.avatar_Linear)
    LinearLayout mLlAvatar;
    @Bind(R.id.avatar_ImageButton)
    ImageButton avaterImageButton;
    @Bind(R.id.avatar_input)
    Button avatarInput;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.rlyt_avatar)
    RelativeLayout mRlAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_info);
//        ButterKnife.bind(this);
        genders = getResources().getStringArray(R.array.gender);
        types = getResources().getStringArray(R.array.type);
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra(BreakfastConstant.MOBILE_TAG);
            password = intent.getStringExtra(BreakfastConstant.PASSWORD_TAG);
        }
        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);
//        mLocationClient.start();
    }


    private void uploadAvatar() {
        File file = new File(mCurrentPhotoPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", etUsername.getText().toString().trim() + ".jpg", requestBody);
        Call<BaseResponse<String>> call = userService.uploadImage(body);
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                startActivity(new Intent(mContext, LoginActivity.class));

            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                startActivity(new Intent(mContext, LoginActivity.class));

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    private void initLoctaion() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void initView() {
        mGenderDialog = new AlertDialog.Builder(UserInfoActivity.this).setAdapter(new ArrayAdapter<String>(UserInfoActivity.this, R.layout.adapter_dialog_item, genders), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sexInput.setText(genders[which]);
                sexInput.setTextColor(getResources().getColor(R.color.color_black));
                mGenderSelected = which;
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
                mTypeSelected = which;
                mTypeDialog.dismiss();
                if (which == 1) {
                    mLlAddress.setVisibility(View.GONE);
                } else {
                    mLlAddress.setVisibility(View.VISIBLE);
                }
            }
        }).create();
        mTypeDialog.setCancelable(true);
        mTypeDialog.setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.sex_input, R.id.gender_ImageButton, R.id.type_input, R.id.type_ImageButton, R.id.btn_next, R.id.iv_location, R.id.iv_back, R.id.avatar_input, R.id.avatar_ImageButton})
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

            case R.id.avatar_input:
            case R.id.avatar_ImageButton:
                showSelectPicture();

                break;
            case R.id.btn_next:
                checkInput();
//                Toast("点击下一个按钮");
                break;
            case R.id.iv_location:
                if (mLocationClient == null) {
                    mLocationClient = new LocationClient(getApplicationContext());
                    mLocationClient.registerLocationListener(myListener);
                    initLoctaion();
                    mLocationClient.start();
                } else {
                    if (mLocationClient.isStarted()) {
                        mLocationClient.stop();
                    }
                    mLocationClient.start();
                }


                break;
        }
    }

    private void checkInput() {
        userName = etUsername.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast("用户名不能为空");
            return;
        }
        if (mCurrentPhotoPath == null) {
            Toast("请先选择你的头像");
            return;
        }
        if (mGenderSelected == -1) {
            Toast("请先选择你的性别");
            return;
        }
        if (mTypeSelected == -1) {
            Toast("请先选择你的用户类型");
            return;
        }
        if (mTypeSelected == 1) {
            if (TextUtils.isEmpty(address)) {
                Toast("请输入你的收货地址");
                return;
            }
        }

        if (mLocationClient!=null&&mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        saveUserInfo();
    }

    private void saveUserInfo() {
        Call<BaseResponse<String>> call = userService.register(new RegisterBody(mobile, password, userName, mGenderSelected, mTypeSelected, TextUtils.isEmpty(address) ? "" : address, ""));
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                uploadAvatar();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
            }
        });
    }

    private void showSelectPicture() {
        tempFile = new File(Environment.getExternalStorageDirectory(), userName + ".jpg");
        if (mCurrentPhotoPath == null) {
            new ActionSheetDialog(mContext).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                    .addSheetItem("拍照", SheetItemColor.GRAY, new ActionSheetDialog.OnSheetItemClickListener() {

                        @Override
                        public void onClick(int which) {
                            takePhoto();
                        }
                    }).addSheetItem("从手机相册选择", SheetItemColor.GRAY, new OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    startActivityForResult(CropImageUtil.gallery(),
                            BreakfastConstant.REQUEST_CODE_OPEN_GALLERY);
                }
            }).show();
        } else {
            new ActionSheetDialog(mContext).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                    .addSheetItem("查看大图", SheetItemColor.GRAY, new OnSheetItemClickListener() {

                        @Override
                        public void onClick(int which) {
//                            Intent intent = new Intent(mContext, PictureFullScreenActivity.class);
//                            intent.putExtra(BreakfastConstant.INTENT_EXTRA_OBJECT,
//                                    mCurrentSmallPhotoPath == null ? mCurrentPhotoPath : mCurrentSmallPhotoPath);
//                            mContext.startActivity(intent);
//                            mContext.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                        }
                    }).addSheetItem("拍照", SheetItemColor.GRAY, new OnSheetItemClickListener() {

                @Override
                public void onClick(int which) {
                    takePhoto();
                }
            }).addSheetItem("从手机相册选择", SheetItemColor.GRAY, new OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    startActivityForResult(CropImageUtil.gallery(),
                            BreakfastConstant.REQUEST_CODE_OPEN_GALLERY);
                }
            }).show();
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            // 指定存放拍摄照片的位置
            File f = createImageFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(takePictureIntent, BreakfastConstant.REQUEST_CODE_OPEN_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = format.format(new Date());
        String imageFileName = "breakfast_" + timeStamp + ".jpg";

        File image = new File(PictureUtil.getAlbumDir(), imageFileName);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                if (!TextUtils.isEmpty(location.getAddrStr())) {
                    Toast("定位成功");
                    Message message = Message.obtain();
                    message.obj = location.getAddrStr().substring(2);//去掉中国
                    message.what = LOCATION_RESULT_TAG;
                    new mHandler().sendMessage(message);
                }
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else {
                Toast("网络异常,请先打开网络连接");
                if (mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
            }

            Log.d("jianlu", sb.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == BreakfastConstant.REQUEST_CODE_OPEN_CAMERA) {
            if (resultCode != Activity.RESULT_OK) {
                PictureUtil.deleteTempFile(mCurrentPhotoPath);
                return;
            }
            // 添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
            PictureUtil.galleryAddPic(mContext, mCurrentPhotoPath);

            ivAvatar.setImageBitmap(PictureUtil.getSmallBitmap(mCurrentPhotoPath));
//            Picasso.display(mIbtnPhoto, mCurrentPhotoPath);
//            Picasso.with(mContext).load(mCurrentPhotoPath).into(ivAvatar);
            mRlAvatar.setVisibility(View.VISIBLE);
            avatarInput.setVisibility(View.GONE);
            save();
        } else if (requestCode == BreakfastConstant.REQUEST_CODE_OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent.getData() != null) {
                    Uri selectedImage = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = mContext.getContentResolver()
                            .query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mCurrentPhotoPath = cursor.getString(columnIndex);
                    mCurrentSmallPhotoPath = null;
                    cursor.close();
                    BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
                    opts.inSampleSize = 2;
                    Bitmap bitmap = CropImageUtil.rotateBitmap(CropImageUtil.readPicDegree(mCurrentPhotoPath),
                            BitmapFactory.decodeFile(mCurrentPhotoPath, opts));
                    ivAvatar.setImageBitmap(bitmap);
                    mRlAvatar.setVisibility(View.VISIBLE);
                    avatarInput.setVisibility(View.GONE);
                    // mBtnTakePhoto.setVisibility(View.GONE);
                }
            }
        }
    }

    private void save() {
        if (mCurrentPhotoPath != null) {
            try {
                File f = new File(mCurrentPhotoPath);
                Bitmap bm = PictureUtil.getSmallBitmap(mCurrentPhotoPath);
                File file = new File(PictureUtil.getAlbumDir(), "small_" + f.getName());
                mCurrentSmallPhotoPath = file.getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
            } catch (Exception e) {
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }

    }
}
