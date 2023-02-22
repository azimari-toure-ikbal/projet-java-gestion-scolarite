package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.db.DAOInterfaces.UserDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Notification;
import com.gesschoolapp.models.users.Admin;
import com.gesschoolapp.models.users.Caissier;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO, DAO<Utilisateur> {
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
    public List<Notification> getNotifs(String name) throws DAOException {

        try(Connection connection = DBManager.getConnection()) {
           //get a list of notifications(String message, LocalDateTime date) from notifications table
            //where utilisateur = name, and return it
            String query = "SELECT * FROM notifications WHERE utilisateur=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            List<Notification> notifs = new ArrayList<>();
            while (rs.next()) {
                String message = rs.getString("message");
                String stringDate = rs.getString("date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //convert String to LocalDate
                LocalDateTime date = LocalDateTime.parse(stringDate, formatter);

                notifs.add(new Notification(message, date));
            }
            return notifs;
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
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
    public Utilisateur create(Utilisateur obj, String user) throws DAOException {
        //Utilisateur has idUtilisateur, nom, prenom, email, password, numero, type
        //INSERT INTO utilisateurs (nom, prenom, email, password, numero, type) VALUES (?,?,?,?,?,?)
        //if obj instance of Admin -> type = "administrateur"
        //if obj instance of Secretaire -> type = "secretaire"
        //if obj instance of Caissier -> type = "caissier"
        try {
            Connection connection = DBManager.getConnection();
            String query = "INSERT INTO utilisateurs (nom, prenom, email, password, numero, type) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getNumero());
            ps.setString(6, obj.getType());
            ps.executeUpdate();
            return this.getList().get(this.getList().size() - 1);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void update(Utilisateur obj, String user) throws DAOException {
        //UPDATE utilisateurs SET nom=?, prenom=?, email=?, password=?, numero=?, type=? WHERE idUtilisateur=?
        try {
            Connection connection = DBManager.getConnection();
            String query = "UPDATE utilisateurs SET nom=?, prenom=?, email=?, password=?, numero=?, type=? WHERE idUtilisateur=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getNumero());
            ps.setString(6, obj.getType());
            ps.setInt(7, obj.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }

    }

    @Override
    public void delete(int id, String user) throws DAOException {
        try {
            Connection connection = DBManager.getConnection();
            String query = "DELETE FROM utilisateurs WHERE idUtilisateur=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Utilisateur read(int id) throws DAOException {
        try {
            Connection connection = DBManager.getConnection();
            String query = "SELECT * FROM utilisateurs WHERE idUtilisateur=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                //get nom, prenom, email, password, numero, id, type
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String numero = rs.getString("numero");
                int idUtilisateur = rs.getInt("idUtilisateur");
                String type = rs.getString("type");

                Utilisateur user = null;
                switch (type) {
                    case "administrateur" -> user = new Admin(idUtilisateur, nom, prenom, email, password, numero);
                    case "secretaire" -> user = new Secretaire(idUtilisateur, nom, prenom, email, password, numero);
                    case "caissier" -> user = new Caissier(idUtilisateur, nom, prenom, email, password, numero);
                }
                return user;
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> getList() throws DAOException {

        List<Utilisateur> users = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM utilisateurs";
            PreparedStatement statement = connection.prepareStatement(query);
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
                    default -> {
                        users.add(null);
                    }
                }
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
}
