package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by jianlu on 16/3/12.
 * 数据库订单信息表
 */
public class OrderInfoDB extends SugarRecord {

    private String orderNumber;//订单号

    private UserDB clientUser;//下单用户id

    private UserDB courierUser;//派送用户id

    private Integer status;//订单状态

    private Float amount;//订单总额

    private Float bonus;//悬赏金

    private boolean isClientCommented;//下单人是否评论

    private boolean isCourierCommented;//派送员是否评论

    private Date receivedTs;//接单时间

    public OrderInfoDB() {

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

    public UserDB getClientUser() {
        return clientUser;
    }

    public void setClientUser(UserDB clientUser) {
        this.clientUser = clientUser;
    }

    public UserDB getCourierUser() {
        return courierUser;
    }

    public void setCourierUser(UserDB courierUser) {
        this.courierUser = courierUser;
    }

    public Date getReceivedTs() {
        return receivedTs;
    }

    public void setReceivedTs(Date receivedTs) {
        this.receivedTs = receivedTs;
    }

    List<OrderDetailDB> getOrderDetails() {
        return OrderDetailDB.find(OrderDetailDB.class, "orderInfo = ?", new String(String.valueOf(getId())));
    }


}
