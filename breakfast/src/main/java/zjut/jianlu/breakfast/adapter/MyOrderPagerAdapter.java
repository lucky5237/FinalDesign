package zjut.jianlu.breakfast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jianlu on 16/3/12.
 */
public class MyOrderPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    private static final String[] CLIENT_TITLES = new String[]{"全部订单", "待接单", "待配送", "待收货", "交易成功", "交易取消"};
    private static final String[] COURIER_TITLES = new String[]{"全部订单", "待配送", "待收货", "交易成功", "交易取消"};
    private String[] mTitles = CLIENT_TITLES;

    public MyOrderPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
        if (fragmentList != null && fragmentList.size() == COURIER_TITLES.length) {
            mTitles = COURIER_TITLES;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position % mTitles.length];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position % mTitles.length);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}
