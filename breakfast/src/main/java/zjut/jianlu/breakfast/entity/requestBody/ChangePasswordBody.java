package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/3/25.
 */
public class ChangePasswordBody {

    private String mobile;

    private String password;

    public ChangePasswordBody(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
