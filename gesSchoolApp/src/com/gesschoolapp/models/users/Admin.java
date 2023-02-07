package com.gesschoolapp.models.users;

public class Admin extends Utilisateur {

    public Admin(int id, String nom, String prenom, String email, String password, String numero) {
        super(id, nom, prenom, email, password, numero);
    }

    public Admin(String nom, String prenom, String email, String password, String numero) {
        super(nom, prenom, email, password, numero);
    }
}
