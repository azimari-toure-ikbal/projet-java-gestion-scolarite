package com.gesschoolapp.models.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Actions implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Action> listActions = new ArrayList<>();

    public Actions() {
        this.listActions = new ArrayList<>();
    }

    public Actions(List<Action> listActions) {
        this.listActions = listActions;
    }

    public List<Action> getListActions() {
        return listActions;
    }

    public void setListActions(List<Action> listActions) {
        this.listActions = listActions;
    }

    public void add(Action action) {
        action.setIdAction(listActions.size());
        listActions.add(action);
    }

    public void remove(Action action) {
        listActions.remove(action);
    }

    public void remove(int index) {
        listActions.remove(index);
    }

}
