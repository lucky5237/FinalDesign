package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/9.
 */
public class BaseUserBody {

    private Integer userId;

    private Integer userType;

    public BaseUserBody(Integer userId, Integer userType) {
        this.userId = userId;
        this.userType = userType;
    }
}
