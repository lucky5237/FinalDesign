package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.fragment.HomePageFragment;
import zjut.jianlu.breakfast.fragment.MeFragment;
import zjut.jianlu.breakfast.fragment.OrderFragment;
import zjut.jianlu.breakfast.fragment.RankFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class MainActivicy extends BaseActivity {

    private int mCurrentIndex = 0;//当前选中的索引
    private HomePageFragment mHomePageFragment;
    private RankFragment mRankFragment;
    private OrderFragment mOrderFragment;
    private MeFragment mMeFragment;
    private static final int HOME_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final int ORDER_INDEX = 2;
    private static final int ME_INDEX = 3;
    @Bind(R.id.tv_topbar)
    TextView mTvTopBar;
    @Bind(R.id.iv_plus)
    ImageView mIvPlus;

    private FragmentTransaction mTransaction;
    private Fragment[] fragments;

    @OnClick({R.id.btn_home, R.id.btn_rank, R.id.btn_order, R.id.btn_me,R.id.iv_plus})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                mTvTopBar.setText("实时订单");
                mIvPlus.setVisibility(View.VISIBLE);
                ShowFragment(HOME_INDEX);
                break;
            case R.id.btn_rank:
                mTvTopBar.setText("热门榜单");
                mIvPlus.setVisibility(View.GONE);
                ShowFragment(RANK_INDEX);
                break;
            case R.id.btn_order:
                mTvTopBar.setText("我的订单");
                mIvPlus.setVisibility(View.GONE);
                ShowFragment(ORDER_INDEX);
                break;
            case R.id.btn_me:
                mTvTopBar.setText("个人设置");
                mIvPlus.setVisibility(View.GONE);
                ShowFragment(ME_INDEX);
                break;
            case R.id.iv_plus:
                startActivity(new Intent(MainActivicy.this,FoodShopActivity.class));
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

        mCurrentIndex=index;

        transaction.show(fragments[index]).commit();
        }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePageFragment = new HomePageFragment();
        mRankFragment = new RankFragment();
        mOrderFragment = new OrderFragment();
        mMeFragment = new MeFragment();
        fragments=new Fragment[]{mHomePageFragment,mRankFragment,mOrderFragment,mMeFragment};
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.flyt_container, mHomePageFragment)
                .add(R.id.flyt_container, mRankFragment).add(R.id.flyt_container, mOrderFragment)
                .add(R.id.flyt_container, mMeFragment).hide(mRankFragment).hide(mOrderFragment).hide(mMeFragment).show(mHomePageFragment).commit();


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
