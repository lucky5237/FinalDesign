package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

import zjut.jianlu.breakfast.entity.db.ConfirmFood;

/**
 * Created by jianlu on 16/3/12.
 */
public class OrderInfo implements Serializable {

    private Integer id;

    private String orderNumber;//订单号

    private MyUser clientUser;//下单用户

    private MyUser courierUser;//派送用户

    private Integer status;//订单状态

    private Float amount;//订单总额

    private Float bonus;//悬赏金

    private Integer isClientCommented;//下单人是否评论

    private Integer isCourierCommented;//派送员是否评论

    private String receivedTs;//接单时间

    private String deliveryTs;//开始配送时间

    private String finishTs;//订单完成时间

    private String cancelTs;//订单取消时间

    private String createTs;//下单时间

    private Integer cancelUserType;//取消订单的用户类型

    private ArrayList<ConfirmFood> orderdetails;

    public String getDeliveryTs() {
        return deliveryTs;
    }

    public void setDeliveryTs(String deliveryTs) {
        this.deliveryTs = deliveryTs;
    }

    public String getFinishTs() {
        return finishTs;
    }

    public void setFinishTs(String finishTs) {
        this.finishTs = finishTs;
    }

    public String getCancelTs() {
        return cancelTs;
    }

    public void setCancelTs(String cancelTs) {
        this.cancelTs = cancelTs;
    }

    public Integer getCancelUserType() {
        return cancelUserType;
    }

    public void setCancelUserType(Integer cancelUserType) {
        this.cancelUserType = cancelUserType;
    }

    public ArrayList<ConfirmFood> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(ArrayList<ConfirmFood> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIsClientCommented() {
        return isClientCommented;
    }

    public void setIsClientCommented(Integer isClientCommented) {
        this.isClientCommented = isClientCommented;
    }

    public Integer getIsCourierCommented() {
        return isCourierCommented;
    }

    public void setIsCourierCommented(Integer isCourierCommented) {
        this.isCourierCommented = isCourierCommented;
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

    public MyUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(MyUser clientUser) {
        this.clientUser = clientUser;
    }

    public MyUser getCourierUser() {
        return courierUser;
    }

    public void setCourierUser(MyUser courierUser) {
        this.courierUser = courierUser;
    }
}
