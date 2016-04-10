package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/3/16.
 * 订单详情（此订单购买的物品）
 */
public class OrderDetail implements Serializable {

    private Integer orderId;//对应的order_info表的id

    private Integer foodId;//对应food表的id

    private Integer quantity;//food购买的数量

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", foodId=" + foodId +
                ", quantity=" + quantity +
                '}';
    }
}
