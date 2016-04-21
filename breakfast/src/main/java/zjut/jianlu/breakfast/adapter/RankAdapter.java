package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.activity.MainActivity;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.listener.OnAvatarClickListener;
import zjut.jianlu.breakfast.utils.BreakfastUtils;

/**
 * Created by jianlu on 4/12/2016.
 */
public class RankAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> userList;
    private Integer userType;

    public RankAdapter(Context context, List<User> users, Integer userType) {
        mContext = context;
        userList = users;
        this.userType = userType;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_rank_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = (User) getItem(position);
        String genderChinese = user.getGender() == 1 ? "他" : "她";
        Picasso.with(mContext).load(BreakfastUtils.getAbsAvatarUrlPath(user.getUsername())).into(viewHolder.ivUserImage);
        viewHolder.ivUserImage.setOnClickListener(new OnAvatarClickListener(mContext, user.getUsername(), MainActivity.getInstance().getmLlytMainContainer()));
        switch (userType) {
            case 0:
                viewHolder.tvUserName.setText(user.getUsername());
                viewHolder.tvBonus.setText(genderChinese + "共悬赏了¥" + user.getBonus());
                viewHolder.tvUserOrderNum.setText(genderChinese + "共下了" + user.getOrderNum() + "次单");
                viewHolder.ivBonus.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, "ic_bonus"));
                viewHolder.ivOrderNum.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, "ic_user_order"));
                break;
            case 1:
                viewHolder.tvUserName.setText(user.getUsername());
                viewHolder.tvBonus.setText(genderChinese + "共获取了¥" + user.getBonus());
                viewHolder.tvUserOrderNum.setText(genderChinese + "成功接了" + user.getOrderNum() + "次单");
                viewHolder.ivBonus.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, "ic_user_money"));
                viewHolder.ivOrderNum.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, "ic_user_delivery"));
                break;
        }
        if (position == 0 || position == 1 || position == 2) {
            String imageName = "ic_rank_" + (position + 1);
            viewHolder.ivRank.setVisibility(View.VISIBLE);
            viewHolder.ivRank.setImageResource(BreakfastUtils.getDrawableIdByName(mContext, imageName));
        } else {
            viewHolder.ivRank.setVisibility(View.GONE);
        }


        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.iv_user_image)
        CircleImageView ivUserImage;
        @Bind(R.id.iv_user)
        ImageView ivUser;
        @Bind(R.id.tv_user_name)
        TextView tvUserName;
        @Bind(R.id.iv_bonus)
        ImageView ivBonus;
        @Bind(R.id.tv_bonus)
        TextView tvBonus;
        @Bind(R.id.iv_order_num)
        ImageView ivOrderNum;
        @Bind(R.id.tv_user_order_num)
        TextView tvUserOrderNum;
        @Bind(R.id.iv_rank)
        ImageView ivRank;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
