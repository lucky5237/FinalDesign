package zjut.jianlu.breakfast.entity.db;

import java.io.Serializable;

import zjut.jianlu.breakfast.entity.bean.Food;

/**
 * Created by jianlu on 16/3/14.
 */

public class ConfirmFood  implements Serializable {

    private Integer quantity;

    private Food food;

    private float totalCost;


    public ConfirmFood(Integer quantity, Food food, float totalCost) {
        this.quantity = quantity;
        this.food = food;
        this.totalCost = totalCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
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
