package zjut.jianlu.breakfast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.ShoppingCartAdapter;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;

/**
 * Created by jianlu on 16/3/12.
 */
public class ShopCartFragment extends BaseRefreshableFragment {
    @Bind(R.id.cb_shopping_cart_total_check)
    CheckBox cbShoppingCartTotalCheck;
    @Bind(R.id.tv_shopping_cart_total_cost)
    TextView tvShoppingCartTotalCost;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_cart;
    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

    }

    private ShoppingCartAdapter adapter;

    private List<ConfirmFood> foodList;


    private static final int SHOW_NUM = 10;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (ConfirmFood.count(ConfirmFood.class) == 0) {//说明本地没保存的购物车数据
//            ShowUI(BreakfastConstant.NO_GOOD_SHOPCART);
//        } else {
//            foodList = ConfirmFood.listAll(ConfirmFood.class);
//            initData();
//        }


    }

    private void initData() {

        adapter = new ShoppingCartAdapter(mContext, foodList);
        mListView.setAdapter(adapter);
        cbShoppingCartTotalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapter.setAllChecked(isChecked);

            }
        });
    }


    @OnClick(R.id.tv_shopping_cart_total_cost)
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_shopping_cart_total_cost:
                break;
        }
    }
}
