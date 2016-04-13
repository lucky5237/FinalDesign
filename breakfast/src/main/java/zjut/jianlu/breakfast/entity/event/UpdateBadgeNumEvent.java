package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/13.
 */
public class UpdateBadgeNumEvent {

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public UpdateBadgeNumEvent(Integer num) {
        this.num = num;
    }
}
