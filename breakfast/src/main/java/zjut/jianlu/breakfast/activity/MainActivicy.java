package zjut.jianlu.breakfast.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.entity.db.ShoppingCartDB;
import zjut.jianlu.breakfast.entity.event.ChangeIndexEvent;
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
    private ShopCartFragment mShopCartFragment;
    private FragmentManager mTransitionManager;
    public static final int HOME_INDEX = 0;
    public static final int RANK_INDEX = 3;
    public static final int ORDER_INDEX = 1;
    public static final int CART_INDEX = 2;
    @Bind(R.id.tv_topbar)
    TextView mTvTopBar;
    private MyBadgeView badgeView;
    @Bind(R.id.btn_cart)
    RadioButton mRbtnCart;
    @Bind(R.id.main_cotainer)
    LinearLayout mLlytMainContainer;

    public LinearLayout getmLlytMainContainer() {
        return mLlytMainContainer;
    }

    private MenuDrawer menuDrawer;

    private static MainActivicy instance;

    private String[] data = {"menu1", "menu2", "menu3", "menu4"};

    private FragmentTransaction mTransaction;
    private Fragment[] fragments;

    @OnClick({R.id.btn_home, R.id.btn_rank, R.id.btn_order, R.id.btn_cart})
    public void onclick(View view) {
        FragmentTransaction transation = mTransitionManager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_home:
                if (getCurrentUserType() == 0) {
                    mTvTopBar.setText("美食广场");

                } else {
                    mTvTopBar.setText("最新订单");

                }
                showFragment(HOME_INDEX, transation);
                break;
            case R.id.btn_rank:
                mTvTopBar.setText("热门榜单");
                showFragment(RANK_INDEX, transation);
                break;
            case R.id.btn_order:
                mTvTopBar.setText("我的订单");
                showFragment(ORDER_INDEX, transation);
                break;
            case R.id.btn_cart:
                mTvTopBar.setText("购物车");
                showFragment(CART_INDEX, transation);
                break;
            default:
                break;
        }
        transation.commitAllowingStateLoss();


    }

    private void onTabSelected(int index) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mCurrentIndex = index;
        showFragment(mCurrentIndex, transaction);
        transaction.commitAllowingStateLoss();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showFragment(int index, FragmentTransaction transaction) {

        mCurrentIndex = index;
        switch (index) {
            case HOME_INDEX:
                if (null != mRankFragment) {
                    transaction.hide(mRankFragment);
                }
                if (null != mOrderFragment) {
                    transaction.hide(mOrderFragment);
                }
                if (null != mShopCartFragment) {
                    transaction.hide(mShopCartFragment);
                }
                if (null == mHomePageFragment) {
                    if (getCurrentUserType() == 0) {
                        mHomePageFragment = new ClientHomePageFragment();
                    }
                    if (getCurrentUserType() == 1) {
                        mHomePageFragment = new CourierHomePageFragment();
                    }
                    transaction.add(R.id.flyt_container, mHomePageFragment);
                } else {
                    transaction.show(mHomePageFragment);
                }
                break;
            case ORDER_INDEX:
                if (null != mRankFragment) {
                    transaction.hide(mRankFragment);
                }
                if (null != mHomePageFragment) {
                    transaction.hide(mHomePageFragment);
                }
                if (null != mShopCartFragment) {
                    transaction.hide(mShopCartFragment);
                }
                if (null == mOrderFragment) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.flyt_container, mOrderFragment);
                } else {
                    transaction.show(mOrderFragment);
                }
                break;
            case RANK_INDEX:
                if (null != mHomePageFragment) {
                    transaction.hide(mHomePageFragment);
                }
                if (null != mOrderFragment) {
                    transaction.hide(mOrderFragment);
                }
                if (null != mShopCartFragment) {
                    transaction.hide(mShopCartFragment);
                }
                if (null == mRankFragment) {
                    mRankFragment = new RankFragment();
                    transaction.add(R.id.flyt_container, mRankFragment);
                } else {
                    transaction.show(mRankFragment);
                }
                break;
            case CART_INDEX:
                if (null != mRankFragment) {
                    transaction.hide(mRankFragment);
                }
                if (null != mOrderFragment) {
                    transaction.hide(mOrderFragment);
                }
                if (null != mHomePageFragment) {
                    transaction.hide(mHomePageFragment);
                }
                if (null == mShopCartFragment) {
                    mShopCartFragment = new ShopCartFragment();
                    transaction.add(R.id.flyt_container, mShopCartFragment);
                } else {
                    transaction.show(mShopCartFragment);
                }
                break;

        }
    }


    public static MainActivicy getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        EventBus.getDefault().register(this);
        mTransitionManager = getSupportFragmentManager();
        onTabSelected(mCurrentIndex);
        badgeView = new MyBadgeView(this);
        badgeView.setTargetView(mRbtnCart);
        badgeView.setBadgeCount(getBadgeViewCount());

//        menuDrawer=MenuDrawer.attach(this);
//        menuDrawer.setMenuView(R.layout.fragment_menu);
//        menuDrawer.setContentView(R.layout.activity_main);
//        menuDrawer.peekDrawer();
//        menuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
//            @Override
//            public void onDrawerStateChange(int oldState, int newState) {
//
//            }
//
//            @Override
//            public void onDrawerSlide(float openRatio, int offsetPixels) {
//
//            }
//        });
//        mLeftListItem.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
//        mLeftListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast(data[position]);
//                mDlytMain.closeDrawer(mLeftListItem);
//            }
//        });
//        mDlytMain.setDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                    Toast("侧栏打开");
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                Toast("侧栏关闭");
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        });


//        


    }

    private int getBadgeViewCount() {
        // TODO: 16/4/15 购物车的数目 
        return (int) ShoppingCartDB.count(ShoppingCartDB.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void changeIndex(ChangeIndexEvent event) {
        Integer index = event.getIndex();
        if (index != null) {
            FragmentTransaction transaction = mTransitionManager.beginTransaction();
            if (index == ORDER_INDEX) {
                if (mOrderFragment != null) {
                    mOrderFragment.getMyorder();
                }
            }
            showFragment(index, transaction);
            transaction.commitAllowingStateLoss();
        }

    }

    @Subscribe
    public void updateBadgeView(UpdateBadgeNumEvent event) {
        if (badgeView != null) {
            int newNumber = badgeView.getBadgeCount() + event.getNum();
            badgeView.setBadgeCount(newNumber < 0 ? 0 : newNumber);
        }
        if (mShopCartFragment == null) {
            mShopCartFragment = new ShopCartFragment();
        }
        mShopCartFragment.initData();

        mShopCartFragment.adapter.notifyDataSetChanged();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
