package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/4/12.
 */
public class Place implements Serializable {

    private Long id;
    private String name;
    private Integer orderNum;


    public Place(Long id,String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
