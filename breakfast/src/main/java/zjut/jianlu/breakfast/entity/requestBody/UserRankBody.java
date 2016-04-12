package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 4/12/2016.
 */
public class UserRankBody {

    private Integer number;

    private Integer type;

    private Integer flag;//0-悬赏金 1-订单数

    public UserRankBody(Integer number, Integer type, Integer flag) {
        this.number = number;
        this.type = type;
        this.flag = flag;
    }
}
