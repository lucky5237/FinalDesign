package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/4/10.
 */
public class BuyFood implements Serializable {

    private Integer quantity;

    private Integer foodId;

    public BuyFood(Integer quantity, Integer foodId) {
        this.quantity = quantity;
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }
}
