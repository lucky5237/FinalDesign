package zjut.jianlu.breakfast.entity.bean;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by jianlu on 16/3/13.
 */
    public class Food extends SugarRecord implements Serializable  {


    private String name;//食品名称

    private String image;//食品图片的路径

    private Float price;//食品价格

    private Place place;//购买地点

    private Integer sales;//销量

    private String createTs;

    public String getCreateTs() {
        return createTs;
    }
    public Food(){

    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }


}
