package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.CommentActivity;
import zjut.jianlu.breakfast.activity.MakeOrderActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.enums.OrderStatus;
import zjut.jianlu.breakfast.listener.UpdateOrderStatusListener;

/**
 * Created by jianlu on 16/3/17.
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context mContext;

    private List<OrderInfo> mOrderInfoList;

    private ArrayList<ConfirmFood> mBuyFoodList;

    private Integer userType;

    private Integer orderId;

    private static final String CANCEL_ORDER = "取消订单";

    private static final String Start_DELIVERY = "开始配送";

    private static final String CONFIRM_ORDER = "确认收货";

    private static final String COMMENT_ORDER = "评价";

    private static final String ONEMORE_ORDER = "再来一单";

    private static final String CANCEL_CONFIRM_MESSGAE = "是否确定取消订单";

    private static final String START_DELIVERY_CONFIRM_MESSGAE = "是否确定开始配送";

    private static final String RECEIVE_CONFIRM_MESSAGE = "是否确认收货";

    public MyOrderAdapter(Context context, List<OrderInfo> orderInfos, Integer userType) {
        mContext = context;
        mOrderInfoList = orderInfos;
        this.userType = userType;
    }

    @Override
    public int getCount() {
        return mOrderInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OrderInfo orderinfo = (OrderInfo) getItem(position);
        orderId = orderinfo.getId();
        mBuyFoodList = orderinfo.getOrderdetails();
        if (mBuyFoodList != null && mBuyFoodList.size() > 0) {
            if (mBuyFoodList.size() == 1) {
                viewHolder.tvFoodName.setText(mBuyFoodList.get(0).getFood().getName());
            } else {
                viewHolder.tvFoodName
                        .setText(mBuyFoodList.get(0).getFood().getName() + "等" + mBuyFoodList.size() + "件食品");
            }
        }
        viewHolder.tvTime.setText(orderinfo.getCreateTs());
        viewHolder.tvMoney.setText("¥" + orderinfo.getAmount() + "");
        viewHolder.tvBonus.setText("¥" + orderinfo.getBonus() + "");
        viewHolder.tvStatus.setText(OrderStatus.getOrderDesByCode(orderinfo.getStatus(), userType));
        switch (orderinfo.getStatus()) {

        case 0:// 待卖家接单
            viewHolder.btnRight.setText(CANCEL_ORDER);
            viewHolder.btnRight.setVisibility(View.VISIBLE);
            viewHolder.btnLeft.setVisibility(View.GONE);
            viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                    OrderStatus.CANCEL.getCode(), CANCEL_CONFIRM_MESSGAE));
            break;

        case 1:// 待卖家配送
            viewHolder.btnRight.setVisibility(View.VISIBLE);
            if (userType == 0) {// 买家界面
                viewHolder.btnRight.setText(CANCEL_ORDER);
                viewHolder.btnLeft.setVisibility(View.GONE);
                viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                        OrderStatus.CANCEL.getCode(), CANCEL_CONFIRM_MESSGAE));
            }
            if (userType == 1) {
                viewHolder.btnLeft.setText(CANCEL_ORDER);
                viewHolder.btnRight.setText(Start_DELIVERY);
                viewHolder.btnLeft.setVisibility(View.VISIBLE);
                viewHolder.btnLeft.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                        OrderStatus.CANCEL.getCode(), CANCEL_CONFIRM_MESSGAE));
                viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                        OrderStatus.WAIT_CONFIRM.getCode(), START_DELIVERY_CONFIRM_MESSGAE));
            }
            break;
        case 2:
            viewHolder.btnRight.setVisibility(View.GONE);
            viewHolder.btnLeft.setVisibility(View.GONE);
            if (userType == 0) {
                viewHolder.btnRight.setText(CONFIRM_ORDER);
                viewHolder.btnRight.setVisibility(View.VISIBLE);
                viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                        OrderStatus.FINISH.getCode(), RECEIVE_CONFIRM_MESSAGE));
            }
            break;
        case 3:
            viewHolder.btnRight.setVisibility(View.VISIBLE);
            viewHolder.btnRight.setText(COMMENT_ORDER);
            viewHolder.btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// 订单评论
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra("orderId", orderId);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.btnLeft.setVisibility(View.GONE);
            if (userType == 0) {
                viewHolder.btnLeft.setText(ONEMORE_ORDER);
                viewHolder.btnLeft.setVisibility(View.VISIBLE);
                viewHolder.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {// 再来一单

                        Intent intent = new Intent(mContext, MakeOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("from", 0);
                        bundle.putSerializable("foodList", mBuyFoodList);
                        bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, orderinfo.getAmount());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }

            break;
        case 4:
            viewHolder.btnRight.setVisibility(View.GONE);
            viewHolder.btnLeft.setVisibility(View.GONE);
            break;

        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_deliver_gender)
        ImageView ivDeliverGender;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;
        @Bind(R.id.tv_food_name)
        TextView tvFoodName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_plus)
        TextView tvPlus;
        @Bind(R.id.tv_bonus)
        TextView tvBonus;
        @Bind(R.id.btn_left)
        Button btnLeft;
        @Bind(R.id.btn_right)
        Button btnRight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
