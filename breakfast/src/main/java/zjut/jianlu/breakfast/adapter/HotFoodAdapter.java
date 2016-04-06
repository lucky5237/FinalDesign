package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.Food;

/**
 * Created by jianlu on 16/3/13.
 */
public class HotFoodAdapter extends BaseAdapter {

    private List<Food> mFoodList;
    public Context mContext;
    private View mView;
    public HotFoodAdapter(Context context, List<Food> foodList, View view) {
        mContext = context;
        mFoodList = foodList;
        mView=view;
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
    public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder viewHolder=null;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_hot_food, parent, false);
//            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
//        final Food food = (Food) getItem(position);
//        viewHolder.mTvName.setText(food.getName());
//        viewHolder.mTvPlace.setText(food.getPlace());
//        viewHolder.mTvPrice.setText(food.getPrice().toString());
//        viewHolder.mTvSales.setText(food.getSales().toString());
//        viewHolder.mRealPrice.setText(food.getPrice().toString());
//
//        final String url = food.getImage().getFileUrl(mContext);
//        Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_launcher).resize(100,100).centerCrop().into(viewHolder.mIvFood);
//        viewHolder.mIvFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ScanPicPopWindow popWindow =new ScanPicPopWindow(mContext,url);
//                if (!popWindow.isShowing()){
//                    popWindow.showAtLocation(mView, Gravity.TOP,0,0);
//                }
//            }
//        });
//        viewHolder.mBtnBuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "跳转购买界面", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, MakeOrderActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putInt(BreakfastConstant.BUY_FOOD_NUM_TAG, Integer.valueOf(viewHolder.mEtQuantuty.getText().toString()));
////                bundle.putFloat(BreakfastConstant.BUY_FOOD_PRICE, food.getPrice());
////                bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, Float.valueOf(viewHolder.mTvPrice.getText().toString()));
////                intent.putExtras(bundle);
//                Bundle bundle =new Bundle();
//                bundle.putSerializable("food",food);
//                bundle.putInt(BreakfastConstant.BUY_FOOD_NUM_TAG, Integer.valueOf(viewHolder.mEtQuantuty.getText().toString()));
//                bundle.putFloat(BreakfastConstant.BUY_FOOD_AMOUNT, Float.valueOf(viewHolder.mTvPrice.getText().toString()));
//
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//            }});
        return convertView;


    }

    static class ViewHolder {
//        @Bind(R.id.tv_name)
//        TextView mTvName;
//        @Bind(R.id.tv_place)
//        TextView mTvPlace;
//        @Bind(R.id.tv_price)
//        TextView mTvPrice;
//        @Bind(R.id.tv_sales)
//        TextView mTvSales;
//        @Bind(R.id.iv_image)
//        ImageView mIvFood;
//        @Bind(R.id.btn_add)
//        Button mBtnAdd;
//        @Bind(R.id.btn_sub)
//        Button mBtnSub;
//        @Bind(R.id.btn_buy)
//        Button mBtnBuy;
//        @Bind(R.id.real_price)
//        TextView mRealPrice;
//        @Bind(R.id.et_quantity)
//        EditText mEtQuantuty;
//        @OnTextChanged(value = R.id.et_quantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//        void afterTextChanged(Editable s) {
//
//
//                mTvPrice.setText(String.valueOf((float) (Float.valueOf(mRealPrice.getText().toString()) * Integer.valueOf(mEtQuantuty.getText().toString().equals("")?"0":mEtQuantuty.getText().toString()))));
//                mEtQuantuty.setSelection(mEtQuantuty.getText().length());
//        }
//
//
//
//        @OnClick({R.id.btn_add,R.id.btn_sub})
//        public void onclick(View view){
//
//            String numberString =mEtQuantuty.getText().toString();
//            if (TextUtils.isEmpty(numberString)){
//                return;
//            }
//            int numberInt=Integer.valueOf(numberString);
//
//            switch (view.getId()){
//                case R.id.btn_add:
//                    if (numberInt<99){
//                        mEtQuantuty.setText(String.valueOf(numberInt+1));
//                    }
//                    break;
//                case R.id.btn_sub:
//                    if (numberInt>1){
//                        mEtQuantuty.setText(String.valueOf(numberInt-1));
//                    }
//                    break;
//
//            }
//            mTvPrice.setText(String.valueOf((float)(Float.valueOf(mRealPrice.getText().toString())*Integer.valueOf(mEtQuantuty.getText().toString()))));
//        }
//
//
//
//        public ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//
//        }


    }
}
