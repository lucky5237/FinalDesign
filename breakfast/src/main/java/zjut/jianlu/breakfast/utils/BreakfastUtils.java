package zjut.jianlu.breakfast.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import zjut.jianlu.breakfast.constant.BreakfastConstant;

/**
 * Created by jianlu on 16/3/12.
 */
public class BreakfastUtils {

    private static final String IMAGE_FOLDER_NAME = "static/image/";

    public synchronized static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public static String getAbsImageUrlPath(String imageName) {

        return BreakfastConstant.URL + IMAGE_FOLDER_NAME + imageName + ".jpg";
    }


    public static int getResourseIdByName(Context context,String resourseType,String resourceName){

        return context.getResources().getIdentifier(resourceName,resourseType,context.getPackageName());
    }

    public static int getDrawableIdByName(Context context,String resourceName){

        return getResourseIdByName(context,"mipmap",resourceName);
    }

}
