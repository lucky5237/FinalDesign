package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.MakeOrderActivity;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.ConfirmFood;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 16/3/14.
 */
public class HomeFoodAdapter extends BaseAdapter {


    static Context mContext;

    private Food mFood;

    private int mNum;

    private View mView;

    private List<Food> mFoodList;


    public HomeFoodAdapter(Context context, List<Food> mFoodList) {
        mContext = context;
        this.mFoodList = mFoodList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_food, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final Food mFood = (Food) getItem(position);
        viewHolder.tvName.setText(mFood.getName());
        viewHolder.tvPlace.setText(mFood.getPlace().getName());
        viewHolder.tvPrice.setText(mFood.getPrice().toString());
        viewHolder.tvSales.setText(mFood.getSales());
        viewHolder.tvTime.setText(mFood.getCreateTs());
        viewHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(viewHolder.etQuantity.getText().toString())) {
                    Toast.makeText(mContext, "请先选择购买的数目", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(mContext, "跳转购买界面", Toast.LENGTH_SHORT).show();
                ArrayList<ConfirmFood> foodCarts = new ArrayList<ConfirmFood>();
                foodCarts.add(new ConfirmFood(Integer.valueOf(viewHolder.etQuantity.getText().toString()), mFood));
                //添加其他购买的食品
                Intent intent = new Intent(mContext, MakeOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodList", foodCarts);
                bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, mFood.getPrice());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        final String url = mFood.getImage();
        Picasso.with(mContext).load(BreakfastUtils.getAbsImageUrlPath(url)).placeholder(R.mipmap.ic_launcher).resize(100, 100).centerCrop().into(viewHolder.ivImage);
        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, url);
                if (!popWindow.isShowing()) {
                    popWindow.showAtLocation(mView, Gravity.TOP, 0, 0);
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
        @Bind(R.id.tv_create_ts)
        TextView tvTime;
        @Bind(R.id.tv_sales)
        TextView tvSales;
        @Bind(R.id.btn_sub)
        Button btnSub;
        @Bind(R.id.et_quantity)
        EditText etQuantity;
        @Bind(R.id.btn_add)
        Button btnAdd;
        @Bind(R.id.btn_buy)
        Button btnBuy;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnTextChanged(value = R.id.et_quantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
        void afterTextChanged(Editable s) {
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
                    }
                    break;
                case R.id.btn_sub:
                    if (numberInt > 1) {
                        etQuantity.setText(String.valueOf(numberInt - 1));
                    }
                    break;

            }
        }

    }
}
