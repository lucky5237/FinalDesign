package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/3/24.
 */
public class RegisterBody {

    private String mobile;
    private String password;
    private String username;
    private Integer gender;
    private Integer type;
    private String address;
    private String brief;

    public RegisterBody(String mobile, String password, String username, Integer gender, Integer type, String address, String brief) {
        this.mobile = mobile;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.type = type;
        this.address = address;
        this.brief = brief;
    }


}
