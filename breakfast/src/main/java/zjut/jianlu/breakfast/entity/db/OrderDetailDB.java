package zjut.jianlu.breakfast.entity.db;

/**
 * Created by jianlu on 16/3/16.
 * 订单详情（此订单购买的物品）
 */
public class OrderDetailDB  {

    private OrderInfoDB orderInfo;

    private FoodDB food;

    private Integer quantity;

    public OrderDetailDB() {

    }

    public OrderInfoDB getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoDB orderInfo) {
        this.orderInfo = orderInfo;
    }

    public FoodDB getFood() {
        return food;
    }

    public void setFood(FoodDB food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
