package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.OrderComment;
import zjut.jianlu.breakfast.utils.BreakfastUtils;
import zjut.jianlu.breakfast.widget.ScanPicPopWindow;

/**
 * Created by jianlu on 4/18/2016.
 */
public class CommentDetailAdapter extends BaseAdapter {

    private List<OrderComment> mOrderCommentList;

    private Context mContext;

    private Integer mUserType;

    private View mView;

    private Integer mOrderId;//寻找的orderId,如果=-1不寻找

    public Integer getmCommentPosition() {
        return mCommentPosition;
    }

    public void setmCommentPosition(Integer mCommentPosition) {
        this.mCommentPosition = mCommentPosition;
    }

    private Integer mCommentPosition;

    public CommentDetailAdapter(Context context, List<OrderComment> orderComments, Integer userType, View view, Integer orderId) {
        mContext = context;
        mOrderCommentList = orderComments;
        mUserType = userType;
        mView = view;
        mOrderId = orderId;

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

        final OrderComment orderComment = (OrderComment) getItem(position);
        if (mOrderId != -1 && orderComment.getOrderId() == mOrderId) {
            mCommentPosition = position;
            viewHolder.mLlytMain.setBackgroundColor(Color.parseColor("#B2EBF2"));
        }
        viewHolder.mRatingbar.setmClickable(false);
        switch (mUserType) {
            case 0:// 查看买家的个人中心
                viewHolder.mTvUserName.setText(orderComment.getCourierUserName());
                viewHolder.mTvTime.setText(orderComment.getClientCommentTs());
                Picasso.with(mContext).load(BreakfastUtils.getAbsAvatarUrlPath(orderComment.getCourierUserName())).into(viewHolder.mIvUserImage);
                viewHolder.mIvUserImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, BreakfastUtils.getAbsAvatarUrlPath(orderComment.getCourierUserName()));
                        if (!popWindow.isShowing()) {
                            popWindow.showAtLocation(mView, Gravity.TOP, 0, 0);
                        }
                    }
                });
                viewHolder.mRatingbar.setStar(orderComment.getClientScore());
                viewHolder.mTvScore.setText(String.valueOf(orderComment.getClientScore()));
                viewHolder.mTvComment.setText(orderComment.getClientComment());
                break;
            case 1:// 卖家个人中心
                viewHolder.mTvUserName.setText(orderComment.getClientUserName());
                viewHolder.mTvTime.setText(orderComment.getCourierCommentTs());
                final String mAvatarUrl = BreakfastUtils.getAbsAvatarUrlPath(orderComment.getCourierUserName());

                Picasso.with(mContext).load(BreakfastUtils.getAbsAvatarUrlPath(orderComment.getClientUserName())).into(viewHolder.mIvUserImage);
                viewHolder.mIvUserImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScanPicPopWindow popWindow = new ScanPicPopWindow(mContext, BreakfastUtils.getAbsAvatarUrlPath(orderComment.getClientUserName()));
                        if (!popWindow.isShowing()) {
                            popWindow.showAtLocation(mView, Gravity.TOP, 0, 0);
                        }
                    }
                });
                viewHolder.mRatingbar.setStar(orderComment.getCourierScore());
                viewHolder.mTvScore.setText(String.valueOf(orderComment.getCourierScore()));
                viewHolder.mTvComment.setText(orderComment.getCourierComment());
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
        @Bind(R.id.llyt_main)
        LinearLayout mLlytMain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
