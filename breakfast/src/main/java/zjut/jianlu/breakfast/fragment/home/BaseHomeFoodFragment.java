package zjut.jianlu.breakfast.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.adapter.HomeFoodAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.entity.requestBody.HomeFoodBody;
import zjut.jianlu.breakfast.service.FoodService;

import static zjut.jianlu.breakfast.R.layout.fragment_home_food;

/**
 * Created by jianlu on 16/4/12.
 */
public abstract class BaseHomeFoodFragment extends BaseRefreshableFragment {
    private HomeFoodAdapter adapter;

    private List<Food> foodList;

    public Integer placeId;//食品购买地点id

    public Integer flag = 0;//0-销量，1-新品

    private Retrofit retrofit;

    private FoodService foodService;


    private static final int SHOW_NUM = 10;

    @Override
    public int getLayoutId() {

        setPlaceId();
        return fragment_home_food;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        foodList = new ArrayList<Food>();
        adapter = new HomeFoodAdapter(mContext, foodList);
        mListView.setAdapter(adapter);
        retrofit = MyApplication.getRetrofitInstance();
        foodService = retrofit.create(FoodService.class);

    }

    public abstract void setPlaceId();

    private void getallFood(final Integer placeId, Integer flag) {
        Call<BaseResponse<List<Food>>> call = foodService.getAllFood(new HomeFoodBody(SHOW_NUM, placeId, flag));
        call.enqueue(new BaseCallback<List<Food>>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<Food>>> call, Response<BaseResponse<List<Food>>> response) {
                if (foodList != null || foodList.size() > 0) {
                    foodList.clear();
                }

                foodList.addAll(response.body().getData());
                updateLocalDB(placeId, foodList);

                adapter.notifyDataSetChanged();
                mListView.onRefreshComplete();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<Food>>> call, Response<BaseResponse<List<Food>>> response) {

            }
        });
    }

    public void updateLocalDB(Integer placeId, List<Food> foodList) {
        if (foodList == null || foodList.size() == 0) {
            ShowUI(BreakfastConstant.NO_FOOD);
            return;
        }
        ShowUI(BreakfastConstant.NORMAL);
//        Food
//        for(Food food : foodList){
//            food.getPlace().sa
//        }

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {

        getallFood(placeId, flag);
    }
}
