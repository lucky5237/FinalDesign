package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.OrderComment;

/**
 * Created by jianlu on 4/18/2016.
 */
public class CommentDetailAdapter extends BaseAdapter {

    private List<OrderComment> mOrderCommentList;

    private Context mContext;

    private Integer mUserType;

    public CommentDetailAdapter(Context context, List<OrderComment> orderComments,Integer userType) {
        mContext = context;
        mOrderCommentList = orderComments;
        mUserType=userType;
    }

    @Override
    public int getCount() {
        return mOrderCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment_detail, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }


        OrderComment orderComment = (OrderComment) getItem(position);
        viewHolder.mRatingbar.setStarHalfDrawable(mContext.getResources().getDrawable(R.mipmap.ic_half_star));
        switch (mUserType){
            case 0://查看买家的个人中心

                break;
            case 1://卖家个人中心
                break;
        }

        return convertView;

    }

    static class ViewHolder {
        @Bind(R.id.iv_user_image)
        CircleImageView mIvUserImage;
        @Bind(R.id.tv_user_name)
        TextView mTvUserName;
        @Bind(R.id.ratingbar)
        RatingBar mRatingbar;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.tv_comment)
        TextView mTvComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
