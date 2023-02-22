package com.gesschoolapp.models.users;

public class Caissier extends Utilisateur {

    public Caissier(){

    }

    public Caissier(int id, String nom, String prenom, String email, String password, String numero) {
        super(id, nom, prenom, email, password, numero);
    }

    public Caissier(String nom, String prenom, String email, String password, String numero) {
        super(nom, prenom, email, password, numero);
    }


}
