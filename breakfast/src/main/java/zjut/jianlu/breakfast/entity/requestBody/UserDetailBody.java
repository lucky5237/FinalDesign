package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 4/19/2016.
 */
public class UserDetailBody extends BaseUserBody {

    private Integer number;// 评论返回的条数

    public UserDetailBody(Integer userId, Integer userType, Integer number) {
        super(userId, userType);
        this.number = number;
    }
}
