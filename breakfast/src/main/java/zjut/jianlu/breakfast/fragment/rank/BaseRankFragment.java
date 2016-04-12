package zjut.jianlu.breakfast.fragment.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import zjut.jianlu.breakfast.adapter.RankAdapter;
import zjut.jianlu.breakfast.base.BaseCallback;
import zjut.jianlu.breakfast.base.BaseRefreshableFragment;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.base.MyApplication;
import zjut.jianlu.breakfast.constant.BreakfastConstant;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.requestBody.UserRankBody;
import zjut.jianlu.breakfast.service.UserService;

import static zjut.jianlu.breakfast.R.layout.fragment_bonus_rank;

/**
 * Created by jianlu on 16/3/12.
 */
public abstract class BaseRankFragment extends BaseRefreshableFragment {

    private RankAdapter adapter;

    private List<User> userList;

    public Integer userType;//默认显示买家发出的悬赏金

    public Integer flag = 0;//0-悬赏金，1-订单数

    private Retrofit retrofit;

    private UserService userService;

    private static final int SHOW_NUM = 10;

    @Override
    public int getLayoutId() {

        setUserType();
        return fragment_bonus_rank;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userList = new ArrayList<User>();
        adapter = new RankAdapter(mContext, userList, userType);
        mListView.setAdapter(adapter);
        retrofit = MyApplication.getRetrofitInstance();
        userService = retrofit.create(UserService.class);

    }

    public abstract void setUserType();

    private void getBonusRank(Integer userType, Integer flag) {
        Call<BaseResponse<List<User>>> call = userService.getUserRank(new UserRankBody(SHOW_NUM, userType, flag));
        call.enqueue(new BaseCallback<List<User>>() {
            @Override
            public void onNetFailure(Throwable t) {
                Toast(BreakfastConstant.NO_NET_MESSAGE);
            }

            @Override
            public void onBizSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                if (userList != null || userList.size() > 0) {
                    userList.clear();
                }
                userList.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
                mListView.onRefreshComplete();

            }

            @Override
            public void onBizFailure(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {

            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {

        getBonusRank(userType, flag);
    }
}