package com.gesschoolapp.models.actions;

import com.gesschoolapp.db.DAOClassesImpl.ActionDAOImp;
import com.gesschoolapp.utils.ActionType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Action implements Serializable {
    public static final long serialVersionUID = 427;

    private int idAction;
    private Object object;
    private String actor;
    private ActionType action;
    private LocalDateTime date;
    private boolean canceled ;

    public Action() {
        this.canceled = false;
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
        this.canceled = false;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
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
    public String getObjectType(){
        return this.object.getClass().getSimpleName();
    }

    public void cancelAction(String admin){
        new ActionDAOImp().cancelAction(this, admin);
        this.canceled = true;
        try {
            new ActionDAOImp().setActionCanceled(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while deserializing actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "\nAction{" +
                "idAction=" + idAction +
                ", object=" + object +
                ", actor='" + actor + '\'' +
                ", action=" + action +
                ", date=" + date +
                '}' + '\n';
    }
}
