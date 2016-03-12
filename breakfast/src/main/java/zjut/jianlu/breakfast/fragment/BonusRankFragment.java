package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import zjut.jianlu.breakfast.base.BaseRefreshableFragment;

import static zjut.jianlu.breakfast.R.layout.fragment_bonus_rank;

/**
 * Created by jianlu on 16/3/12.
 */
public class BonusRankFragment extends BaseRefreshableFragment {
    @Override
    public int getLayoutId() {
        return fragment_bonus_rank;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        showToast(mPageName);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        showToast(mPageName);
        mListView.onRefreshComplete();
    }
}
