package com.gesschoolapp.models.actions;

import com.gesschoolapp.view.util.ActionType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Action implements Serializable {
    public static final long serialVersionUID = 427;

    private int idAction;
    private Object object;
    private String actor;
    private ActionType action;
    private LocalDateTime date;


    public Action() {
    }

    /**
     *
     *      Action constructor:
     *      Action is a class that contains the information about the action that has been done by the user
     *      object is the object involved in the action
     *      actor is the user who has done the action
     *      action is the type of action that has been done, you can find the list of actions in the ActionType class
     *
     * @param idAction
     * @param object
     * @param actor
     * @param action
     * @param date
     *\n
     *
     *
     */

    public Action(int idAction, Object object, String actor, ActionType action, LocalDateTime date) {
        this.setIdAction(idAction);
        this.setObject(object);
        this.setActor(actor);
        this.setAction(action);
        this.setDate(date);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }
}
