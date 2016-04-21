package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.CommentDetailAdapter;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderComment;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.requestBody.BaseUserBody;
import zjut.jianlu.breakfast.entity.requestBody.OrderCommentListBody;
import zjut.jianlu.breakfast.service.UserService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 4/18/2016.
 */
public class UserDetailActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_user_image)
    CircleImageView mIvUserImage;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.tv_order_num)
    TextView mTvOrderNum;
    @Bind(R.id.tv_bonus)
    TextView mTvBonus;
    @Bind(R.id.ratingbar)
    RatingBar mRatingbar;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.lv_comment)
    ListView mLvComment;
    @Bind(R.id.main_cotainer)
    LinearLayout mLlytMainContainer;

    private User user;

    private String mAvatarUrl;

    private CommentDetailAdapter adapter;

    private Retrofit retrofit;

    private UserService service;

    private Integer userType;

    private Integer userId;

    private Float mAveScore;

    private Integer number = null;// TODO: 16/4/19 返回的评论条数

    private List<OrderComment> mOrderCommentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_detatil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            userId = getIntent().getIntExtra("userId", -1);
            userType = getIntent().getIntExtra("userType", -1);

        }
        retrofit = MyApplication.getRetrofitInstance();
        service = retrofit.create(UserService.class);
        mOrderCommentList = new ArrayList<OrderComment>();
        adapter = new CommentDetailAdapter(mContext, mOrderCommentList, userType, mLlytMainContainer);
        mLvComment.setAdapter(adapter);
        mRatingbar.setStarHalfDrawable(getResources().getDrawable(R.mipmap.ic_half_star));
        mRatingbar.setmClickable(false);
        getUserInfo();

    }


    private void getUserInfo() {
        showMyDialog();
        Call<BaseResponse<User>> call = service.getUserInfo(userId);
        call.enqueue(new BaseCallback<User>() {
            @Override
            public void onFinally() {

            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                user = response.body().getData();
                setData();
                getUserAveScore();
                getUserCommentList();

//

            }

            @Override
            public void onBizFailure(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {

            }
        });
    }

    private void getUserCommentList() {

        OrderCommentListBody body = new OrderCommentListBody(userId, userType, number);

        Call<BaseResponse<List<OrderComment>>> call = service.getCommentList(body);
        call.enqueue(new BaseCallback<List<OrderComment>>() {
            @Override
            public void onFinally() {
                dismissMyDialog();
            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<OrderComment>>> call, Response<BaseResponse<List<OrderComment>>> response) {
                List<OrderComment> orderComments = response.body().getData();
                if (orderComments == null || orderComments.size() == 0) {
                    //暂无收到评论 显示view
                    Toast(response.body().getMessage());
                } else {
                    if (mOrderCommentList != null) {
                        mOrderCommentList.clear();
                    }
                    mOrderCommentList.addAll(orderComments);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<OrderComment>>> call, Response<BaseResponse<List<OrderComment>>> response) {

            }
        });


    }

    private void setData() {
        mTvUserName.setText(user.getUsername());
        mTvBonus.setText(String.valueOf(user.getBonus()));
        mTvOrderNum.setText(user.getOrderNum().toString());
        mAvatarUrl = BreakfastUtils.getAbsAvatarUrlPath(user.getUsername());
        Picasso.with(mContext).load(mAvatarUrl).into(mIvUserImage);
//        mTvScore.setText(String.valueOf(userInfo.getAveScore()));
//        mRatingbar.setStar(userInfo.getAveScore());
    }

    private void getUserAveScore() {

        Call<BaseResponse<Float>> call = service.getAveScore(new BaseUserBody(userId, userType));
        call.enqueue(new BaseCallback<Float>() {
            @Override
            public void onFinally() {

            }

            @Override
            public void onNetFailure(Throwable t) {

            }

            @Override
            public void onBizSuccess(Call<BaseResponse<Float>> call, Response<BaseResponse<Float>> response) {
                mAveScore = response.body().getData();
                mTvScore.setText(String.valueOf(mAveScore));
                mRatingbar.setStar(mAveScore); // TODO: 16/4/19 星星显示的规则

            }

            @Override
            public void onBizFailure(Call<BaseResponse<Float>> call, Response<BaseResponse<Float>> response) {
                // TODO: 16/4/19 未收到评分
                mTvScore.setText("暂无评分");
            }
        });


    }

    @OnClick({R.id.iv_user_image, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_user_image:
                if (!TextUtils.isEmpty(mAvatarUrl)) {
                    ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, mAvatarUrl);
                    if (!popWindow.isShowing()) {
                        popWindow.showAtLocation(mLlytMainContainer, Gravity.TOP, 0, 0);
                    }
                }

                break;
        }


    }


}
