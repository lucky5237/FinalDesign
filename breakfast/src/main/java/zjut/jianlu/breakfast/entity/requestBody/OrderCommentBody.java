package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 4/18/2016.
 */
public class OrderCommentBody extends BaseUserBody {

    private Integer score;

    private String comment;

    private Integer orderId;

    private String userName;

    private Integer theOtherUserId;

    private String theOtherUserName;

    public OrderCommentBody(Integer userId, Integer userType, Integer score, String comment, Integer orderId, String userName, Integer otherUserId, String otherUserName) {
        super(userId, userType);
        this.score = score;
        this.comment = comment;
        this.orderId = orderId;
        this.userName = userName;
        this.theOtherUserId = otherUserId;
        this.theOtherUserName = otherUserName;
    }


}
