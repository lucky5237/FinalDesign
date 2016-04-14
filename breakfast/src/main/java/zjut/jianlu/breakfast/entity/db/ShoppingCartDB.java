package zjut.jianlu.breakfast.entity.db;

import zjut.jianlu.breakfast.entity.bean.Food;

/**
 * Created by jianlu on 16/4/13.
 */
public class ShoppingCartDB {

    private Food food;

    private Integer num;

    private float totalCost;

    public ShoppingCartDB() {

    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
