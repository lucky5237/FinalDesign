package zjut.jianlu.breakfast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jianlu on 16/3/12.
 */
public class HomeFoodPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    private static final String[] TITLES = new String[]{"垃圾街", "养贤府", "家和堂"};


    public HomeFoodPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position % TITLES.length);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}
