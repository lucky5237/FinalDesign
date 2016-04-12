package zjut.jianlu.breakfast.fragment.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.HomeFoodPagerAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class ClientHomePageFragment extends BaseFragment {

    @Bind(R.id.titles)
    TabPageIndicator mtabPageIndicator;
    @Bind(R.id.pages)
    ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_client_homepage;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new RubbishStreetFoodFrament());
        mFragmentList.add(new YangxianCanteenFoodFragment());
        mFragmentList.add(new HomePeaceCanteenFoodFragment());
        mViewPager.setAdapter(new HomeFoodPagerAdapter(getChildFragmentManager(), mFragmentList));
        mtabPageIndicator.setViewPager(mViewPager);
        mtabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Toast("当前选中了第" + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
