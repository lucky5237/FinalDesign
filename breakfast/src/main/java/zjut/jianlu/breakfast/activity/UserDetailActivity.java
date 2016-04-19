package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

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
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderComment;
import zjut.jianlu.breakfast.entity.bean.UserInfo;
import zjut.jianlu.breakfast.entity.requestBody.UserDetailBody;
import zjut.jianlu.breakfast.service.UserService;

/**
 * Created by jianlu on 4/18/2016.
 */
public class UserDetailActivity extends BaseActivity {
    @Bind(zjut.jianlu.breakfast.R.id.iv_back)
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

    private UserInfo userInfo;

    private CommentDetailAdapter adapter;

    private Retrofit retrofit;

    private UserService service;

    private Integer userType;

    private Integer userId;

    private List<OrderComment> mOrderCommentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_detatil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderCommentList = new ArrayList<OrderComment>();
        adapter = new CommentDetailAdapter(mContext, mOrderCommentList, userType);
        mRatingbar.setStarHalfDrawable(getResources().getDrawable(R.mipmap.ic_half_star));
        getUserInfo();

    }

    private void getUserInfo() {

        Call<BaseResponse<UserInfo>> call = service.getUserInfo(new UserDetailBody(userId,userType,null));
        call.enqueue(new BaseCallback<UserInfo>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<UserInfo>> call, Response<BaseResponse<UserInfo>> response) {

                userInfo = response.body().getData();
                setData();

            }

            @Override
            public void onBizFailure(Call<BaseResponse<UserInfo>> call, Response<BaseResponse<UserInfo>> response) {

            }
        });
    }

    private void setData() {
        mTvUserName.setText(userInfo.getUserName());
        mTvBonus.setText(String.valueOf(userInfo.getBonus()));
        mTvOrderNum.setText(userInfo.getOrderNum());
        mTvScore.setText(String.valueOf(userInfo.getAveScore()));
        mRatingbar.setStar(userInfo.getAveScore());
    }

    @OnClick(R.id.iv_user_image)
    public void onClick() {
    }



}
