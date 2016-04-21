package zjut.jianlu.breakfast.listener;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 16/4/21.
 */
public class OnFoodImageClickedListener implements View.OnClickListener {

    private String userName;

    private View view;

    private Context context;


    public OnFoodImageClickedListener(Context context, String userName, View view) {
        this.context = context;
        this.userName = userName;
        this.view = view;
    }


    @Override
    public void onClick(View v) {
        ScanPicPopWindow popWindow = new ScanPicPopWindow(context, BreakfastUtils.getAbsImageUrlPath(userName));
        if (!popWindow.isShowing()) {
            popWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        }
    }
}
