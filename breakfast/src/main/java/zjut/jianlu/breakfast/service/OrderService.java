package zjut.jianlu.breakfast.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zjut.jianlu.breakfast.base.BaseResponse;
import zjut.jianlu.breakfast.entity.bean.OrderInfo;
import zjut.jianlu.breakfast.entity.requestBody.BaseUserBody;
import zjut.jianlu.breakfast.entity.requestBody.MakeOrderBody;
import zjut.jianlu.breakfast.entity.requestBody.NewestOrderBody;
import zjut.jianlu.breakfast.entity.requestBody.TakeOrderBody;
import zjut.jianlu.breakfast.entity.requestBody.UpdateOrderStatusBody;

/**
 * Created by jianlu on 16/4/9.
 */
public interface OrderService {
    @POST("order/makeOrder")
    Call<BaseResponse<String>> makeOrder(@Body MakeOrderBody body);

    @POST("order/orderList")
    Call<BaseResponse<List<OrderInfo>>> getMyorderList(@Body BaseUserBody body);

    @POST("order/updateStatus")
    Call<BaseResponse<String>> updateOrderStatus(@Body UpdateOrderStatusBody body);

    @POST("order/newestOrder")
    Call<BaseResponse<List<OrderInfo>>> getNewestOrders(@Body NewestOrderBody body);

    @POST("order/takeOrder")
    Call<BaseResponse<String>> takeOrder(@Body TakeOrderBody body);
}
