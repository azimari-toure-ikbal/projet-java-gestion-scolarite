package com.gesschoolapp.models.matieres;

import com.gesschoolapp.models.student.Apprenant;

import java.io.Serializable;

public class Note implements Serializable {
    public static final long serialVersionUID = 426;
    //Note has id, note(int), apprenant(Apprenant), module(Module)
    private int id;
    private float note;
    private Apprenant apprenant;
    private String module;

    //constructors
    public Note() {
    }

    public Note(int id, int note, Apprenant apprenant, String module) {
        this.setId(id);
        this.setNote(note);
        this.setApprenant(apprenant);
        this.setModule(module);
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Apprenant getApprenant() {
        return apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "\nNote{" +
                "id=" + id +
                ", note=" + note +
                ", apprenant=" + apprenant +
                ", module='" + module + '\'' +
                "}\n";
    }
}
