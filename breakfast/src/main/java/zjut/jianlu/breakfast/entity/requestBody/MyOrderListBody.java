package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/23.
 */
public class MyOrderListBody extends BaseUserBody {

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    public MyOrderListBody(Integer userId, Integer userType, Integer status) {
        super(userId, userType);
        this.status = status;
    }


}
