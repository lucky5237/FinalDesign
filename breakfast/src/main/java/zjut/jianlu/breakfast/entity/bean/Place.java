package zjut.jianlu.breakfast.entity.bean;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jianlu on 16/4/12.
 */
public class Place extends SugarRecord implements Serializable {

    private String name;
    private Integer orderNum;

    public Place() {
    }

    public Place(String name, Integer orderNum) {
        this.name = name;
        this.orderNum = orderNum;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }


}
