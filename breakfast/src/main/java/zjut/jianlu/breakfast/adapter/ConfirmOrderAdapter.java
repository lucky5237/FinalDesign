package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.Food;
import zjut.jianlu.breakfast.entity.OrderConfirmFoodList;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 16/3/14.
 */
public class ConfirmOrderAdapter extends BaseAdapter {


    private Context mContext;

    private Food mFood;

    private int mNum;

    private View mView;

    private List<OrderConfirmFoodList> mFoodList;

    public static Button mBtnTotal;

    public ConfirmOrderAdapter(Context context, List<OrderConfirmFoodList> mFoodList, View view) {
        mContext = context;
        this.mFoodList = mFoodList;
        mView = view;
        mBtnTotal= (Button) view.findViewById(R.id.btn_pay_money);

    }

    @Override
    public int getCount() {
        return mFoodList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_confirm_food_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        OrderConfirmFoodList orderConfirmFoodList = (OrderConfirmFoodList) getItem(position);
        mFood = orderConfirmFoodList.getFood();
        mNum = orderConfirmFoodList.getNum();
        viewHolder.tvName.setText(mFood.getName());
        viewHolder.tvPlace.setText(mFood.getPlace());
        viewHolder.tvPrice.setText(mFood.getPrice().toString());
        viewHolder.etQuantity.setText(mNum + "");
        viewHolder.tvBuyNum.setText(mNum + "");
        viewHolder.tvBuyAmount.setText(Float.valueOf(mNum * mFood.getPrice()).toString());

        final String url = mFood.getImage().getFileUrl(mContext);
        Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_launcher).resize(100, 100).centerCrop().into(viewHolder.ivImage);
        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, url);
                if (!popWindow.isShowing()) {
                    popWindow.showAtLocation(mView, Gravity.TOP, 0, 0);
                }
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFoodList.size() > 1) {
                    mFoodList.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "至少保留一个区支付", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_image)//商品图片
                ImageView ivImage;
        @Bind(R.id.tv_name)//商品名称
                TextView tvName;
        @Bind(R.id.tv_place)//购买地点
                TextView tvPlace;
        @Bind(R.id.tv_price)//商品单价
                TextView tvPrice;
        @Bind(R.id.btn_sub)
        Button btnSub;
        @Bind(R.id.et_quantity)
        EditText etQuantity;
        @Bind(R.id.btn_add)
        Button btnAdd;
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.tv_buy_num)//购买数目
                TextView tvBuyNum;
        @Bind(R.id.tv_buy_amount)//购买总额
                TextView tvBuyAmount;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnTextChanged(value = R.id.et_quantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
        void afterTextChanged(Editable s) {


            tvBuyAmount.setText(String.valueOf((float) (Float.valueOf(tvPrice.getText().toString()) * Integer.valueOf(etQuantity.getText().toString().equals("") ? "0" : etQuantity.getText().toString()))));
            etQuantity.setSelection(etQuantity.getText().length());
        }


        @OnClick({R.id.btn_add, R.id.btn_sub})
        public void onclick(View view) {

            String numberString = etQuantity.getText().toString();
            if (TextUtils.isEmpty(numberString)) {
                return;
            }
            int numberInt = Integer.valueOf(numberString);

            switch (view.getId()) {
                case R.id.btn_add:
                    if (numberInt < 99) {
                        etQuantity.setText(String.valueOf(numberInt + 1));
                        tvBuyNum.setText(String.valueOf(numberInt + 1));

                    }
                    break;
                case R.id.btn_sub:
                    if (numberInt > 1) {
                        etQuantity.setText(String.valueOf(numberInt - 1));
                        tvBuyNum.setText(String.valueOf(numberInt - 1));

                    }
                    break;

            }
            tvBuyAmount.setText(String.valueOf((float) (Float.valueOf(tvPrice.getText().toString()) * Integer.valueOf(etQuantity.getText().toString()))));
            mBtnTotal.setText("实付金额：¥"+tvBuyAmount.getText().toString());
        }

    }
}
