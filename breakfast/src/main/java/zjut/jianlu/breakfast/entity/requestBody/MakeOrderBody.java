package zjut.jianlu.breakfast.entity.requestBody;

import java.util.List;

import zjut.jianlu.breakfast.entity.bean.BuyFood;

/**
 * Created by jianlu on 16/4/9.
 */
public class MakeOrderBody {

    private String orderNumber;
    private Float amount;
    private Float bonus;
    private Integer clientUserId;
    private List<BuyFood> orderDetails;

    public MakeOrderBody(String orderNumber, Float amount, Float bonus, Integer clientUserId, List<BuyFood> orderDetails) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.bonus = bonus;
        this.clientUserId = clientUserId;
        this.orderDetails = orderDetails;
    }

}
