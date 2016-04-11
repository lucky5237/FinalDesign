package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/10.
 */
public class UpdateOrderStatusEvent {

    private Integer orderId;
    private Integer status;

    public UpdateOrderStatusEvent(Integer status, Integer orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
