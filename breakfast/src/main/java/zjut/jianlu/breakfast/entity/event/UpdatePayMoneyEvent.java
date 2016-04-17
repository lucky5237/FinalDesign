package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/16.
 */
public class UpdatePayMoneyEvent {


    private float money;

    public UpdatePayMoneyEvent(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
