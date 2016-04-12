package zjut.jianlu.breakfast.fragment.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.HotFoodAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.entity.requestBody.FoodRankBody;
import zjut.jianlu.breakfast.service.FoodService;

/**
 * Created by jianlu on 16/3/12.
 */
public class FoodSalesRankFragment extends BaseRefreshableFragment {
//    @Bind(R.id.pull_to_refresh_listview)
//    PullToRefreshListView listView;


    private List<Food> mFoodList = new ArrayList<Food>();
    private HotFoodAdapter adapter;
    private Retrofit retrofit;
    private FoodService foodService;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_food;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getActivity().findViewById(R.id.main_cotainer);
        adapter = new HotFoodAdapter(getActivity(), mFoodList, view);
        mListView.setAdapter(adapter);
        retrofit = MyApplication.getRetrofitInstance();
        foodService = retrofit.create(FoodService.class);
        getHotFood();

    }


    private void getHotFood() {

        Call<BaseResponse<List<Food>>> call = foodService.getSalesRank(new FoodRankBody(BreakfastConstant.FOOD_SALES_RANK_NUM, false));
        call.enqueue(new BaseCallback<List<Food>>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<Food>>> call, Response<BaseResponse<List<Food>>> response) {
                List<Food> foodList = response.body().getData();
                if (foodList != null || foodList.size() > 0) {
                    if (mFoodList != null && mFoodList.size() > 0) {
                        mFoodList.clear();
                    }
                    mFoodList.addAll(foodList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast("暂无热销产品");
                }
            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<Food>>> call, Response<BaseResponse<List<Food>>> response) {

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
        }, 1000);


    }
}
