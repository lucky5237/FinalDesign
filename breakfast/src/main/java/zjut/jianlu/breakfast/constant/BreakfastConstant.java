package zjut.jianlu.breakfast.constant;

/**
 * Created by jianlu on 3/9/2016.
 */
public class BreakfastConstant {

    public static final String SHARE_SDK_APP_KEY = "103258cfcd9de";
    public static final String SHARE_SDK_APP_SECRET = "e0778251b4bab8a1ffc1aa6772f9a80c";
    public static final String BOMB_APPLICATION_ID = "3db38af2f22c4c72bbaf8d2e1092f728";
    public static final String FROM_WHICH_ACTIVITTY_TAG = "TAG";
    public static final String PASSWORD_TAG = "password";
    public static final String MOBILE_TAG = "mobile";
    public static final String BUY_FOOD_NUM_TAG = "foodnumber";
    public static final String BUY_FOOD_PRICE = "foodprice";
    public static final String BUY_FOOD_AMOUNT = "footamount";
    public static final String BUY_FOOD_BONUS = "footbonus";
        public static final String HOST = "http://192.168.155.3:";//johnson
//    public static final String HOST = "http://10.0.2.2:";//本地
//    public static final String HOST = "http://10.0.3.2:";//Genymotion

    public static final String PORT = "5000/";
    public static final String URL = HOST + PORT;
    public final static String TAG_IS_CHANGEPASSWORD = "tag_is_changepassword";
    public final static String NO_NET_MESSAGE = "当前网络不佳，请确认是否已连接网络";
    public final static String SEND_SMS_SUC = "验证码已发送，请注意查收";
    public final static int FOOD_SALES_RANK_NUM = 10;
    public final static String GET_NEWEST_ORDER_TAG = "空白格";

    public static final int NO_NET = 0;//断网标识
    public static final int NO_GOOD_SHOPCART = 1;//购物车无数据
    public static final int NO_ORDER = 2;//无订单
    public static final int NORMAL = -1;//正常
    public static final int NO_FOOD = 3;//无商品

    /**
     * Request Code:打开图库
     */
    public static final int REQUEST_CODE_OPEN_GALLERY = 2;
    /**
     * Request Code:打开相机
     */
    public static final int REQUEST_CODE_OPEN_CAMERA = 3;
    /**
     * Request Code:照片裁剪
     */
    public static final int REQUEST_CODE_CROP_PIC = 1;
    /**
     * 评论
     */
    public static final int REQUEST_CODE_COMMENT = -1;


}
