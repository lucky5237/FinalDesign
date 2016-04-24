package zjut.jianlu.breakfast.fragment.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.MyOrderPagerAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;

/**
 * Created by jianlu on 16/4/23.
 */
public class MyOrderFragment extends BaseFragment {

    @Bind(R.id.titles)
    TabPageIndicator mtabPageIndicator;
    @Bind(R.id.pages)
    ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private MyAllOrderFragment myAllOrderFragment;
    private MyWaitReceiveFragment myWaitReceiveFragment;
    private MyWaitDeliveryFragment myWaitDeliveryFragment;
    private MyWaitConfirmFragment myWaitConfirmFragment;
    private MyFinishOrderFragment myFinishOrderFragment;
    private MyCancelOrderFragment myCancelOrderFragment;

    public MyAllOrderFragment getMyAllOrderFragment() {
        return myAllOrderFragment;
    }

    public void setMyAllOrderFragment(MyAllOrderFragment myAllOrderFragment) {
        this.myAllOrderFragment = myAllOrderFragment;
    }

    public MyWaitReceiveFragment getMyWaitReceiveFragment() {
        return myWaitReceiveFragment;
    }

    public void setMyWaitReceiveFragment(MyWaitReceiveFragment myWaitReceiveFragment) {
        this.myWaitReceiveFragment = myWaitReceiveFragment;
    }

    public MyWaitDeliveryFragment getMyWaitDeliveryFragment() {
        return myWaitDeliveryFragment;
    }

    public void setMyWaitDeliveryFragment(MyWaitDeliveryFragment myWaitDeliveryFragment) {
        this.myWaitDeliveryFragment = myWaitDeliveryFragment;
    }

    public MyWaitConfirmFragment getMyWaitConfirmFragment() {
        return myWaitConfirmFragment;
    }

    public void setMyWaitConfirmFragment(MyWaitConfirmFragment myWaitConfirmFragment) {
        this.myWaitConfirmFragment = myWaitConfirmFragment;
    }

    public MyFinishOrderFragment getMyFinishOrderFragment() {
        return myFinishOrderFragment;
    }

    public void setMyFinishOrderFragment(MyFinishOrderFragment myFinishOrderFragment) {
        this.myFinishOrderFragment = myFinishOrderFragment;
    }

    public MyCancelOrderFragment getMyCancelOrderFragment() {
        return myCancelOrderFragment;
    }

    public void setMyCancelOrderFragment(MyCancelOrderFragment myCancelOrderFragment) {
        this.myCancelOrderFragment = myCancelOrderFragment;
    }

    public int getLayoutId() {
        return R.layout.fragment_client_homepage;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myAllOrderFragment = new MyAllOrderFragment();
        myWaitDeliveryFragment = new MyWaitDeliveryFragment();
        myWaitConfirmFragment = new MyWaitConfirmFragment();
        myFinishOrderFragment = new MyFinishOrderFragment();
        myCancelOrderFragment = new MyCancelOrderFragment();


        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(myAllOrderFragment);
        if (getCurrentUserType() == 0) {//如果是买家，展示待接单界面
            myWaitReceiveFragment = new MyWaitReceiveFragment();
            mFragmentList.add(myWaitReceiveFragment);
        }
        mFragmentList.add(myWaitDeliveryFragment);
        mFragmentList.add(myWaitConfirmFragment);
        mFragmentList.add(myFinishOrderFragment);
        mFragmentList.add(myCancelOrderFragment);

        mViewPager.setAdapter(new MyOrderPagerAdapter(getChildFragmentManager(), mFragmentList));
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);
        mtabPageIndicator.setViewPager(mViewPager);
        mtabPageIndicator.setCurrentItem(0);


    }
}
