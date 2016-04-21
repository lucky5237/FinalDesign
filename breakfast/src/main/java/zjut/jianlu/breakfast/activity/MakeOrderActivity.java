package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.ConfirmOrderAdapter;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.BuyFood;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.entity.db.ShoppingCartDB;
import zjut.jianlu.breakfast.entity.event.ChangeIndexEvent;
import zjut.jianlu.breakfast.entity.event.DeleteShopCartEvent;
import zjut.jianlu.breakfast.entity.event.UpdateBadgeNumEvent;
import zjut.jianlu.breakfast.entity.requestBody.MakeOrderBody;
import zjut.jianlu.breakfast.service.OrderService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.MyAlertDialog;

/**
 * Created by jianlu on 16/3/12.
 */
public class MakeOrderActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_info_error)
    TextView tvInfoError;
    @Bind(R.id.tv_receiver)
    TextView tvReceiver;
    @Bind(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_order_confirm_shoppingcart)
    TextView tvOrderConfirmShoppingcart;
    @Bind(R.id.lv_order_confirmation)
    ListView lvOrderConfirmation;
    @Bind(R.id.llyt_order_confirm_gift)
    LinearLayout llytOrderConfirmGift;
    @Bind(R.id.rbtn_delivery_pay)
    RadioButton rbtnDeliveryPay;
    @Bind(R.id.rbtn_delivery_pos)
    RadioButton rbtnDeliveryPos;
    @Bind(R.id.rbtn_online_pay)
    RadioButton rbtnOnlinePay;
    @Bind(R.id.rg_order_confirm)
    RadioGroup rgOrderConfirm;
    @Bind(R.id.btn_pay_money)
    Button btnPayMoney;
    @Bind(R.id.tv_order_confirm_discount)
    TextView tvOrderConfirmDiscount;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private int mbuyFoodNum = 0;

    private float mbuyFoodPrice;

    private float mbuyFoodAmount = 0.0f;

    private View view;

    private static int fromWhere;//从哪里跳转的  0-首页下单直接购买或者直接再来一单  1-购物车点击结算

    private static final String GIVE_UP_ORDER_TITLE = "是否确认放弃下单";

    private static final String GIVE_UP_ORDER_MESSAGE = "放弃下单的食品可重新在购物车内选购";


    private ConfirmOrderAdapter adapter;
    private ArrayList<ConfirmFood> mConfirmFoodlist = new ArrayList<ConfirmFood>();
    private List<BuyFood> mBuyFoodList = new ArrayList<BuyFood>();
    private Float bonus = 0.0f;

    private Retrofit retrofit;

    private OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mConfirmFoodlist = (ArrayList<ConfirmFood>) bundle.getSerializable("foodList");
            mbuyFoodAmount = bundle.getFloat(BreakfastConstant.BUY_FOOD_AMOUNT);
            bonus = bundle.getFloat(BreakfastConstant.BUY_FOOD_BONUS);
            fromWhere = bundle.getInt("from");
            if (mbuyFoodNum == 0 && mbuyFoodAmount == 0.0f) {
                Log.d("jianlu", "食品信息并未传过来");
            }

        }
        initView();
        initData();
    }

    private void initData() {
        retrofit = MyApplication.getRetrofitInstance();
        orderService = retrofit.create(OrderService.class);
        adapter = new ConfirmOrderAdapter(mContext, mConfirmFoodlist, view);
        lvOrderConfirmation.setAdapter(adapter);
        if (mBuyFoodList != null) {
            mBuyFoodList.clear();
        }

        for (ConfirmFood food : mConfirmFoodlist) {
            BuyFood mfood = new BuyFood(food.getQuantity(), food.getFood().getId());
            mBuyFoodList.add(mfood);
        }

    }

    private void initView() {
        view = findViewById(R.id.main_cotainer);
        tvReceiver.setText(getCurrentUser().getUsername());
        tvContactPhone.setText(getCurrentUser().getMobile());
        tvAddress.setText(getCurrentUser().getAddress());
        btnPayMoney.setText("实付金额：¥" + String.valueOf(mbuyFoodAmount));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_order;

    }

    @OnClick({R.id.iv_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (fromWhere == 2) {
                    finish();
                    return;
                }
                MyAlertDialog dialog = new MyAlertDialog(mContext, GIVE_UP_ORDER_TITLE, GIVE_UP_ORDER_MESSAGE, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fromWhere == 0) {//主页直接下单，放弃支付的话需要加入到购物车
                            int updateNum = 0;
                            for (ConfirmFood food : mConfirmFoodlist) {
                                ShoppingCartDB db = ShoppingCartDB.findById(ShoppingCartDB.class, food.getShopCartId());
                                if (db != null) {//购物车如果已经存在该商品的话，那么加上购买数目
                                    db.setNum(db.getNum() + food.getQuantity());
                                    db.setTotalCost(db.getTotalCost() + food.getTotalCost());
                                    db.save();
                                } else {
                                    ShoppingCartDB newDb = new ShoppingCartDB(food);
                                    newDb.save();
                                    updateNum = 1;
                                }
                                EventBus.getDefault().post(new UpdateBadgeNumEvent(updateNum));
                            }
                        }
                        finish();
                    }
                });
                dialog.show();
                break;
            case R.id.btn_confirm:
                // TODO: 16/4/9 确认悬赏金
                makeOrder(mbuyFoodAmount, bonus, mBuyFoodList);
                break;
        }
    }

    private void makeOrder(Float amount, Float bonus, List<BuyFood> orderDetails) {
        showMyDialog();
        String orderNumber = BreakfastUtils.getOutTradeNo();
        Call<BaseResponse<String>> call = orderService.makeOrder(new MakeOrderBody(orderNumber, amount, bonus, getCurrentUserID(), orderDetails));
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onFinally() {
                dismissMyDialog();
            }

            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                if (fromWhere == 1) {
                    EventBus.getDefault().post(new DeleteShopCartEvent(mConfirmFoodlist));
                }
                EventBus.getDefault().post(new ChangeIndexEvent(MainActivicy.ORDER_INDEX));
                finish();

            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (fromWhere == 2) {
                finish();
                return true;
            }

            MyAlertDialog dialog = new MyAlertDialog(mContext, GIVE_UP_ORDER_TITLE, GIVE_UP_ORDER_MESSAGE, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fromWhere == 0) {//主页直接下单，放弃支付的话需要加入到购物车
                        int updateNum = 0;
                        for (ConfirmFood food : mConfirmFoodlist) {
                            ShoppingCartDB db = ShoppingCartDB.findById(ShoppingCartDB.class, food.getShopCartId());
                            if (db != null) {//购物车如果已经存在该商品的话，那么加上购买数目
                                db.setNum(db.getNum() + food.getQuantity());
                                db.setTotalCost(db.getTotalCost() + food.getTotalCost());
                                db.save();
                            } else {
                                ShoppingCartDB newDb = new ShoppingCartDB(food);
                                newDb.save();
                                updateNum = 1;
                            }
                            EventBus.getDefault().post(new UpdateBadgeNumEvent(updateNum));
                        }
                    }
                    finish();
                }
            });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
