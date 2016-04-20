package zjut.jianlu.breakfast.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.requestBody.MakeCommentBody;
import zjut.jianlu.breakfast.service.OrderService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.utils.LogUtil;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 4/18/2016.
 */
public class CommentActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_user_image)
    CircleImageView mIvUserImage;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.ratingbar)
    RatingBar mRatingbar;
    @Bind(R.id.et_comment)
    EditText mEtComment;
    @Bind(R.id.btn_commit)
    Button mBtnCommit;
    @Bind(R.id.tv_score_des)
    TextView mTvScoreDes;
    @Bind(R.id.llyt_main)
    LinearLayout mLlytMain;

    private int mCurrentScore = 0;

    private Integer orderId = 0;

    private static final String[] SCORE_DES = {"1分，差劲", "2分，一般", "3分，还行", "4分，不错", "5分，完美"};

    private OrderService service;

    private Retrofit retrofit;

    private String userName;

    private Long userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = MyApplication.getRetrofitInstance();
        service = retrofit.create(OrderService.class);
        orderId = getIntent().getIntExtra("orderId", 0);
        userName = getIntent().getStringExtra("otherUserName");
        userId = getIntent().getLongExtra("otherUserId", 0);
        mTvUserName.setText(userName == null ? "" : userName);
        final String mAvatarUrl = BreakfastUtils.getAbsAvatarUrlPath(userName);
        Picasso.with(mContext).load(mAvatarUrl).into(mIvUserImage);
        mIvUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, mAvatarUrl);
                if (!popWindow.isShowing()) {
                    popWindow.showAtLocation(mLlytMain, Gravity.TOP, 0, 0);
                }
            }
        });
        mRatingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                mTvScoreDes.setVisibility(View.VISIBLE);
                mTvScoreDes.setText(SCORE_DES[RatingCount - 1]);
                mCurrentScore = RatingCount;
            }
        });
//        mRatingbar.setStarHalfDrawable(getResources().getDrawable(R.mipmap.ic_half_star));
//        mRatingbar.setStar(2.5f);

    }

    private void makeComment() {
        MakeCommentBody body = new MakeCommentBody(getCurrentUserID(), getCurrentUserType(), mCurrentScore, mEtComment.getText().toString(), orderId, getCurrentUser().getUsername(), userId.intValue(), userName);
        Call<BaseResponse<String>> call = service.comment(body);
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                finish();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                LogUtil.d(response.body().getMessage());
            }
        });

    }

    @OnClick({R.id.btn_commit, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_commit:
                if (mCurrentScore == 0) {
                    Toast("请先为该用户评分");
                    return;
                }
                if (TextUtils.isEmpty(mEtComment.getText().toString())) {
                    Toast("请先填写评论");
                    return;
                }
                makeComment();
                break;
        }

    }


}
