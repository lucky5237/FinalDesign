package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.adapter.ConfirmOrderAdapter;
import zjut.jianlu.breakfast.base.BaseActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.entity.bean.FoodCart;
import zjut.jianlu.breakfast.entity.bean.User;

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
    @Bind(R.id.llyt_order_confirm_coupon)
    LinearLayout llytOrderConfirmCoupon;
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
    private Food mfood;

    private User mCurrentUser;

    private int mbuyFoodNum = 0;

    private float mbuyFoodPrice;

    private float mbuyFoodAmount = 0.0f;

    private View view;

    private ConfirmOrderAdapter adapter;
    private List<FoodCart> mFoodlist = new ArrayList<FoodCart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mfood = (Food) bundle.getSerializable("food");
            mbuyFoodNum = bundle.getInt(BreakfastConstant.BUY_FOOD_NUM_TAG);
            mbuyFoodAmount = bundle.getFloat(BreakfastConstant.BUY_FOOD_AMOUNT);
            if (mfood == null && mbuyFoodNum == 0 && mbuyFoodAmount == 0.0f) {
                Log.d("jianlu", "食品信息并未传过来");
            }

        }
        initView();
        initData();
    }

    private void initData() {
        mFoodlist.add(new FoodCart(mbuyFoodNum, mfood));
        adapter = new ConfirmOrderAdapter(mContext, mFoodlist, view);
        lvOrderConfirmation.setAdapter(adapter);

    }

    private void initView() {
        view = findViewById(R.id.main_cotainer);
        tvReceiver.setText(mCurrentUser.getUsername());
//        tvContactPhone.setText(mCurrentUser.getMobilePhoneNumber());
        tvAddress.setText(mCurrentUser.getAddress());
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
                finish();
                break;
            case R.id.btn_confirm:
                Toast("点击了确认订单");
                makeOrder(mfood, mbuyFoodNum, mbuyFoodAmount);
                break;
        }
    }

    private void makeOrder(final Food food, final int num, float amount) {
//        final OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setOrderNumber(BreakfastUtils.getOutTradeNo());
//        orderInfo.setAmount(amount);
//        orderInfo.setClientUserId(1);
//        orderInfo.setBonus(0f);
//        orderInfo.setClientCommented(false);
//        orderInfo.setCourierCommented(false);
//        orderInfo.setStatus(0);
    }

}
