package com.gesschoolapp.models.users.concrete;

import com.gesschoolapp.models.users.Utilisateur;

public class Admin extends Utilisateur {

    public Admin(){

    }

    public Admin(int id, String nom, String prenom, String email, String password, String numero) {
        super(id, nom, prenom, email, password, numero);
    }

    public Admin(String nom, String prenom, String email, String password, String numero) {
        super(nom, prenom, email, password, numero);
    }
}
