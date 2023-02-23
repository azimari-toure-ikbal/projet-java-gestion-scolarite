package com.gesschoolapp.models.classroom;

import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.paiement.Echeance;
import com.gesschoolapp.models.paiement.Echeancier;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Classe implements Serializable, Comparable<Classe>{
    public static final long serialVersionUID = 42L;

    //attributes id, intitule, reference(int), annee(string), formation(string), apprenants(list<Apprenants>), modules(list<Module>)
    private int id;
    private String intitule;
    private int reference;
    private String annee;
    private String formation;
    private List<Apprenant> apprenants;
    private List<Module> modules;
    private LocalDateTime views;
    private List<Rubrique> rubriques;
    private List<Echeance> echeancier;

    //constructors
    public Classe() {
    }

    public Classe(int id, String intitule, int reference, String annee, String formation, List<Apprenant> apprenants, List<Module> modules, LocalDateTime views) {
        //use setters
        this.setId(id);
        this.setIntitule(intitule);
        this.setReference(reference);
        this.setAnnee(annee);
        this.setFormation(formation);
        this.setApprenants(apprenants);
        this.setModules(modules);
        this.setViews(views);
    }

    public Classe(int id, String intitule, int reference, String annee, String formation, List<Apprenant> apprenants, List<Module> modules, LocalDateTime views, List<Rubrique> rubriques) {
        //use setters
        this.setId(id);
        this.setIntitule(intitule);
        this.setReference(reference);
        this.setAnnee(annee);
        this.setFormation(formation);
        this.setApprenants(apprenants);
        this.setModules(modules);
        this.setViews(views);
        this.setRubriques(rubriques);
    }

    public Classe(int id, String intitule, int reference, String annee, String formation, List<Apprenant> apprenants, List<Module> modules, LocalDateTime views, List<Rubrique> rubriques, List<Echeance> echeancier) {
        //use setters
        this.setId(id);
        this.setIntitule(intitule);
        this.setReference(reference);
        this.setAnnee(annee);
        this.setFormation(formation);
        this.setApprenants(apprenants);
        this.setModules(modules);
        this.setViews(views);
        this.setRubriques(rubriques);
        this.setEcheancier(echeancier);
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

    public LocalDateTime getViews() {
        return views;
    }

    public void setViews(LocalDateTime views) {
        this.views = views;
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


    public List<Rubrique> getRubriques() {
        return rubriques;
    }

    public void setRubriques(List<Rubrique> rubriques) {
        this.rubriques = rubriques;
    }

    public List<Echeance> getEcheancier() {
        return echeancier;
    }

    public void setEcheancier(List<Echeance> echeancier) {
        this.echeancier = echeancier;
    }

    public Echeance getCurrentEcheance(){
        for (Echeance echeance : echeancier){
            if (echeance.getDate().getMonthValue() == LocalDate.now().getMonthValue()){
                return echeance;
            }
        }
        return null;
    }

    /**
     *
     * This method return a boolean, true is the current echeance is paid, false otherwise
     * You just pass the apprenant as parameter
     *
     *
     * @param apprenant
     * @return
     */
    public boolean isCurrentEcheancePaid(Apprenant apprenant){
        if(apprenant.getEtatPaiement() == 0){
            return false;
        }
        LocalDate date = echeancier.get(apprenant.getEtatPaiement()).getDate();

        if(date.isAfter(getCurrentEcheance().getDate()) || date.isEqual(getCurrentEcheance().getDate())){
            return true;
        }
        return false;
    }

    public List<Apprenant> getGoodApprenants(){
        List<Apprenant> goodApprenants = new ArrayList<>();
        for (Apprenant apprenant : apprenants){
            if (apprenant.getEtatPaiement() > 0){
                goodApprenants.add(apprenant);
            }
        }
        return goodApprenants;
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
                ", views=" + views +
                ", rubriques=" + rubriques +
                ", echeancier=" + echeancier +
                "}\n";
    }


        @Override
        public int compareTo(Classe o) {
            //compare by views
            return o.getViews().compareTo(this.getViews());
        }

}
