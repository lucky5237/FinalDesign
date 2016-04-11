package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/11.
 */
public class TakeOrderEvent {

    private String tag;

    private Integer orderId;

    public TakeOrderEvent(String tag, Integer orderId) {
        this.tag = tag;
        this.orderId = orderId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
