package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.LoginDAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.users.Admin;
import com.gesschoolapp.models.users.Caissier;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements SearchDAO<Utilisateur>, LoginDAO {
    @Override
    public Utilisateur authenticate(String email, String password) throws DAOException {
        try(Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE email=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                //get nom, prenom, email, password, numero, id, type
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String theEmail = rs.getString("email");
                String thePassword = rs.getString("password");
                String numero = rs.getString("numero");
                int id = rs.getInt("idUtilisateur");
                String type = rs.getString("type");

                Utilisateur user = null;
                switch (type) {
                    case "administrateur" -> user = new Admin(id, nom, prenom, theEmail, thePassword, numero);
                    case "secretaire" -> user = new Secretaire(id, nom, prenom, theEmail, thePassword, numero);
                    case "caissier" -> user = new Caissier(id, nom, prenom, theEmail, thePassword, numero);
                }
                return user;
            }

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> search(String stringToSearch) throws DAOException {
        List<Utilisateur> users = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {

            String query = "SELECT * FROM utilisateurs WHERE email LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, "%" + stringToSearch + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idUtilisateur = resultSet.getInt("idUtilisateur");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String numero = resultSet.getString("numero");
                String type = resultSet.getString("type");
                String password = resultSet.getString("password");

                System.out.println("idUtilisateur = " + idUtilisateur);
                System.out.println("nom = " + nom);
                System.out.println("prenom = " + prenom);
                System.out.println("numero = " + numero);
                switch (type) {
                    case "administrateur" -> users.add(new Admin(idUtilisateur, nom, prenom, numero, type, password));
                    case "secretaire" -> users.add(new Secretaire(idUtilisateur, nom, prenom, numero, type, password));
                    case "caissier" -> users.add(new Caissier(idUtilisateur, nom, prenom, numero, type, password));
                    default -> {}
                }
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return users;
    }

    @Override
    public void create(Utilisateur obj) throws DAOException {

    }

    @Override
    public void update(Utilisateur obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Utilisateur read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Utilisateur> getList() throws DAOException {
        return null;
    }
}
