package zjut.jianlu.breakfast.widget;

import android.content.Context;
import android.graphics.Color;

import com.jauker.widget.BadgeView;

import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 4/13/2016.
 */
public class MyBadgeView extends BadgeView {
    public MyBadgeView(Context context) {
        super(context);
        setBackgroundResource(R.mipmap.ic_red);
        setTextColor(Color.WHITE);
        setTextSize(8);
    }
}
