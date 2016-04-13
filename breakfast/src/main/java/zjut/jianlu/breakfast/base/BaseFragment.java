package zjut.jianlu.breakfast.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.utils.SharedPreferencesUtil;

/**
 * Created by jianlu on 16/3/12.
 */
public abstract class BaseFragment extends Fragment {
    public static Context mContext;
    private Toast mToast;
    public String mPageName;
    public RelativeLayout mRlltNoData;
    public RelativeLayout mRlltNoNet;
    public RelativeLayout mRlltNoGoodInShopCart;
    public RelativeLayout mRlltNoOrder;

    public View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mPageName = this.getClass().getSimpleName();
        view = inflater.inflate(getLayoutId(), container, false);
        mRlltNoData = (RelativeLayout) view.findViewById(R.id.no_data_tips);
        mRlltNoNet = (RelativeLayout) view.findViewById(R.id.no_net);
        mRlltNoGoodInShopCart = (RelativeLayout) view.findViewById(R.id.no_goods_cart);
        mRlltNoOrder = (RelativeLayout) view.findViewById(R.id.no_orders);

        ButterKnife.bind(this, view);
        return view;

    }

    public void ShowUI(int status) {
        mRlltNoData.setVisibility(View.VISIBLE);
        mRlltNoNet.setVisibility(View.GONE);
        mRlltNoGoodInShopCart.setVisibility(View.GONE);
        mRlltNoOrder.setVisibility(View.GONE);
        switch (status) {
            case BreakfastConstant.NO_NET:
                mRlltNoNet.setVisibility(View.VISIBLE);
                break;
            case BreakfastConstant.NO_GOOD_SHOPCART:
                mRlltNoGoodInShopCart.setVisibility(View.VISIBLE);
                break;
            case BreakfastConstant.NO_ORDER:
                mRlltNoOrder.setVisibility(View.VISIBLE);
                break;
            case BreakfastConstant.NORMAL:
                mRlltNoData.setVisibility(View.GONE);
        }


    }


    public void Toast(String content) {
        if (content != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }

    public abstract int getLayoutId();

    public static Integer getCurrentUserType() {

        return SharedPreferencesUtil.getInstance(mContext).getUserType();

    }

    public static Integer getCurrentUserID() {

        return SharedPreferencesUtil.getInstance(mContext).getUserId();

    }

    public static String getCurrentUserMobile() {

        return SharedPreferencesUtil.getInstance(mContext).getMobile();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
