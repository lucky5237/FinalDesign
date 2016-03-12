package zjut.jianlu.breakfast.fragment;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderFragment extends BaseRefreshableFragment

{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
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
