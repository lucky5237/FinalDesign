package zjut.jianlu.breakfast.enums;

/**
 * Created by jianlu on 16/4/10.
 */
public enum OrderStatus {

    WAIT_RECEIVE(0, "待接单", ""),//待卖家接单

    WAIT_DELIVERY(1, "待对方配送", "准备开始配送"),//卖家已接单，待卖家配送

    WAIT_CONFIRM(2, "对方正在配送", "待对方确认收货"),//卖家开始配送，待买家确认收货

    FINISH(3, "订单已完成", "订单已完成"),

    CANCEL(4, "订单已取消", "订单已取消");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getClientDes() {
        return clientDes;
    }

    public void setClientDes(String clientDes) {
        this.clientDes = clientDes;
    }

    public String getCourierDes() {
        return courierDes;
    }

    public void setCourierDes(String courierDes) {
        this.courierDes = courierDes;
    }

    private int code;
    private String clientDes;
    private String courierDes;

    OrderStatus(int code, String clientDes, String courierDes) {
        this.code = code;
        this.clientDes = clientDes;
        this.courierDes = courierDes;

    }

    public static String getOrderDesByCode(int code, int userType) {
        OrderStatus[] orderStatuses = OrderStatus.values();
        for (OrderStatus status : orderStatuses) {
            if (status.code == code) {
                return userType == 0 ? status.clientDes : status.courierDes;
            }
        }
        return null;
    }
}
