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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.db.ConfirmFood;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.listener.ConfirmOnclickListener;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 16/3/14.
 */
public class ShoppingCartAdapter extends BaseAdapter {


    static Context mContext;

    private Food mFood;

    private int mNum;

    private View mView;

    private List<ConfirmFood> mFoodList;
    private List<CheckBean> checkList = new ArrayList<CheckBean>();


    public ShoppingCartAdapter(Context context, List<ConfirmFood> mFoodList) {
        mContext = context;
        this.mFoodList = mFoodList;
        if (mFoodList != null && mFoodList.size() > 0) {
            for (int i = 0; i < mFoodList.size(); i++) {
                checkList.add(new CheckBean(i, true));
            }
        }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_shopping_cart, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final ConfirmFood mConfirmFood = (ConfirmFood) getItem(position);
        final Food mFood = mConfirmFood.getFood();
        final Integer number = mConfirmFood.getQuantity();
        viewHolder.tvName.setText(mFood.getName());
        viewHolder.tvPlace.setText(mFood.getPlace().getName());
        viewHolder.tvPrice.setText(mFood.getPrice().toString());
        viewHolder.etQuantity.setText(number.toString());
        viewHolder.tvTotalNum.setText("数量：" + number.toString() + "件");
        viewHolder.tvTotalFee.setText("小计：¥" + String.valueOf(Float.valueOf(number * mFood.getPrice())));
        if (checkList.get(position).isChecked) {
            viewHolder.cbCheck.setChecked(true);
        } else {
            viewHolder.cbCheck.setChecked(false);
        }


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
        viewHolder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkList.get(position).isChecked = true;
                    viewHolder.tvTotalNum.setText("数量：" + number.toString() + "件");
                    viewHolder.tvTotalFee.setText("小计：¥" + String.valueOf(Float.valueOf(number * mFood.getPrice())));
                } else {
                    checkList.get(position).isChecked = false;
                    viewHolder.tvTotalNum.setText("数量：0件");
                    viewHolder.tvTotalFee.setText("小计：¥0.0");
                }

            }
        });

        viewHolder.btnDelete.setOnClickListener(new DeleteConfirmListener(mContext, "是否确认从购物车删除该物品", position));
        return convertView;
    }

    class DeleteConfirmListener extends ConfirmOnclickListener {
        private int position;

        public DeleteConfirmListener(Context context, String messgae, int position) {
            super(context, messgae);
            this.position = position;
        }

        @Override
        public void onConfirm() {
            mFoodList.remove(position);
            checkList.remove(position);
            notifyDataSetChanged();

        }
    }

    public void setAllChecked(boolean isCheck) {
        if (isCheck) {
            for (CheckBean checkBean : checkList) {
                if (!checkBean.isChecked) {//没勾选上的话勾选上
                    checkBean.isChecked = true;
                }
            }
        } else {
            for (CheckBean checkBean : checkList) {
                if (checkBean.isChecked) {
                    checkBean.isChecked = false;
                }
            }
        }
        notifyDataSetChanged();

    }

    class CheckBean {
        private Integer position;
        private boolean isChecked;

        public CheckBean(Integer position, boolean isChecked) {
            this.position = position;
            this.isChecked = isChecked;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    static class ViewHolder {
        @Bind(R.id.iv_food_image)//商品图片
                ImageView ivImage;
        @Bind(R.id.tv_food_name)//商品名称
                TextView tvName;
        @Bind(R.id.tv_food_place)//购买地点
                TextView tvPlace;
        @Bind(R.id.tv_food_price)//商品单价
                TextView tvPrice;
        @Bind(R.id.btn_sub)
        Button btnSub;
        @Bind(R.id.et_quantity)
        EditText etQuantity;
        @Bind(R.id.btn_add)
        Button btnAdd;
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.tv_food_total_num)
        TextView tvTotalNum;
        @Bind(R.id.tv_food_total_fee)
        TextView tvTotalFee;
        @Bind(R.id.cb_check)
        CheckBox cbCheck;


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
            float price = Float.valueOf(tvPrice.getText().toString());

            switch (view.getId()) {
                case R.id.btn_add:
                    if (numberInt < 99) {
                        etQuantity.setText(String.valueOf(numberInt + 1));
                        tvTotalNum.setText("数量：" + String.valueOf(numberInt + 1) + "件");
                        tvTotalFee.setText("小计：¥" + String.valueOf(Float.valueOf((numberInt + 1) * price)));
                    }
                    break;
                case R.id.btn_sub:
                    if (numberInt > 1) {
                        etQuantity.setText(String.valueOf(numberInt - 1));
                        tvTotalNum.setText("数量：" + String.valueOf(numberInt - 1) + "件");
                        tvTotalFee.setText("小计：¥" + String.valueOf(Float.valueOf((numberInt - 1) * price)));
                    }
                    break;

            }
        }

    }
}
