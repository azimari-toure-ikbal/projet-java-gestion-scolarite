package com.gesschoolapp.models.paiement;

import com.gesschoolapp.models.student.Apprenant;

import java.time.LocalDate;

public class Paiement {
    //paiement has idPaiement, numeroRecu, montant, rubrique, date, apprenant(String), caissier(String), classe(String), observation
    private int idPaiement;
    private String numeroRecu;
    private double montant;
    private String rubrique;
    private LocalDate date;
    private String observation;
    
    private Apprenant apprenant;
    private String caissier;
    private String classe;

    private String nameApprenant;

    public Paiement() {
    }

    public Paiement(int idPaiement, String numeroRecu, double montant, String rubrique, LocalDate date, String observation, String caissier, String classe,  String nameApprenant, Apprenant apprenant) {
        //use the setters
        this.setIdPaiement(idPaiement);
        this.setNumeroRecu(numeroRecu);
        this.setMontant(montant);
        this.setRubrique(rubrique);
        this.setDate(date);
        this.setObservation(observation);
        this.setNameApprenant(nameApprenant);
        this.setCaissier(caissier);
        this.setClasse(classe);
    }

    public Paiement(int idPaiement, String numeroRecu, double montant, String rubrique, LocalDate date, String observation, Apprenant apprenant, String caissier, String classe) {
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

    public String getNameApprenant() {
        return nameApprenant;
    }

    public void setNameApprenant(String nameApprenant) {
        this.nameApprenant = nameApprenant;
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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
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

    public Apprenant getApprenant() {
        return apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
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
                ", nameApprenant='" + nameApprenant + '\'' +
                "}\n";
    }
}
