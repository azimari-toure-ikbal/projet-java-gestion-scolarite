package com.gesschoolapp.models.actions;

import java.time.LocalDateTime;

public class Notification {
    //Notification has a message and a date (LocalDateTime)
    private String message;
    private LocalDateTime date;

    public Notification() {
    }

    public Notification(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
