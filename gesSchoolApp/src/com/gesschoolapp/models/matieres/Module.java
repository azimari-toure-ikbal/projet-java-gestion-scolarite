package com.gesschoolapp.models.matieres;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
    public static final long serialVersionUID = 424;
    //Module has id, intitule
    private int id;
    private String intitule;
    List<Note> notes;

    private String classe;

    //constructors
    public Module() {
    }

    public Module(int id, String intitule, List<Note> notes) {
        this.setId(id);
        this.setIntitule(intitule);
        this.setNotes(notes);
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

    public String getClasse() {
        return classe;
    }
    public List<Note> getNotes() {
        return notes;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return "\nModule{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                " notes " + notes +
                "}\n";
    }
}
