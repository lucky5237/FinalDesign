package zjut.jianlu.breakfast.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.entity.bean.Place;
import zjut.jianlu.breakfast.entity.event.UpdateBadgeNumEvent;
import zjut.jianlu.breakfast.fragment.OrderFragment;
import zjut.jianlu.breakfast.fragment.ShopCartFragment;
import zjut.jianlu.breakfast.fragment.home.ClientHomePageFragment;
import zjut.jianlu.breakfast.fragment.home.CourierHomePageFragment;
import zjut.jianlu.breakfast.fragment.rank.RankFragment;
import zjut.jianlu.breakfast.widget.MyBadgeView;

/**
 * Created by jianlu on 16/3/12.
 */
public class MainActivicy extends BaseActivity {

    private int mCurrentIndex = 0; //当前选中的索引
    private Fragment mHomePageFragment;
    private RankFragment mRankFragment;
    private OrderFragment mOrderFragment;
    private ShopCartFragment mMeFragment;
    public static final int HOCART_INDEX = 0;
    public static final int RANK_INDEX = 3;
    public static final int ORDER_INDEX = 1;
    public static final int CART_INDEX = 2;
    @Bind(R.id.tv_topbar)
    TextView mTvTopBar;
    private MyBadgeView badgeView;
    @Bind(R.id.btn_cart)
    RadioButton mRbtnCart;


    private FragmentTransaction mTransaction;
    private Fragment[] fragments;

    @OnClick({R.id.btn_home, R.id.btn_rank, R.id.btn_order, R.id.btn_cart})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                if (getCurrentUserType() == 0) {
                    mTvTopBar.setText("美食广场");

                } else {
                    mTvTopBar.setText("最新订单");

                }
                showFragment(HOCART_INDEX);
                break;
            case R.id.btn_rank:
                mTvTopBar.setText("热门榜单");
                showFragment(RANK_INDEX);
                break;
            case R.id.btn_order:
                mTvTopBar.setText("我的订单");
                showFragment(ORDER_INDEX);
                break;
            case R.id.btn_cart:
                mTvTopBar.setText("购物车");
                showFragment(CART_INDEX);
                break;
            default:
                break;
        }


    }

    private void showFragment(int index) {
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
            badgeView = new MyBadgeView(mContext);
            badgeView.setTargetView(mRbtnCart);
            badgeView.setBadgeCount(5);
        }
        if (getCurrentUserType() == 1) {
            mHomePageFragment = new CourierHomePageFragment();
        }
        mRankFragment = new RankFragment();
        mOrderFragment = new OrderFragment();
        mMeFragment = new ShopCartFragment();
        fragments = new Fragment[]{mHomePageFragment, mRankFragment, mOrderFragment, mMeFragment};
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.flyt_container, mHomePageFragment)
                .add(R.id.flyt_container, mRankFragment).add(R.id.flyt_container, mOrderFragment)
                .add(R.id.flyt_container, mMeFragment).hide(mRankFragment).hide(mOrderFragment).hide(mMeFragment).show(mHomePageFragment).commit();
        for (int i = 0; i < 4; i++) {
            Place place = new Place("no." + i,i);
            place.setId(Long.valueOf(i));
            place.save();
        }
        Place p = new Place("ext", 5);
        p.setId(3l);
        p.save();




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
    public void changeIndex(Integer index) {
        if (index != null) {
            showFragment(index);
        }
    }

    @Subscribe
    public void updateBadgeView(UpdateBadgeNumEvent event) {
        if (badgeView != null) {
            int newNumber = badgeView.getBadgeCount() + event.getNum();
            badgeView.setBadgeCount(newNumber < 0 ? 0 : newNumber);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
