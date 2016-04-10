package zjut.jianlu.breakfast.entity.requestBody;

/**
 * Created by jianlu on 16/4/9.
 */
public class FoodRankBody {

    private int number;

    private boolean isAsc;

    public FoodRankBody(int number, boolean isAsc) {
        this.number = number;
        this.isAsc = isAsc;
    }
}
