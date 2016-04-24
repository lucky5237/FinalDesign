package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/22.
 */
public class ModifyProfileBody {
    private Integer userId;
    private Integer gender;
    private String address;

    public ModifyProfileBody(Integer userId, Integer gender, String address) {
        this.userId = userId;
        this.gender = gender;
        this.address = address;
    }
}
