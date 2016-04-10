package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/10.
 */
public class UpdateOrderStatusBody {

    private Integer status;

    private Integer userType;

    private Integer orderId;

    public UpdateOrderStatusBody(Integer status, Integer userType, Integer orderId) {
        this.status = status;
        this.userType = userType;
        this.orderId = orderId;
    }
}
