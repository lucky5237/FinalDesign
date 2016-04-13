package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/4/10.
 */
public class BuyFood implements Serializable {

    private Integer quantity;

    private Long foodId;

    public BuyFood(Integer quantity, Long foodId) {
        this.quantity = quantity;
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }
}
