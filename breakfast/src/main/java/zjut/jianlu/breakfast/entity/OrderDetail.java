package zjut.jianlu.breakfast.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by jianlu on 16/3/16.
 */
public class OrderDetail extends BmobObject {

    private OrderInfo orderId;

    private Food foodId;

    private Integer number;


    public OrderInfo getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderInfo orderId) {
        this.orderId = orderId;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }



    public OrderDetail() {
        setTableName("order_detail");
    }
}
