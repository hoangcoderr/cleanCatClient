package cleanCatClient.event;

import java.lang.reflect.*;

public class EventData {
    public final Object source;
    public final Method target;
    public final byte priority;
    public EventData(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}
