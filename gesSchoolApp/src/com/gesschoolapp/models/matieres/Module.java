package com.gesschoolapp.models.matieres;

public class Module {
    //Module has id, intitule
    private int id;
    private String intitule;

    //constructors
    public Module() {
    }

    public Module(int id, String intitule) {
        this.setId(id);
        this.setIntitule(intitule);
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                '}';
    }
}
