package zjut.jianlu.breakfast.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.MakeOrderActivity;
import zjut.jianlu.breakfast.adapter.ShoppingCartAdapter;
import zjut.jianlu.breakfast.base.BaseFragment;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.entity.db.ShoppingCartDB;
import zjut.jianlu.breakfast.entity.event.DeleteShopCartEvent;
import zjut.jianlu.breakfast.entity.event.UpdateBadgeNumEvent;
import zjut.jianlu.breakfast.entity.event.UpdatePayMoneyEvent;

/**
 * Created by jianlu on 16/3/12.
 */
public class ShopCartFragment extends BaseFragment {
    @Bind(R.id.cb_shopping_cart_total_check)
    CheckBox cbShoppingCartTotalCheck;
    @Bind(R.id.tv_shopping_cart_total_cost)
    TextView tvShoppingCartTotalCost;
    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.llyt_shopping_cart_total_cost)
    LinearLayout mLlytPay;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_cart;
    }


    public ShoppingCartAdapter adapter;

    private List<ConfirmFood> foodList;

    private ArrayList<ConfirmFood> mBuyFoodList;

    private List<ShoppingCartDB> shoppingCartList;

    public List<ConfirmFood> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<ConfirmFood> foodList) {
        this.foodList = foodList;
    }

    private static final int SHOW_NUM = 10;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        foodList = new ArrayList<ConfirmFood>();
        initData();
        adapter = new ShoppingCartAdapter(mContext, foodList);
        mListView.setAdapter(adapter);
        cbShoppingCartTotalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapter.setAllChecked(isChecked);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter.getmFoodList() != null && adapter.getmFoodList().size() > 0) {
            if (ShoppingCartDB.count(ShoppingCartDB.class) > 0) {
                ShoppingCartDB.deleteAll(ShoppingCartDB.class);
            }
            for (ConfirmFood food : adapter.getmFoodList()) {
                ShoppingCartDB db = new ShoppingCartDB(food);
                db.save();
            }
        }


    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void updatePayMoney(UpdatePayMoneyEvent event) {
        float money = event.getMoney();
        tvShoppingCartTotalCost.setText(String.valueOf(money));

    }

    /**
     * 购买成功后，删除购物车中改商品
     *
     * @param event
     */
    @Subscribe
    public void deleteShopCart(DeleteShopCartEvent event) {
        ArrayList<ConfirmFood> mList = event.getFoodArrayList();
        if (mList != null && mList.size() > 0) {
            for (ConfirmFood food : mList) {
                List<ShoppingCartDB> list = ShoppingCartDB.find(ShoppingCartDB.class, "food_id = ?", food.getFood().getId().toString());
                if (list != null && list.size() > 0) {
                    ShoppingCartDB db = list.get(0);
                    db.delete();
                }
            }
            EventBus.getDefault().post(new UpdateBadgeNumEvent(-mList.size()));
        }

    }

    public void initData() {
        float sum = 0.0f;
        if (ShoppingCartDB.count(ShoppingCartDB.class) > 0) {//说明本地没保存的购物车数据
            ShowUI(BreakfastConstant.NORMAL);
            shoppingCartList = ShoppingCartDB.listAll(ShoppingCartDB.class);
            if (foodList != null) {
                foodList.clear();
            }

            for (ShoppingCartDB db : shoppingCartList) {
                ConfirmFood food = new ConfirmFood(db);
                foodList.add(food);
                if (food.isChecked()) {
                    sum += food.getTotalCost();
                }
            }

        } else {
            ShowUI(BreakfastConstant.NO_GOOD_SHOPCART);
        }

        tvShoppingCartTotalCost.setText(String.valueOf(sum));
    }


    @OnClick(R.id.llyt_shopping_cart_total_cost)
    public void onClick(View view) {
        mBuyFoodList = new ArrayList<ConfirmFood>();
        for (ConfirmFood food : adapter.getmFoodList()) {
            if (food.isChecked()) {
                mBuyFoodList.add(food);
            }
        }
        if (mBuyFoodList.size() == 0) {
            Toast("请先选择要购买的食品");
            return;
        }
        Intent intent = new Intent(mContext, MakeOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("foodList", mBuyFoodList);
        bundle.putInt("from", 1);
        bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, adapter.getAllMoney());
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }


}
