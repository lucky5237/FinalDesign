package zjut.jianlu.breakfast.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 4/13/2016.
 */
public abstract class ConfirmOnclickListener implements View.OnClickListener {

    private Context mContext;

    private String message;

    private TextView tv_message;

    public ConfirmOnclickListener(Context context, String messgae) {
        mContext = context;
        this.message = messgae;
    }

    @Override
    public void onClick(View v) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null);
        final AlertDialog set = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
        set.setView(view);
        final Button cancel = (Button) view.findViewById(R.id.btn_delete_cancel);
        Button confirm = (Button) view.findViewById(R.id.btn_delete_confirm);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_message.setText(message);
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
                onConfirm();
            }
        });
        set.show();
    }

    public abstract void onConfirm();


}
