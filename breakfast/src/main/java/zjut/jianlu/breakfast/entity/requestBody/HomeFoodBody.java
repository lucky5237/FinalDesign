package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/12.
 */
public class HomeFoodBody {

    private Integer number;

    private Integer placeId;

    private Integer flag;

    public HomeFoodBody(Integer number, Integer placeId, Integer flag) {
        this.number = number;
        this.placeId = placeId;
        this.flag = flag;
    }
}
