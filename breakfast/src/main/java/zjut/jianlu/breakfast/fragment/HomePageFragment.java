package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.OrderWantedItemAdapter;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.entity.OrderInfo;

/**
 * Created by jianlu on 16/3/12.
 */
public class HomePageFragment extends BaseRefreshableFragment {

    @Bind(R.id.pull_to_refresh_listview)
    PullToRefreshListView mPullToRefreshListview;
    private OrderWantedItemAdapter adapter;

    private List<OrderInfo> mOrderInfoList;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        showToast(mPageName);

        getNewestOrder();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mOrderInfoList = new ArrayList<OrderInfo>();
        adapter = new OrderWantedItemAdapter(getActivity(), mOrderInfoList);
        mPullToRefreshListview.setAdapter(adapter);

    }

    private void getNewestOrder() {
        BmobQuery query = new BmobQuery("order_info").addWhereEqualTo("status", 0).order("-createdAt");
        query.findObjects(getActivity(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                mListView.onRefreshComplete();
                String jsonString = jsonArray.toString();
                List<OrderInfo> mOrderInfo = new Gson().fromJson(jsonString, new TypeToken<List<OrderInfo>>() {
                }.getType());
                if (mOrderInfo != null && mOrderInfo.size() > 0) {
                        if (mOrderInfoList.size()>0){
                            mOrderInfoList.clear();
                        }
                    mOrderInfoList.addAll(mOrderInfo);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(int i, String s) {
                showToast(s);
            }
        });


    }
}
