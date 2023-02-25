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
import com.gesschoolapp.utils.Toolbox;
import com.sendgrid.*;

import java.io.IOException;
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
            String query = "SELECT * FROM utilisateurs WHERE email=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
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

                if(!Toolbox.verifyPassword(password, thePassword)) return null;

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

    public void setNotifsSeen(String name) throws DAOException {
        try(Connection connection = DBManager.getConnection()){
            String query = "UPDATE notifications SET seen=1 WHERE utilisateur=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.executeUpdate();
        }catch (Exception e){
            throw new DAOException( "Error in setNotifsSeen() : " +  e.getMessage());
        }
    }

    @Override
    public List<Notification> getNotifs(String name) throws DAOException {

        try(Connection connection = DBManager.getConnection()) {
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

            // Set user password
            obj.setPassword(Toolbox.generateRandomPassword());

            String hashedPassword = Toolbox.generateSecurePassword(obj.getPassword());
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getEmail());
            ps.setString(4, hashedPassword);
            ps.setString(5, obj.getNumero());
            ps.setString(6, obj.getType());
            ps.executeUpdate();

            // Send mail with password here
            String mailContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t<title>Bienvenue chez notre service</title>\n" +
                    "\t<meta charset=\"utf-8\">\n" +
                    "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "\t<style>\n" +
                    "\t\tbody {\n" +
                    "\t\t\tfont-family: Arial, sans-serif;\n" +
                    "\t\t\tcolor: #333333;\n" +
                    "\t\t\tfont-size: 16px;\n" +
                    "\t\t\tline-height: 1.5;\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t}\n" +
                    "\t\th1 {\n" +
                    "\t\t\tfont-size: 24px;\n" +
                    "\t\t\tmargin-top: 0;\n" +
                    "\t\t\tmargin-bottom: 30px;\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t}\n" +
                    "\t\tp {\n" +
                    "\t\t\tmargin-top: 0;\n" +
                    "\t\t\tmargin-bottom: 30px;\n" +
                    "\t\t\ttext-align: left;\n" +
                    "\t\t}\n" +
                    "\t\t.container {\n" +
                    "\t\t\tmax-width: 600px;\n" +
                    "\t\t\tmargin: 0 auto;\n" +
                    "\t\t\tpadding: 20px;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\t<div class=\"container\">\n" +
                    "\t\t<h1>Bienvenue chez notre service</h1>\n" +
                    "\t\t<p>Bonjour " + obj.getFullName() + ",</p>\n" +
                    "\t\t<p>Votre compte a été créé avec succès. Vous pouvez désormais vous connecter en utilisant les informations de connexion suivantes :</p>\n" +
                    "\t\t<ul>\n" +
                    "\t\t\t<li>Adresse e-mail :" + obj.getEmail() + "</li>\n" +
                    "\t\t\t<li>Mot de passe : " + obj.getPassword() + "</li>\n" +
                    "\t\t</ul>\n" +
                    "\t\t<p>Nous vous recommandons de changer votre mot de passe après votre première connexion.</p>\n" +
                    "\t\t<p>Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.</p>\n" +
                    "\t\t<p>Cordialement,</p>\n" +
                    "\t\t<p>L'équipe de notre service</p>\n" +
                    "\t</div>\n" +
                    "</body>\n" +
                    "</html>\n";

            Email from = new Email("noreply@ar-struct.com");
            String subject = "Merci d'utiliser Schoolup !";
            Email to = new Email(obj.getEmail());
            Content content = new Content("text/html", mailContent);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(Toolbox.getEnv());
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

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
                String email = resultSet.getString("email");

                switch (type) {
                    case "administrateur" -> users.add(new Admin(idUtilisateur, nom, prenom, email, password, numero));
                    case "secretaire" -> users.add(new Secretaire(idUtilisateur, nom, prenom, email, password, numero));
                    case "caissier" -> users.add(new Caissier(idUtilisateur, nom, prenom, email, password, numero));
                    default -> {
                        users.add(null);
                    }
                }
            }
            return users;
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }
}
