package com.gesschoolapp.models.paiement;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Echeance {

    private int idEcheance;
    private String intitule;
    private LocalDate date;

    public Echeance() {
    }

    public Echeance(int idEcheance, String intitule, LocalDate date) {
        this.setIdEcheance(idEcheance);
        this.setIntitule(intitule);
        this.setDate(date);
    }


    public int getIdEcheance() {
        return idEcheance;
    }

    public void setIdEcheance(int idEcheance) {
        this.idEcheance = idEcheance;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Echeance{" +
                "intitule='" + intitule + '\'' +
                ", date=" + date +
                '}';
    }
}
