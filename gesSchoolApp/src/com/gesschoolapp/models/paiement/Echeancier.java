package com.gesschoolapp.models.paiement;

import java.util.List;

public class Echeancier {
    private int idEcheancier;
    private String classe;
    private List<Echeance> echeances;

    public Echeancier() {
    }

    public Echeancier(int idEcheancier, String classe, List<Echeance> echeances) {
        this.setIdEcheancier(idEcheancier);
        this.setClasse(classe);
        this.setEcheances(echeances);
    }

    public int getIdEcheancier() {
        return idEcheancier;
    }

    public void setIdEcheancier(int idEcheancier) {
        this.idEcheancier = idEcheancier;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public List<Echeance> getEcheances() {
        return echeances;
    }

    public void setEcheances(List<Echeance> echeances) {
        this.echeances = echeances;
    }

    public void getCurrentEcheance() {

    }

}
