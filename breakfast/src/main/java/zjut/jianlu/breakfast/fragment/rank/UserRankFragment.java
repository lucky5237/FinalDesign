package zjut.jianlu.breakfast.fragment.rank;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.RankPagerAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class UserRankFragment extends BaseFragment {
    @Bind(R.id.lines)
    LinePageIndicator mpageIndictor;
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
        final float density = getResources().getDisplayMetrics().density;
        mViewPager.setAdapter(new RankPagerAdapter(getChildFragmentManager(), mFragmentList));
        mpageIndictor.setViewPager(mViewPager);
        mpageIndictor.setSelectedColor(Color.parseColor("#FF5500"));
        mpageIndictor.setStrokeWidth(4 * density);
        mpageIndictor.setLineWidth(30 * density);
        mpageIndictor.setPadding(0,0,0,10);
    }


}

