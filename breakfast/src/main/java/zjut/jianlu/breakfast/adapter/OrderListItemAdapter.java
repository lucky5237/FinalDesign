package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;

/**
 * Created by jianlu on 16/3/17.
 */
public class OrderListItemAdapter extends BaseAdapter {

    private Context mContext;

    private List<OrderInfo> mOrderInfoList;

    public OrderListItemAdapter(Context context, List<OrderInfo> orderInfos) {
        mContext = context;
        mOrderInfoList = orderInfos;
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
        OrderInfo orderinfo = (OrderInfo) getItem(position);
        viewHolder.tvTime.setText(orderinfo.getCreateTs());
        viewHolder.tvMoney.setText(orderinfo.getAmount() + "");
        viewHolder.tvBonus.setText(orderinfo.getBonus() + "");


        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.iv_deliver_gender)
        ImageView ivDeliverGender;
        @Bind(R.id.tv_status)
        TextView tvStatus;
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
        @Bind(R.id.btn_comment)
        Button btnComment;
        @Bind(R.id.btn_make_onemore)
        Button btnMakeOnemore;
        @Bind(R.id.btn_delete)
        Button btnDelete;

//        @OnClick({R.id.btn_delete})
//        void onClick(int position) {
//            Log.d("jianlu","delete");
//
//        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
