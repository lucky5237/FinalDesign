package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.listener.UpdateOrderStatusListener;

/**
 * Created by jianlu on 16/3/16.
 */
public class NewestOrderAdapter extends BaseAdapter {

    private Context mContext;

    private List<OrderInfo> mlist;

    private View view;

    public NewestOrderAdapter(Context context, List<OrderInfo> orderInfos) {
        mContext = context;
        mlist = orderInfos;
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        OrderInfo orderInfo = (OrderInfo) getItem(position);
        List<ConfirmFood> confirmFoodList = orderInfo.getOrderdetails();
        if (confirmFoodList.size() == 1) {
            viewHolder.tvFoodName.setText(confirmFoodList.get(0).getFood().getName());
        } else if (confirmFoodList.size() > 1) {
            viewHolder.tvFoodName.setText(confirmFoodList.get(0).getFood().getName() + "等" + confirmFoodList.size() + "件食品");
        } else {
            viewHolder.tvFoodName.setText("没买东西怎么能下单？？？");

        }

        viewHolder.tvBonus.setText("¥" + orderInfo.getBonus() + "");
        viewHolder.tvTime.setText(orderInfo.getCreateTs());
        viewHolder.tvAddress.setText(orderInfo.getClientUser().getAddress());
        viewHolder.btnReceive.setOnClickListener(new UpdateOrderStatusListener(mContext, orderInfo.getId(), 1));


//        Picasso.with(mContext).load(orderInfo.get).placeholder(R.mipmap.ic_launcher).resize(100, 100).centerCrop().into(viewHolder.ivImage);
//        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, url);
//                if (!popWindow.isShowing()) {
//                    popWindow.showAtLocation(mView, Gravity.TOP, 0, 0);
//                }
//            }
//        });
        return convertView;

    }


    static class ViewHolder {

        @Bind(R.id.tv_bonus)
        TextView tvBonus;
        @Bind(R.id.iv_user_image)
        CircleImageView ivUserImage;
        @Bind(R.id.tv_food_name)
        TextView tvFoodName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.btn_receive)
        Button btnReceive;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
