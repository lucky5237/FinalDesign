package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.entity.OrderInfo;

/**
 * Created by jianlu on 16/3/12.
 */
public class HotFoodFragment extends BaseRefreshableFragment {
//    @Bind(R.id.pull_to_refresh_listview)
//    PullToRefreshListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> mOrderList=new ArrayList<String>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_food;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mOrderList);
        mListView.setAdapter(adapter);

    }

    private void getNewestOrder() {
        BmobQuery query =new BmobQuery("order_info");
        query.findObjects(getActivity(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                String jsonString=jsonArray.toString();
                mListView.onRefreshComplete();
                List<OrderInfo> orderList=new Gson().fromJson(jsonString,new TypeToken<List<OrderInfo>>(){}.getType());
                if (orderList!=null && orderList.size()>0){
                    Log.d("jianlu","总共获取"+orderList.size()+"条订单");
                    for (OrderInfo order : orderList){
                        mOrderList.add(order.toString());
                    }
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(int i, String s) {

                Log.d("jianlu","i is "+i+"s is "+ s);
            }
        });

    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        Toast.makeText(getActivity(),"正在刷新",Toast.LENGTH_SHORT).show();
        getNewestOrder();


    }
}
