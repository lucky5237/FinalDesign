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
import zjut.jianlu.breakfast.entity.requestBody.MakeOrderBody;
import zjut.jianlu.breakfast.service.OrderService;
import zjut.jianlu.breakfast.utils.BreakfastUtils;

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

    private int mbuyFoodNum = 0;

    private float mbuyFoodPrice;

    private float mbuyFoodAmount = 0.0f;

    private View view;

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
        for (ConfirmFood food : mConfirmFoodlist) {
            BuyFood mfood = new BuyFood(food.getQuantity(), food.getFood().getId());
            mBuyFoodList.add(mfood);
        }

    }

    private void initView() {
        view = findViewById(R.id.main_cotainer);
        tvReceiver.setText(getCurrentUser().getUsername());
//        tvContactPhone.setText(mCurrentUser.getMobilePhoneNumber());
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
                finish();
                break;
            case R.id.btn_confirm:
                Toast("点击了确认订单");
                // TODO: 16/4/9 确认悬赏金
                makeOrder(mbuyFoodAmount, bonus, mBuyFoodList);
                break;
        }
    }

    private void makeOrder(Float amount, Float bonus, List<BuyFood> orderDetails) {
        String orderNumber = BreakfastUtils.getOutTradeNo();
        Call<BaseResponse<String>> call = orderService.makeOrder(new MakeOrderBody(orderNumber, amount, bonus, getCurrentUserID(), orderDetails));
        call.enqueue(new BaseCallback<String>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());
                // TODO: 16/4/9 下单成功之后跳转


            }

            @Override
            public void onBizFailure(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast(response.body().getMessage());

            }
        });
    }

}
