package com.gesschoolapp.models.users;

public abstract class Utilisateur {

    protected int id; // sera généré automatiquement par la base de donnée
    protected String prenom;
    protected String nom;
    protected String login;
    protected String password;
    protected String email;
    protected String  numero;
    protected String fullName;


    public Utilisateur(int id, String nom, String prenom, String email, String password, String numero) {
        this(nom, prenom, email, password, numero);
        this.setId(id);
    }

    public Utilisateur(String nom, String prenom, String email, String password, String numero) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setPassword(password);
        this.setNumero(numero);
        this.setFullName(nom + " " + prenom);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("L'id ne peut pas être négatif");
        }
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
