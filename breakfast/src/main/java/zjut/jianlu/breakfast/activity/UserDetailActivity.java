package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;

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

    private String[] datas = {"111111111111","22222","33333","444444","5555"};


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_detatil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRatingbar.setStarHalfDrawable(getResources().getDrawable(R.mipmap.ic_half_star));
        mRatingbar.setStar(2.5f);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        mLvComment.setAdapter(adapter);
    }

    @OnClick(R.id.iv_user_image)
    public void onClick() {
    }
}
