package com.gesschoolapp.models.classroom;

import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.student.Apprenant;

import java.io.Serializable;
import java.util.List;

public class Classe implements Serializable {
    public static final long serialVersionUID = 42L;

    //attributes id, intitule, reference(int), annee(string), formation(string), apprenants(list<Apprenants>), modules(list<Module>)
    private int id;
    private String intitule;
    private int reference;
    private String annee;
    private String formation;
    private List<Apprenant> apprenants;
    private List<Module> modules;

    //constructors
    public Classe() {
    }

    public Classe(int id, String intitule, int reference, String annee, String formation, List<Apprenant> apprenants, List<Module> modules) {
        //use setters
        this.setId(id);
        this.setIntitule(intitule);
        this.setReference(reference);
        this.setAnnee(annee);
        this.setFormation(formation);
        this.setApprenants(apprenants);
        this.setModules(modules);

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

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public List<Apprenant> getApprenants() {
        return apprenants;
    }

    public void setApprenants(List<Apprenant> apprenants) {
        this.apprenants = apprenants;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "\nClasse{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", reference=" + reference +
                ", annee='" + annee + '\'' +
                ", formation='" + formation + '\'' +
                ", apprenants=" + apprenants +
                ", modules=" + modules +
                "}\n";
    }
}
