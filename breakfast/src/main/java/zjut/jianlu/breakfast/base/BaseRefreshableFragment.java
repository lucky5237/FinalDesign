package zjut.jianlu.breakfast.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 16/3/12.
 */
public abstract class BaseRefreshableFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView> {


    @Bind(R.id.pull_to_refresh_listview)
    public PullToRefreshListView mListView;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnRefreshListener(this);
    }
}
