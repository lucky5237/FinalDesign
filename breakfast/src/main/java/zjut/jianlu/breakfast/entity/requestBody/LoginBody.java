package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/3/23.
 */
public class LoginBody {

    private String mobile;

    private String password;

    public LoginBody(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
