package com.gesschoolapp.models.classroom;

import java.io.Serializable;
import java.util.List;

public class Classes implements Serializable {
    public static final long serialVersionUID = 423;


    private List<Classe> classes;
    private String year;

    public Classes() {
    }

    public Classes(List<Classe> classes, String year) {
        this.classes = classes;
        this.year = year;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
