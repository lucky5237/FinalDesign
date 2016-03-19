package zjut.jianlu.breakfast.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderInfo extends BmobObject {

    private String orderNumber;

    private User clientUserId;

    private User courierUserId;

    private Integer status;

    private Float amount;

    private Float bonus;

    private boolean isClientCommented;

    private boolean isCourierCommented;

    public OrderInfo() {
        setTableName("order_info");
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

    public User getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(User clientUserId) {
        this.clientUserId = clientUserId;
    }

    public User getCourierUserId() {
        return courierUserId;
    }

    public void setCourierUserId(User courierUserId) {
        this.courierUserId = courierUserId;
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

    public BmobDate getReceivedTs() {
        return receivedTs;
    }

    public void setReceivedTs(BmobDate receivedTs) {
        this.receivedTs = receivedTs;
    }

    private BmobDate receivedTs;


    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderNumber='" + orderNumber + '\'' +
                ", clientUserId=" + clientUserId +
                ", courierUserId=" + courierUserId +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", bonus=" + bonus +
                ", isClientCommented=" + isClientCommented +
                ", isCourierCommented=" + isCourierCommented +
                ", receivedTs=" + receivedTs +
                '}';
    }
}
