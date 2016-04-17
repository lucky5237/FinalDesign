package zjut.jianlu.breakfast.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.event.TakeOrderEvent;
import zjut.jianlu.breakfast.entity.event.UpdateOrderStatusEvent;

/**
 * Created by jianlu on 16/4/10.
 */
public class UpdateOrderStatusListener implements View.OnClickListener {

    private Integer orderId;

    private Integer status;

    private Context mContext;

    private String message;


    public UpdateOrderStatusListener(Context context, Integer orderId, Integer status, String message) {
        mContext = context;
        this.orderId = orderId;
        this.status = status;
        this.message = message;
    }

    @Override
    public void onClick(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null);
        final AlertDialog set = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
        set.setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_message);
        tv.setText(message);
        final Button cancel = (Button) view.findViewById(R.id.btn_delete_cancel);
        Button confirm = (Button) view.findViewById(R.id.btn_delete_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                set.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                set.dismiss();
                if (status != 1) {
                    EventBus.getDefault().post(new UpdateOrderStatusEvent(status, orderId));//更新订单状态
                } else {
                    EventBus.getDefault().post(new TakeOrderEvent(BreakfastConstant.GET_NEWEST_ORDER_TAG, orderId));//接单

                }
            }
        });
        set.show();
    }
}