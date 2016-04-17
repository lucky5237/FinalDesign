package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/4/17.
 */
public class ChangeIndexEvent {

    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ChangeIndexEvent(Integer index) {
        this.index = index;
    }
}
