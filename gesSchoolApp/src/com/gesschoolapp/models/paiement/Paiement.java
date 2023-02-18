package com.gesschoolapp.models.paiement;

import java.time.LocalDate;

public class Paiement {
    //paiement has idPaiement, numeroRecu, montant, rubrique, date, apprenant(String), caissier(String), classe(String), observation
    private int idPaiement;
    private String numeroRecu;
    private String montant;
    private String rubrique;
    private LocalDate date;
    private String observation;
    
    private String apprenant;
    private String caissier;
    private String classe;

    public Paiement(int idPaiement, String numeroRecu, String montant, String rubrique, LocalDate date, String observation, String apprenant, String caissier, String classe) {
        //use the setters
        this.setIdPaiement(idPaiement);
        this.setNumeroRecu(numeroRecu);
        this.setMontant(montant);
        this.setRubrique(rubrique);
        this.setDate(date);
        this.setObservation(observation);
        this.setApprenant(apprenant);
        this.setCaissier(caissier);
        this.setClasse(classe);
    }

    public int getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public String getNumeroRecu() {
        return numeroRecu;
    }

    public void setNumeroRecu(String numeroRecu) {
        this.numeroRecu = numeroRecu;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getRubrique() {
        return rubrique;
    }

    public void setRubrique(String rubrique) {
        this.rubrique = rubrique;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getApprenant() {
        return apprenant;
    }

    public void setApprenant(String apprenant) {
        this.apprenant = apprenant;
    }

    public String getCaissier() {
        return caissier;
    }

    public void setCaissier(String caissier) {
        this.caissier = caissier;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "\nPaiement{" +
                "idPaiement=" + idPaiement +
                ", numeroRecu='" + numeroRecu + '\'' +
                ", montant='" + montant + '\'' +
                ", rubrique='" + rubrique + '\'' +
                ", date=" + date +
                ", observation='" + observation + '\'' +
                ", apprenant='" + apprenant + '\'' +
                ", caissier='" + caissier + '\'' +
                ", classe='" + classe + '\'' +
                "}\n";
    }
}
