package zjut.jianlu.breakfast.fragment;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.Bind;
import butterknife.OnItemClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderFragment extends BaseRefreshableFragment

{
    @Bind(R.id.pull_to_refresh_listview)
    PullToRefreshListView pullToRefreshListview;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        Toast(mPageName);

        getMyorder();

        mListView.onRefreshComplete();
    }

    public void getMyorder() {


    }

    @OnItemClick(R.id.pull_to_refresh_listview)
    void onItemClick(int position) {
        Toast("onclick item posotion = " + String.valueOf(position + 1));
    }
}
