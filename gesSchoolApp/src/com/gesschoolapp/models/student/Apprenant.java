package com.gesschoolapp.models.student;

import java.time.LocalDate;

public class Apprenant {

    private int idApprenant;
    private int matricule;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String sexe;
    private String nationalite;
    private int etatPaiement = 0;

    public Apprenant(int idApprenant, int matricule, String prenom, String nom, LocalDate dateNaissance, String sexe, String nationalite, int etatPaiement) {
        this(matricule, prenom, nom, dateNaissance, sexe, nationalite, etatPaiement);
        this.setIdApprenant(idApprenant);
    }

    public Apprenant(int matricule, String prenom, String nom, LocalDate dateNaissance, String sexe, String nationalite, int etatPaiement) {
        this.setMatricule(matricule);
        this.setPrenom(prenom);
        this.setNom(nom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
        this.setNationalite(nationalite);
        this.setEtatPaiement(etatPaiement);
    }


    public int getIdApprenant() {
        return idApprenant;
    }

    public void setIdApprenant(int idApprenant) throws IllegalArgumentException {
        if (idApprenant < 0) {
            throw new IllegalArgumentException("L'id ne peut pas être négatif");
        }
        this.idApprenant = idApprenant;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) throws IllegalArgumentException {
        if (matricule <= 0) {
            throw new IllegalArgumentException("Le matricule ne peut pas être négatif ni nul");
        }
        this.matricule = matricule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public int getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(int etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    @Override
    public String toString() {
        return "Apprenant{" + "idApprenant=" + idApprenant + ", matricule=" + matricule + ", prenom=" + prenom + ", nom=" + nom + ", dateNaissance=" + dateNaissance + ", sexe=" + sexe + ", nationalite=" + nationalite + ", etatPaiement=" + etatPaiement + '}';
    }
}
