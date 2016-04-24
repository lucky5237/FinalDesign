package zjut.jianlu.breakfast.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
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
import zjut.jianlu.breakfast.entity.db.UserDB;
import zjut.jianlu.breakfast.entity.requestBody.ModifyProfileBody;
import zjut.jianlu.breakfast.listener.OnWheelScrollListener;
import zjut.jianlu.breakfast.service.UserService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.utils.CropImageUtil;
import zjut.jianlu.breakfast.utils.PictureUtil;
import zjut.jianlu.breakfast.widget.ActionSheetDialog;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;
import zjut.jianlu.breakfast.widget.WheelView;

/**
 * Created by jianlu on 16/4/21.
 */
public class UserProfileActivity extends BaseActivity implements OnWheelScrollListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.btn_user_name)
    Button btnUserName;
    @Bind(R.id.btn_user_type)
    Button btnUserType;
    @Bind(R.id.btn_user_gender)
    Button btnUserGender;
    @Bind(R.id.iv_user_avatar)
    CircleImageView ivAvatar;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.iv_location)
    ImageView ivLocation;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.llyt_main)
    LinearLayout llytMain;
    @Bind(R.id.llyt_address)
    LinearLayout llytAddress;
    private int mGenderSelected = -1;
    private String address;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);
        btnUserName.setText(getCurrentUser().getUsername());
        btnUserType.setText(getCurrentUserType() == 0 ? "买家" : "送客");
        mGenderSelected = getCurrentUser().getGender();
        btnUserGender.setText(mGenderSelected == 1 ? "男" : "女");
        if (getCurrentUserType() == 0) {
            etAddress.setText(getCurrentUser().getAddress());
            etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        etAddress.setEnabled(false);
                    }
                }
            });
            llytAddress.setVisibility(View.VISIBLE);
        } else {
            llytAddress.setVisibility(View.GONE);
        }


        Picasso.with(this).load(BreakfastUtils.getAbsAvatarUrlPath(getCurrentUser().getUsername())).into(ivAvatar);

    }

    private void uploadAvatar() {
        showMyDialog();
        File file = new File(mCurrentPhotoPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar",
                btnUserName.getText().toString().trim() + ".jpg", requestBody);
        Call<BaseResponse<String>> call = userService.uploadImage(body);
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
                Toast("头像上传成功");
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    private void initLoctaion() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @OnClick({R.id.btn_next, R.id.iv_location, R.id.iv_back, R.id.ibtn_user_gender, R.id.ibtn_user_avatar,
            R.id.btn_user_gender, R.id.avatar_Linear, R.id.ibtn_user_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ibtn_user_gender:
            case R.id.btn_user_gender:
                showGenderDialog();
                break;

            case R.id.ibtn_user_avatar:
            case R.id.avatar_Linear:
                showSelectPicture();
                break;
            case R.id.btn_next:
                checkInput();
                break;
            case R.id.iv_location:
                showMyDialog();
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
            case R.id.ibtn_user_address:
                showAddressDialog();
        }
    }

    private void showAddressDialog() {
        new ActionSheetDialog(mContext).builder().setCancelable(true).setCanceledOnTouchOutside(true).addSheetItem("重新定位",
                ActionSheetDialog.SheetItemColor.GRAY, new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        showMyDialog();
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
                    }
                }).addSheetItem("编辑地址", ActionSheetDialog.SheetItemColor.GRAY,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        etAddress.setEnabled(true);
                        etAddress.setTextColor(Color.parseColor("#000000"));
                        etAddress.setSelection(etAddress.getText().length());
                    }
                })
                .show();
    }

    private void showGenderDialog() {
        new ActionSheetDialog(mContext).builder().setCancelable(true).setCanceledOnTouchOutside(true).addSheetItem("男",
                ActionSheetDialog.SheetItemColor.GRAY, new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        btnUserGender.setText("男");
                        mGenderSelected = 1;
                        btnUserGender.setTextColor(getResources().getColor(R.color.color_black));
                    }
                }).addSheetItem("女", ActionSheetDialog.SheetItemColor.GRAY,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        btnUserGender.setText("女");
                        mGenderSelected = 0;
                        btnUserGender.setTextColor(getResources().getColor(R.color.color_black));
                    }
                })
                .show();
    }

    private void checkInput() {
        address = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address) && getCurrentUserType() == 0) {
            Toast("收货地址不能为空");
            return;
        }

        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        saveUserInfo();
    }

    private void saveUserInfo() {
        showMyDialog();
        Call<BaseResponse<String>> call = userService.modifyProfile(new ModifyProfileBody(getCurrentUserID(), mGenderSelected, address));
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
                Toast(response.body().getMessage());
                UserDB userDB = UserDB.listAll(UserDB.class).get(0);
                if (getCurrentUserType() == 0) {
                    userDB.setAddress(address);
                }
                userDB.setGender(mGenderSelected);

                userDB.save();
                setResult(RESULT_OK);
                finish();

            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());

            }
        });

    }

    private void showSelectPicture() {
        new ActionSheetDialog(mContext).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                .addSheetItem("查看大图", ActionSheetDialog.SheetItemColor.GRAY,
                        new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                ScanPicPopWindow popWindow = null;
                                if (mCurrentPhotoPath == null) {
                                    // popWindow = new ScanPicPopWindow(UserProfileActivity.this,
                                    // getCurrentUser().getUsername());
                                    popWindow = new ScanPicPopWindow(UserProfileActivity.this,
                                            BreakfastUtils.getAbsAvatarUrlPath(getCurrentUser().getUsername()));
                                } else {
                                    popWindow = new ScanPicPopWindow(UserProfileActivity.this,
                                            new File(mCurrentPhotoPath));
                                }
                                if (!popWindow.isShowing()) {
                                    popWindow.showAtLocation(llytMain, Gravity.TOP, 0, 0);
                                }
                            }
                        })
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.GRAY,
                        new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                takePhoto();
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.GRAY,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                startActivityForResult(CropImageUtil.gallery(),
                                        BreakfastConstant.REQUEST_CODE_OPEN_GALLERY);
                            }
                        })
                .show();

    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
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
            // Receive Location
            StringBuffer sb = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                if (!TextUtils.isEmpty(location.getAddrStr())) {
                    Toast("定位成功");
                    Message message = Message.obtain();
                    message.obj = location.getAddrStr().substring(2);// 去掉中国
                    message.what = LOCATION_RESULT_TAG;
                    new mHandler().sendMessage(message);
                }
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
                if (mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
            }
            dismissMyDialog();
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

            save();
        } else if (requestCode == BreakfastConstant.REQUEST_CODE_OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent.getData() != null) {
                    Uri selectedImage = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null,
                            null);
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

                }
            }
        }

        uploadAvatar();
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
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }

    }
}
