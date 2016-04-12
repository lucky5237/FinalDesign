package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

/**
 * Created by jianlu on 16/4/12.
 */
public class Place implements Serializable {

    private Integer id;
    private String name;
    private Integer orderNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderNum=" + orderNum +
                '}';
    }
}
