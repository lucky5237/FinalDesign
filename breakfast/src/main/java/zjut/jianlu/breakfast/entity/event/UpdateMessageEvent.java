package zjut.jianlu.breakfast.entity.event;

/**
 * Created by jianlu on 16/5/3.
 */
public class UpdateMessageEvent {

    public boolean isHaveMessage() {
        return haveMessage;
    }

    public void setHaveMessage(boolean haveMessage) {
        this.haveMessage = haveMessage;
    }

    private boolean haveMessage;

    public UpdateMessageEvent(boolean haveMessage) {
        this.haveMessage = haveMessage;
    }
}
