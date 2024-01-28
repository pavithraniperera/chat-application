package lk.ijse.entity;

import java.sql.Time;

public class MessageEntity {

    private String senderName;
    private String reciverName;
    private String message;
    private Time time;

    public MessageEntity(String clientName, String name, String serverMsg, Time time) {
        senderName = name;
        reciverName = clientName;
        message =serverMsg;
        this.time = time;

    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
