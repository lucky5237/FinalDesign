package zjut.jianlu.breakfast.utils;

import java.util.UUID;

/**
 * Created by jianlu on 16/3/12.
 */
public class BreakfastUtils {

    public static String genOrderNum(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
