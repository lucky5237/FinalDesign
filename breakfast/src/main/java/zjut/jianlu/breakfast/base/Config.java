package zjut.jianlu.breakfast.base;

/**
 * @author :smile
 * @project:Config
 * @date :2016-01-15-18:23
 */
public class Config {
    /**
     * Bmob应用key
     */
//    public static final String DEFAULT_APPKEY="d6f44e8f1ba9d3dcf4fab7a487fa97dd";//内
    public static final String DEFAULT_APPKEY="87ab0f9bee41bce86dfadd69af692873";//外
    public static final boolean DEBUG=true;
    //好友请求：已添加
    public static final int STATUS_VERIFIED=1;
    //好友请求：未读-未添加-接收到的初始状态
    public static final int STATUS_VERIFY_NONE=0;
    //好友请求：已读-未添加-点击查看了新朋友，则都变成已读状态
    public static final int STATUS_VERIFY_READED=2;
    //好友请求：拒绝
    public static final int STATUS_VERIFY_REFUSE=3;

}
