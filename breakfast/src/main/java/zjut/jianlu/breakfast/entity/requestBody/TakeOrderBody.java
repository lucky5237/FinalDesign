package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/11.
 */
public class TakeOrderBody {

    private Integer orderId;

    private Integer courierUserId;

    public TakeOrderBody(Integer orderId, Integer courierUserId) {
        this.orderId = orderId;
        this.courierUserId = courierUserId;
    }
}
