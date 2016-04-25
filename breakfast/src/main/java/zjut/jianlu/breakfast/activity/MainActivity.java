package zjut.jianlu.breakfast.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.entity.db.ShoppingCartDB;
import zjut.jianlu.breakfast.entity.event.ChangeIndexEvent;
import zjut.jianlu.breakfast.entity.event.UpdateBadgeNumEvent;
import zjut.jianlu.breakfast.fragment.ShopCartFragment;
import zjut.jianlu.breakfast.fragment.home.ClientHomePageFragment;
import zjut.jianlu.breakfast.fragment.home.CourierHomePageFragment;
import zjut.jianlu.breakfast.fragment.order.MyOrderFragment;
import zjut.jianlu.breakfast.fragment.rank.RankFragment;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.utils.SharedPreferencesUtil;
import zjut.jianlu.breakfast.widget.MyAlertDialog;
import zjut.jianlu.breakfast.widget.MyBadgeView;

/**
 * Created by jianlu on 16/3/12.
 */
public class MainActivity extends BaseActivity {

    private int mCurrentIndex = 0; // 当前选中的索引
    private Fragment mHomePageFragment;
    private RankFragment mRankFragment;
    private MyOrderFragment mOrderFragment;
    private ShopCartFragment mShopCartFragment;
    private FragmentManager mTransitionManager;
    private MyBadgeView badgeView;
    private static final int MAINACTIVITY_REQUEST_CODE = 1;
    public static final int HOME_INDEX = 0;
    public static final int RANK_INDEX = 3;
    public static final int ORDER_INDEX = 1;
    public static final int CART_INDEX = 2;
    private long mExitTime;
    @Bind(R.id.tv_topbar)
    TextView mTvTopBar;
    @Bind(R.id.btn_cart)
    RadioButton mRbtnCart;
    @Bind(R.id.main_cotainer)
    LinearLayout mLlytMainContainer;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.iv_user_image)
    CircleImageView mIvAvatar;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.llyt_me)
    LinearLayout mLlytMe;
    @Bind(R.id.llyt_profile)
    LinearLayout mLlytProfile;
    @Bind(R.id.llyt_password)
    LinearLayout mLlytPassword;
    @Bind(R.id.llyt_logout)
    LinearLayout mLlytLogout;


    private MyAlertDialog dialog;

    public LinearLayout getmLlytMainContainer() {
        return mLlytMainContainer;
    }

    private static MainActivity instance;

    private String[] data = {"menu1", "menu2", "menu3", "menu4"};

    private FragmentTransaction mTransaction;
    private Fragment[] fragments;

    @OnClick({R.id.btn_home, R.id.btn_rank, R.id.btn_order, R.id.btn_cart, R.id.llyt_me, R.id.llyt_profile,
            R.id.llyt_password, R.id.llyt_logout, R.id.iv_user_image})
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
            case R.id.llyt_me:
            case R.id.iv_user_image:
                Intent intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("userId", getCurrentUserID());
                intent.putExtra("userType", getCurrentUserType());
                mContext.startActivity(intent);
                break;
            case R.id.llyt_profile:
                startActivityForResult(new Intent(MainActivity.this, UserProfileActivity.class), MAINACTIVITY_REQUEST_CODE);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.llyt_password:
                startActivity(new Intent(MainActivity.this, ChangePasswordActvity.class));
                mDrawerLayout.closeDrawers();
                ;
                break;
            case R.id.llyt_logout:
                dialog = new MyAlertDialog(MainActivity.this, "温馨提示", "是否确定要退出登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtil.getInstance(mContext).clear();
                        startActivity(new Intent(MainActivity.this, LoginOrRegisterActivity.class));
                        finish();
                    }
                });
                dialog.show();
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
                    mOrderFragment = new MyOrderFragment();
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

    public static MainActivity getInstance() {
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
        mTvUserName.setText(getCurrentUser().getUsername());
        Picasso.with(this).load(BreakfastUtils.getAbsAvatarUrlPath(getCurrentUser().getUsername())).into(mIvAvatar);
    }

    private int getBadgeViewCount() {
        // TODO: 16/4/15 购物车的数目
        return (int) ShoppingCartDB.count(ShoppingCartDB.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void changeIndex(ChangeIndexEvent event) {
        Integer index = event.getIndex();
        Integer status = event.getStatus();
        if (index != null) {
            FragmentTransaction transaction = mTransitionManager.beginTransaction();
            if (index == ORDER_INDEX) {//下单成功 跳转到订单fragment
                if (mOrderFragment != null) {
                    mOrderFragment.getMyAllOrderFragment().getMyorder();
                    mOrderFragment.getMyAllOrderFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部

                    if (status != null) {
                        switch (status) {
                            case 0://下单刷新待接单界面
                                mOrderFragment.getMyWaitReceiveFragment().getMyorder();
                                mOrderFragment.getMyWaitReceiveFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部
                                mOrderFragment.getMtabPageIndicator().setCurrentItem(1);
                                break;
                            case 1://接单代配送刷新
                                mOrderFragment.getMyWaitDeliveryFragment().getMyorder();
                                mOrderFragment.getMyWaitDeliveryFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部
                                mOrderFragment.getMtabPageIndicator().setCurrentItem(1);
                                break;
                            case 2://开始配送
                                mOrderFragment.getMyWaitDeliveryFragment().getMyorder();
                                mOrderFragment.getMyWaitConfirmFragment().getMyorder();
                                mOrderFragment.getMyWaitConfirmFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部
                                mOrderFragment.getMtabPageIndicator().setCurrentItem(2);

                                break;
                            case 3://确认收货
                                mOrderFragment.getMyWaitConfirmFragment().getMyorder();
                                mOrderFragment.getMyFinishOrderFragment().getMyorder();
                                mOrderFragment.getMyFinishOrderFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部
                                mOrderFragment.getMtabPageIndicator().setCurrentItem(4);

                                break;
                            case 4://取消订单
                                if (mOrderFragment.getMyWaitReceiveFragment() != null) {
                                    mOrderFragment.getMyWaitReceiveFragment().getMyorder();
                                }
                                mOrderFragment.getMyWaitDeliveryFragment().getMyorder();
                                mOrderFragment.getMyCancelOrderFragment().getMyorder();
                                mOrderFragment.getMyCancelOrderFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部
                                mOrderFragment.getMtabPageIndicator().setCurrentItem(mOrderFragment.getmFragmentList().size() - 1);

                                break;
                            case -1://并不存在的状态，自己定义评论完更新
                                if (mOrderFragment.getMyFinishOrderFragment()!=null){
                                    mOrderFragment.getMyFinishOrderFragment().getMyorder();
                                }
// TODO: 4/25/2016 跳转逻辑优化 

                        }
                    }
//                    mOrderFragment.getMyWaitReceiveFragment().getMyorder();
//                    mOrderFragment.getMyWaitReceiveFragment().getmListView().getRefreshableView().setSelection(0);//调回顶部

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                BaseActivity.exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAINACTIVITY_REQUEST_CODE) {
            Picasso.with(this).load(BreakfastUtils.getAbsAvatarUrlPath(getCurrentUser().getUsername())).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(mIvAvatar);
        }


    }
}
