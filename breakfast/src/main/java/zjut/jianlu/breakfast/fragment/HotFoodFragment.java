package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.HotFoodAdapter;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.entity.Food;

/**
 * Created by jianlu on 16/3/12.
 */
public class HotFoodFragment extends BaseRefreshableFragment {
//    @Bind(R.id.pull_to_refresh_listview)
//    PullToRefreshListView listView;


    private List<Food> mFoodList=new ArrayList<Food>();
    private HotFoodAdapter adapter ;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_food;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view =getActivity().findViewById(R.id.main_cotainer);
        adapter=new HotFoodAdapter(getActivity(),mFoodList,view);
        mListView.setAdapter(adapter);

    }

    private void getHotFood() {
        BmobQuery query=new BmobQuery("food").order("-sales");
        query.findObjects(getActivity(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                String jsonString = jsonArray.toString();
                if (!TextUtils.isEmpty(jsonString)){
                    List<Food> footList=new Gson().fromJson(jsonString,new TypeToken<List<Food>>(){}.getType());
                    if (footList!=null && footList.size()>0){
                        if (mFoodList!=null && mFoodList.size()>0){
                            mFoodList.clear();
                        }
                        mFoodList.addAll(footList);
                        adapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//        Toast.makeText(getActivity(),"正在刷新",Toast.LENGTH_SHORT).show();
        getHotFood();
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.onRefreshComplete();
            }
        },1000);


    }
}
