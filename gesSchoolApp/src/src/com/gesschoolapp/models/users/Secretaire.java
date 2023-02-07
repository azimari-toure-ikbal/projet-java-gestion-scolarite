package com.gesschoolapp.models.users;

public class Secretaire extends Utilisateur {

    public Secretaire(int id, String nom, String prenom, String email, String password, String numero) {
        super(id, nom, prenom, email, password, numero);
    }

    public Secretaire(String nom, String prenom, String email, String password, String numero) {
        super(nom, prenom, email, password, numero);
    }

}
