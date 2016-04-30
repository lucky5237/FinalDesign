package zjut.jianlu.breakfast.service;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.entity.bean.MyUser;
import zjut.jianlu.breakfast.entity.bean.OrderComment;
import zjut.jianlu.breakfast.entity.requestBody.BaseUserBody;
import zjut.jianlu.breakfast.entity.requestBody.ChangePasswordBody;
import zjut.jianlu.breakfast.entity.requestBody.CheckMobileBody;
import zjut.jianlu.breakfast.entity.requestBody.LoginBody;
import zjut.jianlu.breakfast.entity.requestBody.ModifyProfileBody;
import zjut.jianlu.breakfast.entity.requestBody.OrderCommentListBody;
import zjut.jianlu.breakfast.entity.requestBody.RegisterBody;
import zjut.jianlu.breakfast.entity.requestBody.UserRankBody;

/**
 * Created by jianlu on 16/3/23.
 */

public interface UserService {
    @POST("user/login")
    Call<BaseResponse<MyUser>> login(@Body LoginBody body);

    @POST("user/register")
    Call<BaseResponse<String>> register(@Body RegisterBody body);

    @POST("user/checkMobile")
    Call<BaseResponse<String>> checkMobile(@Body CheckMobileBody body);

    @POST("user/changePassword")
    Call<BaseResponse<String>> changePassword(@Body ChangePasswordBody body);

    @POST("user/rank")
    Call<BaseResponse<List<MyUser>>> getUserRank(@Body UserRankBody body);

    @POST("user/userInfo")
    Call<BaseResponse<MyUser>> getUserInfo(@Body Integer userId);

    @POST("user/aveScore")
    Call<BaseResponse<Float>> getAveScore(@Body BaseUserBody body);

    @POST("user/commentList")
    Call<BaseResponse<List<OrderComment>>> getCommentList(@Body OrderCommentListBody body);

    @Multipart
    @POST("user/uploadAvatar")
    Call<BaseResponse<String>> uploadImage(@Part MultipartBody.Part file);

    @POST("user/modifyProfile")
    Call<BaseResponse<String>> modifyProfile(@Body ModifyProfileBody body);
}
