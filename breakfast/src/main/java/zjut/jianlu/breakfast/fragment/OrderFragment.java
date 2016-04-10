package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import zjut.jianlu.breakfast.adapter.MyOrderAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.UpdateOrderStatusEvent;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.requestBody.BaseUserBody;
import zjut.jianlu.breakfast.entity.requestBody.UpdateOrderStatusBody;
import zjut.jianlu.breakfast.service.OrderService;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderFragment extends BaseRefreshableFragment

{
    @Bind(R.id.pull_to_refresh_listview)
    PullToRefreshListView pullToRefreshListview;

    private Retrofit retrofit;

    private OrderService orderService;

    private MyOrderAdapter adapter;

    private List<OrderInfo> mOrderInfos;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        getMyorder();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.onRefreshComplete();

            }
        }, 500);

    }


    public void getMyorder() {
        Call<BaseResponse<List<OrderInfo>>> call = orderService.getMyorderList(new BaseUserBody(getCurrentUserID(), getCurrentUserType()));
        call.enqueue(new BaseCallback<List<OrderInfo>>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<OrderInfo>>> call, Response<BaseResponse<List<OrderInfo>>> response) {
                if (mOrderInfos != null && mOrderInfos.size() > 0) {
                    mOrderInfos.clear();
                }
                mOrderInfos.addAll(response.body().getData());
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<OrderInfo>>> call, Response<BaseResponse<List<OrderInfo>>> response) {
                Toast(response.body().getMessage());

            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retrofit = MyApplication.getRetrofitInstance();
        orderService = retrofit.create(OrderService.class);
        mOrderInfos = new ArrayList<OrderInfo>();
        adapter = new MyOrderAdapter(mContext, mOrderInfos, getCurrentUserType());
        pullToRefreshListview.setAdapter(adapter);
        pullToRefreshListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getMyorder();
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
    public void updateOrderStatus(UpdateOrderStatusEvent event) {
        UpdateOrderStatusBody body = new UpdateOrderStatusBody(event.getStatus(), getCurrentUserType(), event.getOrderId());
        Call<BaseResponse<String>> call = orderService.updateOrderStatus(body);
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast.makeText(mContext, BreakfastConstant.NO_NET_MESSAGE, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                getMyorder();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
