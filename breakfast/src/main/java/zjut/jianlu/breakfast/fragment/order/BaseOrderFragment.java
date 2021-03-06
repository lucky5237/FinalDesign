package zjut.jianlu.breakfast.fragment.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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
import zjut.jianlu.breakfast.activity.MainActivity;
import zjut.jianlu.breakfast.adapter.MyOrderAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.event.ChangeIndexEvent;
import zjut.jianlu.breakfast.entity.event.UpdateOrderStatusEvent;
import zjut.jianlu.breakfast.entity.requestBody.MyOrderListBody;
import zjut.jianlu.breakfast.entity.requestBody.UpdateOrderStatusBody;
import zjut.jianlu.breakfast.service.OrderService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;

/**
 * Created by jianlu on 16/3/12.
 */
public abstract class BaseOrderFragment extends BaseFragment

{
    @Bind(R.id.pull_to_refresh_listview)
    PullToRefreshListView mListView;

    private Retrofit retrofit;

    private OrderService orderService;

    private MyOrderAdapter adapter;

    private List<OrderInfo> mOrderInfos;

    public Integer status;

    public PullToRefreshListView getmListView() {
        return mListView;
    }

    public void setmListView(PullToRefreshListView mListView) {
        this.mListView = mListView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    public void getMyorder() {
        showMyDialog();
        Call<BaseResponse<List<OrderInfo>>> call = orderService
                .getMyorderList(new MyOrderListBody(getCurrentUserID(), getCurrentUserType(), status));
        call.enqueue(new BaseCallback<List<OrderInfo>>() {
            @Override
            public void onFinally() {
                dismissMyDialog();
            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
                ShowUI(BreakfastConstant.NO_NET);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<OrderInfo>>> call,
                                     Response<BaseResponse<List<OrderInfo>>> response) {
                if (response.body().getData() == null || response.body().getData().size() == 0) {
                    ShowUI(BreakfastConstant.NO_ORDER);
                    return;
                }
                if (mOrderInfos != null && mOrderInfos.size() > 0) {
                    mOrderInfos.clear();
                }
                ShowUI(BreakfastConstant.NORMAL);
                mOrderInfos.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<OrderInfo>>> call,
                                     Response<BaseResponse<List<OrderInfo>>> response) {
                Toast(response.body().getMessage());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOrderStatus();
        retrofit = MyApplication.getRetrofitInstance();
        orderService = retrofit.create(OrderService.class);
        mOrderInfos = new ArrayList<OrderInfo>();
        adapter = new MyOrderAdapter(mContext, mOrderInfos, getCurrentUserType());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                getMyorder();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.onRefreshComplete();

                    }
                }, 500);
            }
        });
        if (BreakfastUtils.isNetworkAvailable(mContext)) {
            getMyorder();

        } else {
            ShowUI(BreakfastConstant.NO_NET);
        }
        btnLoadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyorder();

            }
        });
        btnLoadAgainOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyorder();
            }
        });

    }

    public abstract void setOrderStatus();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

//    @Subscribe
//    public void RefreshMyOrder() {
//        getMyorder();
//    }

    @Subscribe
    public void updateOrderStatus(UpdateOrderStatusEvent event) {
        showMyDialog();
        UpdateOrderStatusBody body = new UpdateOrderStatusBody(event.getStatus(), getCurrentUserType(),
                event.getOrderId());
        Call<BaseResponse<String>> call = orderService.updateOrderStatus(body);
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onFinally() {
                dismissMyDialog();
            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
//                getMyorder();
                EventBus.getDefault().post(new ChangeIndexEvent(MainActivity.ORDER_INDEX, status));
            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getData());
//                getMyorder();
                EventBus.getDefault().post(new ChangeIndexEvent(MainActivity.ORDER_INDEX, status));

            }
        });

    }
}
