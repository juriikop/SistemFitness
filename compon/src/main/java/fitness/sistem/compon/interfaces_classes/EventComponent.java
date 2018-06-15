package fitness.sistem.compon.interfaces_classes;

import fitness.sistem.compon.base.BaseComponent;

public class EventComponent {
    public int eventSenderId;
    public BaseComponent eventReceiverComponent;
    public EventComponent(int sender, BaseComponent receiver) {
        eventSenderId = sender;
        eventReceiverComponent = receiver;
    }
}
