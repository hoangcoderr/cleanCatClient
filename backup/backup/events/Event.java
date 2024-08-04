package cleanCatClient.events;

public class Event<T> {
    private boolean cancelled;
    private EventType type;
    private EventDirection direction;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventDirection getDirection() {
        return direction;
    }

    public void setDirection(EventDirection direction) {
        this.direction = direction;
    }

    public boolean isPre() {
        if (this.type == null) {
            return false;
        }
        return this.type == EventType.PRE;
    }

    public boolean isPost() {
        if (this.type == null) {
            return false;
        }
        return this.type == EventType.POST;
    }

    public boolean isIncoming() {
        if (this.direction == null) {
            return false;
        }
        return this.direction == EventDirection.INCOMING;
    }

    public boolean isOutgoing() {
        if (this.direction == null) {
            return false;
        }
        return this.direction == EventDirection.OUTGOING;
    }
}
