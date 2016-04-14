package zjut.jianlu.breakfast.entity.db;

import com.orm.SugarRecord;

import zjut.jianlu.breakfast.entity.bean.Food;

/**
 * Created by jianlu on 16/3/13.
 */
public class FoodDB extends SugarRecord {

    private Long foodID;

    private String name;//食品名称

    private String image;//食品图片的路径

    private Float price;//食品价格

    private Long placeId;//购买地点

    private String placeName;

    private Integer sales;//销量

    private String createTs;

    public FoodDB() {

    }

    public FoodDB(Food food) {
        foodID = food.getId();
        name = food.getName();
        image = food.getImage();
        price = food.getPrice();
        placeId = food.getPlace().getId();
        placeName = food.getPlace().getName();
        sales = food.getSales();
        createTs = food.getCreateTs();
    }

    public String getName() {
        return name;
    }

    public Long getFoodID() {
        return foodID;
    }

    public void setFoodID(Long foodID) {
        this.foodID = foodID;
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

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
