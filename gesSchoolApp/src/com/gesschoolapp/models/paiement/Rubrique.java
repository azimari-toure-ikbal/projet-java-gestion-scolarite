package com.gesschoolapp.models.paiement;

public class Rubrique {

    private int idRubrique;
    private String intitule;
    private double montant;

    public Rubrique() {
    }

    public Rubrique(int idRubrique, String intitule, double montant) {
        this.setIdRubrique(idRubrique);
        this.setIntitule(intitule);
        this.setMontant(montant);
    }

    public Rubrique(String intitule, double montant) {
        this.intitule = intitule;
        this.montant = montant;
    }

    public int getIdRubrique() {
        return idRubrique;
    }

    public void setIdRubrique(int idRubrique) {
        this.idRubrique = idRubrique;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return intitule;
    }
}
