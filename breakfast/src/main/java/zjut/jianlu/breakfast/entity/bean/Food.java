package zjut.jianlu.breakfast.entity.bean;

import java.io.Serializable;

import zjut.jianlu.breakfast.entity.db.FoodDB;

/**
 * Created by jianlu on 16/3/13.
 */
public class Food implements Serializable {

    private Long id;

    private String name;//食品名称

    private String image;//食品图片的路径

    private Float price;//食品价格

    private Place place;//购买地点

    private Integer sales;//销量

    private String createTs;

    public String getCreateTs() {
        return createTs;
    }

    public Food(FoodDB foodDB) {
        id = foodDB.getFoodID();
        name = foodDB.getName();
        image = foodDB.getImage();
        price = foodDB.getPrice();
        Place place = new Place(foodDB.getPlaceId(), foodDB.getName());
        this.place = place;
        sales = foodDB.getSales();
        createTs = foodDB.getCreateTs();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
