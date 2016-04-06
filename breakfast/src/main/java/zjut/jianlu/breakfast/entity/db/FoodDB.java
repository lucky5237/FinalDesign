package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

/**
 * Created by jianlu on 16/3/13.
 */
public class FoodDB extends SugarRecord {

    private String name;//食品名称

    private String image;//食品图片的路径

    private Float price;//食品价格

    private String place;//购买地点

    private Integer sales;//销量

    public FoodDB() {

    }

    public String getName() {
        return name;
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

}
