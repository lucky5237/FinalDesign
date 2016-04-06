package zjut.jianlu.breakfast.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by jianlu on 16/3/12.
 */
public abstract class BaseFragment extends Fragment {
    private Context mContext;
    private Toast mToast;
    public String mPageName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mPageName = this.getClass().getSimpleName();
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;

    }


    public void Toast(String content) {
        if (content != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(content);
            }
            mToast.show();
        }
    }

    public abstract int getLayoutId();

    public String getCurrentUserMobile() {
//        User user = BmobUser.getCurrentUser(mContext, User.class);
//        if (user != null) {
//            return user.getMobilePhoneNumber();
//        }
//        return null;
        return "15757115237";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
