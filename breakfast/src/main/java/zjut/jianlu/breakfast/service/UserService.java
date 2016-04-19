package zjut.jianlu.breakfast.service;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.entity.bean.User;
import zjut.jianlu.breakfast.entity.bean.UserInfo;
import zjut.jianlu.breakfast.entity.requestBody.ChangePasswordBody;
import zjut.jianlu.breakfast.entity.requestBody.CheckMobileBody;
import zjut.jianlu.breakfast.entity.requestBody.LoginBody;
import zjut.jianlu.breakfast.entity.requestBody.RegisterBody;
import zjut.jianlu.breakfast.entity.requestBody.UserDetailBody;
import zjut.jianlu.breakfast.entity.requestBody.UserRankBody;

/**
 * Created by jianlu on 16/3/23.
 */

public interface UserService {
    @POST("user/login")
    Call<BaseResponse<User>> login(@Body LoginBody body);

    @POST("user/register")
    Call<BaseResponse<String>> register(@Body RegisterBody body);

    @POST("user/checkMobile")
    Call<BaseResponse<String>> checkMobile(@Body CheckMobileBody body);

    @POST("user/changePassword")
    Call<BaseResponse<String>> changePassword(@Body ChangePasswordBody body);

    @POST("user/rank")
    Call<BaseResponse<List<User>>> getUserRank(@Body UserRankBody body);

    @POST("user/userInfo")
    Call<BaseResponse<UserInfo>> getUserInfo(@Body UserDetailBody body);

    @Multipart
    @POST("user/uploadAvatar")
    Call<BaseResponse<String>> uploadImage(@Part("fileName") String description,
                             @Part("file\"; filename=\"image.png\"")RequestBody imgs);
}
