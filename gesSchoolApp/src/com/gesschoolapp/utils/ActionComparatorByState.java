package com.gesschoolapp.utils;

public class ActionComparatorByState implements java.util.Comparator<com.gesschoolapp.models.actions.Action> {
    @Override
    public int compare(com.gesschoolapp.models.actions.Action o1, com.gesschoolapp.models.actions.Action o2) {
        if(o1.isCanceled() && !o2.isCanceled()) return 1;
        else if(!o1.isCanceled() && o2.isCanceled()) return -1;
        else return o2.getDate().compareTo(o1.getDate());
    }
}
