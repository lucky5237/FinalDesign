package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/3/14.
 */

public class ConfirmFood implements Serializable {

    private Integer quantity;

    private Food food;

    public ConfirmFood(Integer quantity, Food food) {
        this.quantity = quantity;
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }


}
