package zjut.jianlu.breakfast.entity.db;

import java.io.Serializable;

import zjut.jianlu.breakfast.entity.bean.Food;

/**
 * Created by jianlu on 16/3/14.
 */

public class ConfirmFood implements Serializable {

    private Long shopCartId;

    public Long getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(Long shopCartId) {
        this.shopCartId = shopCartId;
    }

    private Integer quantity;

    private Food food;

    private float totalCost;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;


    public ConfirmFood(Integer quantity, Food food, float totalCost) {
        this.quantity = quantity;
        this.food = food;
        this.totalCost = totalCost;
    }

    public ConfirmFood(ShoppingCartDB db) {
        this.shopCartId = db.getId();
        this.quantity = db.getNum();
        this.food = new Food(db.getFoodDB());
        this.totalCost = db.getTotalCost();
        this.isChecked = db.isChecked();
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
