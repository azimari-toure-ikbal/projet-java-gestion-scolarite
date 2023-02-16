package com.gesschoolapp.models.paiement;

import java.time.LocalDateTime;

public class Echeance {

    private int idEcheance;
    private String intitule;
    private LocalDateTime date;

    public Echeance() {
    }

    public Echeance(int idEcheance, String intitule, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
