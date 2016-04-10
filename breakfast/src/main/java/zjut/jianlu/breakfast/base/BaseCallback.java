package zjut.jianlu.breakfast.base;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jianlu on 4/6/2016.
 */
public abstract class BaseCallback<T> implements Callback<BaseResponse<T>> {

    private T data;
    private String message;
    private String code;
    private String url;
    private static final String ACK_CODE = "ACK";
    private static final String NACK_CODE = "NACK";




    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if (response.isSuccessful()) {
            data = response.body().getData();
            code = response.body().getCode();
            message = response.body().getMessage();
            url = call.request().url().toString();
            Log.d("jianlu", "Url: " + url);
            Log.d("jianlu", "Code: " + code);
            Log.d("jianlu", "Messgae: " + message);
            if (ACK_CODE.equals(code)) {
                if (data != null) {
                    Log.d("jianlu", "Data: " + data.toString());
                }
                onBizSuccess(call, response);
            }
            if (NACK_CODE.equals(code)) {
                onBizFailure(call, response);
            }
        }

    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
        if (t instanceof IOException) {
            Log.d("jianlu", "Error Message: " + t.getMessage());
            onNetFailure(t);

        }
    }

    public abstract void onNetFailure(Throwable t);

    public abstract void onBizSuccess(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response);

    public abstract void onBizFailure(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response);
}
