package com.gesschoolapp.utils;

public class ActionComparatorByDate implements java.util.Comparator<com.gesschoolapp.models.actions.Action> {
    @Override
    public int compare(com.gesschoolapp.models.actions.Action o1, com.gesschoolapp.models.actions.Action o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
