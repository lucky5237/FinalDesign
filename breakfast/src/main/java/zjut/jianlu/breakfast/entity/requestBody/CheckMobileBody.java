package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/3/25.
 */
public class CheckMobileBody {

    private String mobile;
    private boolean isChangePwd;

    public CheckMobileBody(String mobile, boolean isChangePwd) {
        this.mobile = mobile;
        this.isChangePwd = isChangePwd;
    }
}
