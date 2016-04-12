package zjut.jianlu.breakfast.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.MainActivicy;
import zjut.jianlu.breakfast.adapter.NewestOrderAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.event.TakeOrderEvent;
import zjut.jianlu.breakfast.entity.requestBody.NewestOrderBody;
import zjut.jianlu.breakfast.entity.requestBody.TakeOrderBody;
import zjut.jianlu.breakfast.service.OrderService;

/**
 * Created by jianlu on 16/3/12.
 */
public class CourierHomePageFragment extends BaseRefreshableFragment {

    @Bind(R.id.pull_to_refresh_listview)
    PullToRefreshListView mPullToRefreshListview;

    private NewestOrderAdapter adapter;
    private List<OrderInfo> mOrderInfoList;
    private Retrofit retrofit;
    private OrderService orderService;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_courier_homepage;
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        getNewestOrder();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mOrderInfoList = new ArrayList<OrderInfo>();
        adapter = new NewestOrderAdapter(getActivity(), mOrderInfoList);
        mPullToRefreshListview.setAdapter(adapter);
        retrofit = MyApplication.getRetrofitInstance();
        orderService = retrofit.create(OrderService.class);

    }


    private void getNewestOrder() {


        Call<BaseResponse<List<OrderInfo>>> call = orderService.getNewestOrders(new NewestOrderBody(null, 0));
        call.enqueue(new BaseCallback<List<OrderInfo>>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<OrderInfo>>> call, Response<BaseResponse<List<OrderInfo>>> response) {
                if (mOrderInfoList != null || mOrderInfoList.size() > 0) {
                    mOrderInfoList.clear();
                }
                if (response.body().getData().size() == 0) {
                    Toast("当前暂无新订单");
                    mPullToRefreshListview.onRefreshComplete();
                    return;
                }
                mOrderInfoList.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
                mPullToRefreshListview.onRefreshComplete();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<OrderInfo>>> call, Response<BaseResponse<List<OrderInfo>>> response) {
                Toast(response.body().getMessage());
            }
        });
    }

    private void takeOrder(Integer orderId) {
        TakeOrderBody body = new TakeOrderBody(orderId, getCurrentUserID());
        Call<BaseResponse<String>> call = orderService.takeOrder(body);
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                Toast(response.body().getMessage());
                //接单成功,跳转到我的订单页面查看
                EventBus.getDefault().post(MainActivicy.ORDER_INDEX);

            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                //接单失败，刷新当前列表
                getNewestOrder();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void udpateNewOrder(TakeOrderEvent event) {
        if (event.getTag().equals(BreakfastConstant.GET_NEWEST_ORDER_TAG)) {
            takeOrder(event.getOrderId());
        }

    }


}
