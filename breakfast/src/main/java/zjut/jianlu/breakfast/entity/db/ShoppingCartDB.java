package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

/**
 * Created by jianlu on 16/4/13.
 */
public class ShoppingCartDB extends SugarRecord {

    private Long foodId;

    private Integer num;

    private float totalCost;

    private boolean isChecked = true;

    public ShoppingCartDB() {

    }

    public ShoppingCartDB(Long foodId, Integer num, float totalCost) {
        this.foodId = foodId;
        this.num = num;
        this.totalCost = totalCost;
    }

    public ShoppingCartDB(ConfirmFood food) {
        foodId = food.getFood().getId();
        num = food.getQuantity();
        totalCost = food.getTotalCost();
        isChecked = food.isChecked();

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
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

    public FoodDB getFoodDB() {
        return FoodDB.find(FoodDB.class, "FOOD_ID = ?", foodId.toString()).get(0);
    }
}
