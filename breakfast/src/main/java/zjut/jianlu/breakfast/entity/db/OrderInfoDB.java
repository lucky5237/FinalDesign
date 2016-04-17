package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

/**
 * Created by jianlu on 16/3/12.
 * 数据库订单信息表
 */
public class OrderInfoDB extends SugarRecord {

    private String orderNumber;//订单号

    private String userImageUrl;//用户图片路径

    private Integer status;//订单状态

    private Float amount;//订单总额

    private Float bonus;//悬赏金

    private boolean isClientCommented;//下单人是否评论

    private boolean isCourierCommented;//派送员是否评论

    private String receivedTs;//接单时间

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


}
