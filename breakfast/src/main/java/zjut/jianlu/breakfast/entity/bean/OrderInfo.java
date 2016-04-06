package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderInfo implements Serializable {

    private String orderNumber;//订单号

    private User clientUser;//下单用户

    private User courierUser;//派送用户

    private Integer status;//订单状态

    private Float amount;//订单总额

    private Float bonus;//悬赏金

    private boolean isClientCommented;//下单人是否评论

    private boolean isCourierCommented;//派送员是否评论

    private String receivedTs;//接单时间

    private String createTs;//下单时间

    private List<FoodCart> foodList;

    public List<FoodCart> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodCart> foodList) {
        this.foodList = foodList;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public boolean isClientCommented() {
        return isClientCommented;
    }

    public void setClientCommented(boolean clientCommented) {
        isClientCommented = clientCommented;
    }

    public boolean isCourierCommented() {
        return isCourierCommented;
    }

    public void setCourierCommented(boolean courierCommented) {
        isCourierCommented = courierCommented;
    }

    public String getReceivedTs() {
        return receivedTs;
    }

    public void setReceivedTs(String receivedTs) {
        this.receivedTs = receivedTs;
    }


    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public User getClientUser() {
        return clientUser;
    }

    public void setClientUser(User clientUser) {
        this.clientUser = clientUser;
    }

    public User getCourierUser() {
        return courierUser;
    }

    public void setCourierUser(User courierUser) {
        this.courierUser = courierUser;
    }
}
