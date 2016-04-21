package zjut.jianlu.breakfast.fragment.rank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.IconPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.UserRankPagerAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class UserRankFragment extends BaseFragment {
    @Bind(R.id.icons)
    IconPageIndicator mIconPageIndicator;
    @Bind(R.id.pages)
    ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_rank;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new ClientRankFragment());
        mFragmentList.add(new CourierRankFragment());

        mViewPager.setAdapter(new UserRankPagerAdapter(getChildFragmentManager(), mFragmentList));
        mIconPageIndicator.setViewPager(mViewPager);

    }


}

