package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.CommentActivity;
import zjut.jianlu.breakfast.activity.MakeOrderActivity;
import zjut.jianlu.breakfast.activity.UserDetailActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.enums.OrderStatus;
import zjut.jianlu.breakfast.listener.ConfirmOnclickListener;
import zjut.jianlu.breakfast.listener.OrderClickListener;
import zjut.jianlu.breakfast.listener.UpdateOrderStatusListener;
import zjut.jianlu.breakfast.utils.BreakfastUtils;

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
    private static final String COURIER_CANCEL_ORDER = "联系买家取消订单";
    private static final String COURIER_CONFIRM_ORDER = "联系买家确认收货";


    private static final String Start_DELIVERY = "开始配送";

    private static final String CONFIRM_ORDER = "确认收货";

    private static final String COMMENT_ORDER = "评价";

    private static final String ONEMORE_ORDER = "再来一单";

    private static final String CANCEL_CONFIRM_MESSGAE = "是否确定取消订单";

    private static final String START_DELIVERY_CONFIRM_MESSGAE = "是否确定开始配送";

    private static final String RECEIVE_CONFIRM_MESSAGE = "是否确认收货";

    private static final String CALL_MESSAGE = "联系对方";

    private static final String CALL_DIALOG_MESSAGE = "是否要拨打对方电话";

    private static final String ORDER_NO_COMMENT = "订单未评价";

    private static final String ORDER_HAD_COMMENT = "订单已评价";

    private static final String VIEW_MY_COMMENT = "查看我的评论";


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
    public View getView(final int position, View convertView, ViewGroup parent) {

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
        if (orderinfo.getStatus() != 0) {
            final User theOtherUser = userType == 0 ? orderinfo.getCourierUser() : orderinfo.getClientUser();
            String avatarUrl = BreakfastUtils.getAbsAvatarUrlPath(theOtherUser.getUsername());
            Picasso.with(mContext).load(avatarUrl).into(viewHolder.ivAvatar);
            viewHolder.ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserDetailActivity.class);
                    intent.putExtra("userId", theOtherUser.getId().intValue());
                    intent.putExtra("userType", theOtherUser.getType());
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder.ivAvatar.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, "ic_question"));
        }
        switch (orderinfo.getStatus()) {

            case 0:// 待接单
                viewHolder.btnRight.setText(CANCEL_ORDER);
                viewHolder.btnRight.setVisibility(View.VISIBLE);
                viewHolder.btnLeft.setVisibility(View.GONE);
                viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                        OrderStatus.CANCEL.getCode(), CANCEL_CONFIRM_MESSGAE));
                break;

            case 1:// 待配送
                viewHolder.btnRight.setVisibility(View.VISIBLE);
                if (userType == 0) {// 买家界面
                    viewHolder.btnRight.setText(CANCEL_ORDER);
                    viewHolder.btnLeft.setVisibility(View.VISIBLE);
                    viewHolder.btnLeft.setText(CALL_MESSAGE);
                    viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                            OrderStatus.CANCEL.getCode(), CANCEL_CONFIRM_MESSGAE));
                    viewHolder.btnLeft.setOnClickListener(new ConfirmOnclickListener(mContext, CALL_DIALOG_MESSAGE + orderinfo.getCourierUser().getMobile()) {
                        @Override
                        public void onConfirm() {
                            Intent intentCall = new Intent(Intent.ACTION_CALL);
                            intentCall.setData(Uri.parse("tel:" + orderinfo.getCourierUser().getMobile()));
                            mContext.startActivity(intentCall);
                        }
                    });
                }
                if (userType == 1) {
                    viewHolder.btnLeft.setText(COURIER_CANCEL_ORDER);
                    viewHolder.btnRight.setText(Start_DELIVERY);
                    viewHolder.btnLeft.setVisibility(View.VISIBLE);
                    viewHolder.btnLeft.setOnClickListener(new ConfirmOnclickListener(mContext, CALL_DIALOG_MESSAGE + orderinfo.getClientUser().getMobile()) {
                        @Override
                        public void onConfirm() {
                            Intent intentCall = new Intent(Intent.ACTION_CALL);
                            intentCall.setData(Uri.parse("tel:" + orderinfo.getClientUser().getMobile()));
                            mContext.startActivity(intentCall);
                        }
                    });
                    viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                            OrderStatus.WAIT_CONFIRM.getCode(), START_DELIVERY_CONFIRM_MESSGAE));
                }
                break;
            case 2://待收货
                viewHolder.btnRight.setVisibility(View.GONE);
                viewHolder.btnLeft.setVisibility(View.GONE);
                if (userType == 0) {
                    viewHolder.btnRight.setText(CONFIRM_ORDER);
                    viewHolder.btnRight.setVisibility(View.VISIBLE);
                    viewHolder.btnRight.setOnClickListener(new UpdateOrderStatusListener(mContext, orderId,
                            OrderStatus.FINISH.getCode(), RECEIVE_CONFIRM_MESSAGE));
                }
                if (userType == 1) {
                    viewHolder.btnRight.setText(COURIER_CONFIRM_ORDER);
                    viewHolder.btnRight.setVisibility(View.VISIBLE);
                    viewHolder.btnRight.setOnClickListener(new ConfirmOnclickListener(mContext, CALL_DIALOG_MESSAGE + orderinfo.getClientUser().getMobile()) {
                        @Override
                        public void onConfirm() {
                            Intent intentCall = new Intent(Intent.ACTION_CALL);
                            intentCall.setData(Uri.parse("tel:" + orderinfo.getClientUser().getMobile()));
                            mContext.startActivity(intentCall);
                        }
                    });
                }
                break;
            case 3://已完成  包括已评论和未评论
                if (userType == 0) {
                    if (orderinfo.getIsClientCommented() == 0) {//买家未评论
                        viewHolder.btnRight.setVisibility(View.VISIBLE);
                        viewHolder.btnRight.setText(COMMENT_ORDER);
                        viewHolder.tvStatus.setText(ORDER_NO_COMMENT);
                        viewHolder.btnRight.setOnClickListener(new OrderClickListener(position, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, CommentActivity.class);
                                OrderInfo orderInfos = (OrderInfo) getItem(position);
                                String otherUserName = userType == 0 ? orderInfos.getCourierUser().getUsername() : orderInfos.getClientUser().getUsername();
                                Long otherUserId = userType == 0 ? orderInfos.getCourierUser().getId() : orderInfos.getClientUser().getId();
                                intent.putExtra("orderId", orderInfos.getId());
                                intent.putExtra("otherUserId", otherUserId);
                                intent.putExtra("otherUserName", otherUserName);
                                mContext.startActivity(intent);
                            }
                        }));
                    } else {
                        viewHolder.tvStatus.setText(ORDER_HAD_COMMENT);
                        viewHolder.btnRight.setVisibility(View.VISIBLE);
                        viewHolder.btnRight.setText(VIEW_MY_COMMENT);
                        viewHolder.btnRight.setOnClickListener(new OrderClickListener(position, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, UserDetailActivity.class);
                                intent.putExtra("userId", orderinfo.getCourierUser().getId().intValue());
                                intent.putExtra("userType", orderinfo.getCourierUser().getType());
                                intent.putExtra("orderId", orderinfo.getId());
                                mContext.startActivity(intent);
                            }
                        }));

                    }
                }

                if (userType == 1) {
                    if (orderinfo.getIsCourierCommented() == 0) {//卖家未评论
                        viewHolder.btnRight.setVisibility(View.VISIBLE);
                        viewHolder.btnRight.setText(COMMENT_ORDER);
                        viewHolder.tvStatus.setText(ORDER_NO_COMMENT);
                        viewHolder.btnRight.setOnClickListener(new OrderClickListener(position, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, CommentActivity.class);
                                OrderInfo orderInfos = (OrderInfo) getItem(position);
                                String otherUserName = userType == 0 ? orderInfos.getCourierUser().getUsername() : orderInfos.getClientUser().getUsername();
                                Long otherUserId = userType == 0 ? orderInfos.getCourierUser().getId() : orderInfos.getClientUser().getId();
                                intent.putExtra("orderId", orderInfos.getId());
                                intent.putExtra("otherUserId", otherUserId);
                                intent.putExtra("otherUserName", otherUserName);
                                mContext.startActivity(intent);
                            }
                        }));
                    } else {
                        viewHolder.tvStatus.setText(ORDER_HAD_COMMENT);
                        viewHolder.btnRight.setVisibility(View.VISIBLE);
                        viewHolder.btnRight.setText(VIEW_MY_COMMENT);
                        viewHolder.btnRight.setOnClickListener(new OrderClickListener(position, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, UserDetailActivity.class);
                                intent.putExtra("userId", orderinfo.getClientUser().getId().intValue());
                                intent.putExtra("userType", orderinfo.getClientUser().getType());
                                intent.putExtra("orderId", orderinfo.getId());
                                mContext.startActivity(intent);
                            }
                        }));
                    }
                }

                viewHolder.btnLeft.setVisibility(View.GONE);
                if (userType == 0) {
                    viewHolder.btnLeft.setText(ONEMORE_ORDER);
                    viewHolder.btnLeft.setVisibility(View.VISIBLE);
                    viewHolder.btnLeft.setOnClickListener(new OrderClickListener(position, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, MakeOrderActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("from", 2);
                            OrderInfo orderInfos = (OrderInfo) getItem(position);
                            mBuyFoodList = orderInfos.getOrderdetails();
                            bundle.putSerializable("foodList", mBuyFoodList);
                            bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, orderInfos.getAmount());
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    }));
                }

                break;

            case 4://已取消
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
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
