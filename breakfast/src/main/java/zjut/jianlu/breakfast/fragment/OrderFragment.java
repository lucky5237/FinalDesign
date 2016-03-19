package zjut.jianlu.breakfast.fragment;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.Bind;
import butterknife.OnItemClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.entity.User;

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
        showToast(mPageName);

        getMyorder();
        
        mListView.onRefreshComplete();
    }

    public void getMyorder() {
        User user =getCurrentUser();
        if (user!=null){




        }else{
            showToast("订单获取异常");

        }



    }

    @OnItemClick(R.id.pull_to_refresh_listview)
    void onItemClick(int position){
        showToast("onclick item posotion = "+String.valueOf(position+1));
    }
}
