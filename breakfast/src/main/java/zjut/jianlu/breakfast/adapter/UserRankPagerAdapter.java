package zjut.jianlu.breakfast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 4/12/2016.
 */
public class UserRankPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    private List<Fragment> mFragmentList;

    private static final int[] ICONS = new int[]{R.mipmap.ic_rank_buyer, R.mipmap.ic_rank_deliver};

    public UserRankPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position % ICONS.length);
    }


    @Override
    public int getIconResId(int position) {
        return ICONS[position % ICONS.length];
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
