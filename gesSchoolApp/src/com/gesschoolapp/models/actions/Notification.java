package com.gesschoolapp.models.actions;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;

import java.time.LocalDateTime;

public class Notification {
    //Notification has a message and a date (LocalDateTime)

    private int id;
    private String message;
    private LocalDateTime date;
    private boolean seen;

    public Notification() {
        this.seen = false;
    }

    public Notification(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
        this.seen = false;
    }

    public Notification(int id, String message, LocalDateTime date, boolean seen) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.seen = seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isSeen() {
        return seen;
    }

    public void setSeen() {
        try {
            new UserDAOImp().setNotifSeen(this.id);
        } catch (DAOException e) {
            e.printStackTrace();
            System.out.println("Error while setting notification as seen");
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
