package zjut.jianlu.breakfast.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.utils.LogUtil;
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
    public RelativeLayout mRlltNoFood;
    public Button btnLoadAgain;
    public Button btnLoadAgainFood;
    public Button btnLoadAgainOrder;



    public View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mPageName = this.getClass().getSimpleName();
        LogUtil.d(mPageName + " oncreateView is called");
        view = inflater.inflate(getLayoutId(), container, false);
        mRlltNoData = (RelativeLayout) view.findViewById(R.id.no_data_tips);
        mRlltNoNet = (RelativeLayout) view.findViewById(R.id.no_net);
        mRlltNoGoodInShopCart = (RelativeLayout) view.findViewById(R.id.no_goods_cart);
        mRlltNoOrder = (RelativeLayout) view.findViewById(R.id.no_orders);
        mRlltNoFood = (RelativeLayout) view.findViewById(R.id.no_relation_goods);
        btnLoadAgain= (Button) view.findViewById(R.id.bt_again_load);
        btnLoadAgainFood= (Button) view.findViewById(R.id.btn_again_load_food);
        btnLoadAgainOrder= (Button) view.findViewById(R.id.bt_again_load_order);

        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(mPageName + " onresume is called");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(mPageName + " onHiddenChange " + hidden);
    }

    public void ShowUI(int status) {
        mRlltNoData.setVisibility(View.VISIBLE);
        mRlltNoNet.setVisibility(View.GONE);
        mRlltNoGoodInShopCart.setVisibility(View.GONE);
        mRlltNoOrder.setVisibility(View.GONE);
        mRlltNoFood.setVisibility(View.GONE);
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
            case BreakfastConstant.NO_FOOD:
                mRlltNoFood.setVisibility(View.VISIBLE);
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

    protected Dialog dialog;

    private View loadingView;

    /**
     * 显示进度弹出框
     */
    public void showMyDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
            loadingView=view.findViewById(R.id.pd_base);
            dialog = new Dialog(mContext, R.style.dialog);
            dialog.setContentView(view, new WindowManager.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(false);
        }
        loadingView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.progress));
        dialog.show();
    }

    /**
     * 关闭进度弹出框
     */
    public void dismissMyDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

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
