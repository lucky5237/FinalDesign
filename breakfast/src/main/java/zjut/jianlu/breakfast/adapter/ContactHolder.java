package zjut.jianlu.breakfast.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.entity.bean.Friend;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.utils.ViewUtil;

public class ContactHolder extends BaseViewHolder {

  @Bind(R.id.iv_recent_avatar)
  public ImageView iv_recent_avatar;
  @Bind(R.id.tv_recent_name)
  public TextView tv_recent_name;

  public ContactHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
    super(context, root, R.layout.item_contact,onRecyclerViewListener);
  }

  @Override
  public void bindData(Object o) {
      Friend friend =(Friend)o;
      User user =friend.getFriendUser();
      //会话图标
      ViewUtil.setAvatar(user==null?null:user.getAvatar(), R.mipmap.head, iv_recent_avatar);
      //会话标题
      tv_recent_name.setText(user==null?"未知":user.getUsername());
  }

}