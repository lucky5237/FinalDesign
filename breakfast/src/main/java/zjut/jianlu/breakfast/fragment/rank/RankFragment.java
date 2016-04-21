package zjut.jianlu.breakfast.fragment.rank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.RankPagerAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class RankFragment extends BaseFragment {
    @Bind(R.id.titles)
    TabPageIndicator mtabPageIndicator;
    @Bind(R.id.pages)
    ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new FoodSalesRankFragment());
        mFragmentList.add(new UserRankFragment());
        mViewPager.setAdapter(new RankPagerAdapter(getChildFragmentManager(), mFragmentList));
        mtabPageIndicator.setViewPager(mViewPager);
    }


}

