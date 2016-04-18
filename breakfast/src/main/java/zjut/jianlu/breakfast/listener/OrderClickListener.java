package zjut.jianlu.breakfast.listener;

import android.view.View;

/**
 * Created by jianlu on 16/4/18.
 */
public class OrderClickListener implements View.OnClickListener {
    private Integer position;
    private View.OnClickListener mListener;

    public OrderClickListener(Integer position, View.OnClickListener listener) {
        this.position = position;
        mListener = listener;
    }


    @Override
    public void onClick(View v) {
        mListener.onClick(v);
    }
}
