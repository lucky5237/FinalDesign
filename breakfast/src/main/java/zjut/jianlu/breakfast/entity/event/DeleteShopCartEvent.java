package zjut.jianlu.breakfast.entity.event;

import java.util.ArrayList;

import zjut.jianlu.breakfast.entity.db.ConfirmFood;

/**
 * Created by jianlu on 16/4/17.
 */
public class DeleteShopCartEvent {

    private ArrayList<ConfirmFood> foodArrayList;

    public ArrayList<ConfirmFood> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<ConfirmFood> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    public DeleteShopCartEvent(ArrayList<ConfirmFood> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }
}
