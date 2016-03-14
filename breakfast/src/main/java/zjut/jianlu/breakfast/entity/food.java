package zjut.jianlu.breakfast.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by jianlu on 16/3/13.
 */
public class Food extends BmobObject implements Serializable{

    private String name;

    private BmobFile image;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    private Float price;

    private String place;

    private Integer sales;

    public String getName() {
        return name;
    }
    public Food (){
        setTableName("food");
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", price=" + price +
                ", place='" + place + '\'' +
                ", sales=" + sales +
                '}';
    }
}
