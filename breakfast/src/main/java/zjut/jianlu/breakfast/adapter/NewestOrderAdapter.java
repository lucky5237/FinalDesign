package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;

/**
 * Created by jianlu on 16/3/16.
 */
public class NewestOrderAdapter extends BaseAdapter {

    private Context mContext;

    private List<OrderInfo> mlist;

    private View view;

    public NewestOrderAdapter(Context context , List<OrderInfo> orderInfos){
        mContext=context;
        mlist=orderInfos;
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
        viewHolder.tvBonus.setText("¥"+orderInfo.getBonus()+"");
        viewHolder.tvTime.setText(orderInfo.getCreateTs());
        viewHolder.tvClient.setText(orderInfo.getClientUser().getUsername());
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
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_bonus)
        TextView tvBonus;
        @Bind(R.id.tv_client)
        TextView tvClient;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
