package zjut.jianlu.breakfast.widget;

import android.content.Context;
import android.view.View;

/**
 * Created by jianlu on 16/4/13.
 */
public class ShoppingCartDialog extends MyAlertDialog {

    private static final String TITLE = "加入购物车成功";
    private static final String NEGATIVE_MSG = "继续购买";
    private static final String POSITIVE_MSG = "去结算";


    public ShoppingCartDialog(Context context, String message, View.OnClickListener positiveListener) {
        super(context, TITLE, message, NEGATIVE_MSG, POSITIVE_MSG, positiveListener);
    }
}
