package lk.ijse.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class MessageDto {

    private String senderName;
    private String reciverName;
    private String message;
    private Time time;

    public MessageDto(String clientName, String name, String serverMsg,Time time) {
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
