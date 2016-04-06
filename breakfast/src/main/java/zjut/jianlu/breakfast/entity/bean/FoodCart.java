package zjut.jianlu.breakfast.entity.bean;

/**
 * Created by jianlu on 16/3/14.
 */
public class FoodCart {

    private int num;

    private Food food;

    public FoodCart(int num, Food food) {
        this.num = num;
        this.food = food;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
