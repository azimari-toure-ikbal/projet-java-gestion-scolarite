package com.gesschoolapp.models.matieres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module implements Serializable {
    public static final long serialVersionUID = 424;
    //Module has id, intitule
    private int id;
    private String intitule;
    List<Note> notes;

    private String classe;

    private int semestre;

    //constructors
    public Module() {
    }

    public Module(int id, String intitule, List<Note> notes, String classe, int semestre) {
        this.setId(id);
        this.setIntitule(intitule);
        this.setNotes(notes);
        this.setClasse(classe);
        this.setSemestre(semestre);
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

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public List<Note> getGoodNotes(){
        List<Note> goodNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getApprenant().getEtatPaiement() > 0) {
                goodNotes.add(note);
            }
        }
        return goodNotes;
    }

    @Override
    public String toString() {
        return "\nModule{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                " notes " + notes +
                ", classe='" + classe + '\'' +
                ", semestre=" + semestre +
                "}\n";
    }
}
