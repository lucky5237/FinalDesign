package zjut.jianlu.breakfast.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 16/4/13.
 */
public class MyAlertDialog {

    private TextView tvTitle;

    private TextView tvMessage;

    private Button btnNegative;

    private Button btnPositive;

    private AlertDialog set;

    private static Context context;

    private String title;

    private String message;

    private String negativeMsg;

    private String positiveMsg;

    private View.OnClickListener positiveListener;

    private View.OnClickListener negativeListener;


    private static final String CANCEL = "取消";
    private static final String CONFIRM = "确认";

    public MyAlertDialog(Context context, String title, String message, String negativeMsg, String positiveMsg, View.OnClickListener negativeListener, View.OnClickListener positiveListener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.negativeMsg = negativeMsg;
        this.positiveMsg = positiveMsg;
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    public MyAlertDialog(Context context, String title, String message, View.OnClickListener positiveListener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.negativeMsg = CANCEL;
        this.positiveMsg = CONFIRM;
        this.positiveListener = positiveListener;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        set = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).create();
        set.setView(view);
        btnNegative = (Button) view.findViewById(R.id.btn_delete_cancel);
        btnPositive = (Button) view.findViewById(R.id.btn_delete_confirm);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvMessage.setText(message);
        btnNegative.setText(negativeMsg);
        btnPositive.setText(positiveMsg);
        if (negativeListener == null) {
            btnNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    set.dismiss();
                }
            });
        } else {
            btnNegative.setOnClickListener(negativeListener);
        }

        btnPositive.setOnClickListener(positiveListener);
        set.show();
    }

    public void dismiss() {
        if (set != null && set.isShowing()) {
            set.dismiss();
        }
    }

}
