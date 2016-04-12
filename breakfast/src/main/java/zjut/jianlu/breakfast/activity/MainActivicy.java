package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.fragment.MeFragment;
import zjut.jianlu.breakfast.fragment.OrderFragment;
import zjut.jianlu.breakfast.fragment.home.ClientHomePageFragment;
import zjut.jianlu.breakfast.fragment.home.CourierHomePageFragment;
import zjut.jianlu.breakfast.fragment.rank.RankFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class MainActivicy extends BaseActivity {

    private int mCurrentIndex = 0;//当前选中的索引
    private CourierHomePageFragment mCourierHomePageFragment;
    private ClientHomePageFragment mClientHomePageFragment;
    private Fragment mHomePageFragment;
    private RankFragment mRankFragment;
    private OrderFragment mOrderFragment;
    private MeFragment mMeFragment;
    public static final int HOME_INDEX = 0;
    public static final int RANK_INDEX = 1;
    public static final int ORDER_INDEX = 2;
    public static final int ME_INDEX = 3;
    @Bind(R.id.tv_topbar)
    TextView mTvTopBar;


    private FragmentTransaction mTransaction;
    private Fragment[] fragments;

    @OnClick({R.id.btn_home, R.id.btn_rank, R.id.btn_order, R.id.btn_me})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                mTvTopBar.setText("实时订单");
                ShowFragment(HOME_INDEX);
                break;
            case R.id.btn_rank:
                mTvTopBar.setText("热门榜单");
                ShowFragment(RANK_INDEX);
                break;
            case R.id.btn_order:
                mTvTopBar.setText("我的订单");
                ShowFragment(ORDER_INDEX);
                break;
            case R.id.btn_me:
                mTvTopBar.setText("个人设置");
                ShowFragment(ME_INDEX);
                break;
            default:
                break;
        }


    }

    private void ShowFragment(int index) {
        if (index == mCurrentIndex) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(fragments[mCurrentIndex]);

        mCurrentIndex = index;

        transaction.show(fragments[index]).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getCurrentUserType() == 0) {
            mHomePageFragment = new ClientHomePageFragment();
        }
        if (getCurrentUserType() == 1){
            mHomePageFragment = new CourierHomePageFragment();
        }
        mRankFragment = new RankFragment();
        mOrderFragment = new OrderFragment();
        mMeFragment = new MeFragment();
        fragments = new Fragment[]{mHomePageFragment, mRankFragment, mOrderFragment, mMeFragment};
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.flyt_container, mHomePageFragment)
                .add(R.id.flyt_container, mRankFragment).add(R.id.flyt_container, mOrderFragment)
                .add(R.id.flyt_container, mMeFragment).hide(mRankFragment).hide(mOrderFragment).hide(mMeFragment).show(mHomePageFragment).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void ChangeIndex(Integer index) {
        if (index != null) {
            ShowFragment(index);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
