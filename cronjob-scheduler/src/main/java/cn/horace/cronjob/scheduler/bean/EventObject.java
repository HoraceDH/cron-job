package cn.horace.cronjob.scheduler.bean;

import cn.horace.cronjob.scheduler.constants.EventType;

/**
 * 事件消息
 * <p>
 *
 * @author Horace
 */
public class EventObject {
    /**
     * 事件类型
     */
    private EventType type;
    /**
     * 事件数据
     */
    private Object data;

    public EventObject(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventObject{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}