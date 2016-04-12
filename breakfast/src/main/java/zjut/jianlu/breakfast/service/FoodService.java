package zjut.jianlu.breakfast.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.entity.bean.Food;
import zjut.jianlu.breakfast.entity.requestBody.FoodRankBody;
import zjut.jianlu.breakfast.entity.requestBody.HomeFoodBody;

/**
 * Created by jianlu on 16/4/9.
 */
public interface FoodService {

    @POST("food/salesRank")
    Call<BaseResponse<List<Food>>> getSalesRank(@Body FoodRankBody body);

    @POST("food/allFood")
    Call<BaseResponse<List<Food>>> getAllFood(@Body HomeFoodBody body);

}
