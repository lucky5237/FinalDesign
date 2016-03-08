package zjut.jianlu.breakfast.entity;

import com.orm.SugarRecord;

/**
 * Created by jianlu on 3/8/2016.
 */

public class Book extends SugarRecord{
    String title;
    String edition;
    public Book(){

    }
    public Book(String title,String edition){
        this.title=title;
        this.edition=edition;
    }

}