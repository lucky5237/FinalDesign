//package zjut.jianlu.breakfast.adapter;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.viewpagerindicator.IconPagerAdapter;
//import com.viewpagerindicator.LinePageIndicator;
//
//import java.util.List;
//
//import zjut.jianlu.breakfast.R;
//
///**
// * Created by jianlu on 4/12/2016.
// */
//public class UserRankPagerAdapter extends FragmentPagerAdapter {
//
//    private List<Fragment> mFragmentList;
//
//    public UserRankPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
//        super(fm);
//        mFragmentList = fragmentList;
//    }
//
//
//    @Override
//    public Fragment getItem(int position) {
//        return mFragmentList.get(position % ICONS.length);
//    }
//
//
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//}
