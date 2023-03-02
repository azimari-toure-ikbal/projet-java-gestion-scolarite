package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.extensions.ApprenantDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.utils.ActionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApprenantDAOImp implements ApprenantDAO {

    @Override
    public Apprenant create(Apprenant obj, String user) throws DAOException {
        try(Connection connexion = DBManager.getConnection()){
            int matricule = 100;
            String queryMat = "SELECT count(*) FROM apprenants";
            PreparedStatement statementMat = connexion.prepareStatement(queryMat);
            ResultSet rs = statementMat.executeQuery();
            if(rs.next()){
                matricule += rs.getInt(1) + 1;
            }
            //Generate a method to insert a Apprenant in the database

            if(obj.getIdApprenant() != 0){
                String query = "INSERT INTO apprenants (idApprenant, nom, prenom, sexe, nationalite, dtNaiss, echeancier"
                        + ", matricule) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connexion.prepareStatement(query);
                statement.setInt(1, obj.getIdApprenant());
                statement.setString(2, obj.getNom());
                statement.setString(3, obj.getPrenom());
                statement.setString(4, obj.getSexe());
                statement.setString(5, obj.getNationalite());
                statement.setString(6, obj.getDateNaissance().toString());
                statement.setInt(7, obj.getEtatPaiement());
                statement.setInt(8, matricule);
                statement.executeUpdate();
            }else{
                String query = "INSERT INTO apprenants (nom, prenom, sexe, nationalite, dtNaiss, echeancier, matricule) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = connexion.prepareStatement(query);
                statement.setString(1, obj.getNom());
                statement.setString(2, obj.getPrenom());
                statement.setString(3, obj.getSexe());
                statement.setString(4, obj.getNationalite());
                statement.setString(5, obj.getDateNaissance().toString());
                statement.setInt(6, obj.getEtatPaiement());
                statement.setInt(7, matricule);
                statement.executeUpdate();
            }

            Classe classe = new ClasseDAOImp().search(obj.getClasse()).get(0);
            Apprenant apprenant = this.searchForCreate(matricule);

            String query2 = "INSERT INTO classeapprenant (idApprenant, idClasse) VALUES (?, ?)";
            PreparedStatement statement2 = connexion.prepareStatement(query2);
            statement2.setInt(1, apprenant.getIdApprenant());
            statement2.setInt(2, classe.getId());
            statement2.executeUpdate();

            List<Module> modules = classe.getModules();
            for (Module module : modules){
                String query3 = "INSERT INTO notes (idApprenant, idModule, valeur) VALUES (?, ?, 0)";
                PreparedStatement statement3 = connexion.prepareStatement(query3);
                statement3.setInt(1, apprenant.getIdApprenant());
                statement3.setInt(2, module.getId());
                statement3.executeUpdate();
            }
            Apprenant addedApprenant = this.searchByMatricule(matricule);

            if(!Objects.equals(user, "admin")){
                List<Action> actions = new ActionDAOImp().getActions();
                for (Action action : actions){
                    if(action.getObject() instanceof Apprenant student){
                        if(student.getIdApprenant() == addedApprenant.getIdApprenant() ||
                                student.getMatricule() == addedApprenant.getMatricule()){
                            new ActionDAOImp().delete(action.getIdAction());
                        }
                    }
                }
                Action action = new Action();
                action.setAction(ActionType.ADD);
                action.setActor(user);
                action.setObject(addedApprenant);
                action.setDate(LocalDateTime.now());
                new ActionDAOImp().create(action);
            }
            return addedApprenant;
        }catch (Exception e) {
            throw new DAOException("Error while creating Apprenant" + e.getMessage());
        }
    }

    @Override
    public void update(Apprenant obj, String user) throws DAOException {

        try(Connection connection = DBManager.getConnection() ){

            if(!Objects.equals(user, "admin")){
                List<Action> actions = new ActionDAOImp().getActions();
                for (Action action : actions){
                    if(action.getObject() instanceof Apprenant student){
                        if(student.getIdApprenant() == obj.getIdApprenant() ||
                                student.getMatricule() == obj.getMatricule()){
                            new ActionDAOImp().delete(action.getIdAction());
                        }
                    }
                }

                Action action = new Action();
                action.setAction(ActionType.UPDATE);
                action.setActor(user);
                action.setObject(read(obj.getIdApprenant()));
                action.setDate(LocalDateTime.now());
                new ActionDAOImp().create(action);
            }


            String query = "UPDATE apprenants SET nom = ?, prenom = ?, sexe = ?, nationalite = ?, dtNaiss = ?, " +
                    "echeancier = ? WHERE idApprenant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setString(3, obj.getSexe());
            statement.setString(4, obj.getNationalite());
            statement.setString(5, obj.getDateNaissance().toString());
            statement.setInt(6, obj.getEtatPaiement());
            statement.setInt(7, obj.getIdApprenant());
            statement.executeUpdate();
        }catch (Exception e) {
            throw new DAOException("Error while updating Apprenant" + e.getMessage());
        }

    }

    @Override
    public void delete(int id, String user) throws DAOException {
        try(Connection connexion = DBManager.getConnection()){

            if(!Objects.equals(user, "admin")){
                List<Action> actions = new ActionDAOImp().getActions();
                for (Action action
                        : actions){
                    if(action.getObject() instanceof Apprenant student){
                        Apprenant apprenant = new ApprenantDAOImp().read(id);
                        if(student.getIdApprenant() == apprenant.getIdApprenant() ||
                                student.getMatricule() == apprenant.getMatricule()){
                            new ActionDAOImp().delete(action.getIdAction());
                        }
                    }
                }

                Action action = new Action();
                action.setAction(ActionType.DELETE);
                action.setActor(user);
                action.setObject(read(id));
                action.setDate(LocalDateTime.now());
                new ActionDAOImp().create(action);
            }

            String query = "DELETE FROM apprenants WHERE idApprenant = ?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            String query2 = "DELETE FROM classeapprenant WHERE idApprenant = ?";
            PreparedStatement statement2 = connexion.prepareStatement(query2);
            statement2.setInt(1, id);
            statement2.executeUpdate();

            String query3 = "DELETE FROM notes WHERE idApprenant = ?";
            PreparedStatement statement3 = connexion.prepareStatement(query3);
            statement3.setInt(1, id);
            statement3.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error while deleting Apprenant" + e.getMessage());
        }
    }

    @Override
    public Apprenant read(int id) throws DAOException {

        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT a.prenom, a.nom, a.nationalite, a.dtNaiss, a.echeancier, a.matricule, a.sexe," +
                    "c.intitule FROM apprenants a, classes c, classeapprenant ca" +
                    " WHERE a.idApprenant = ? AND c.idClasse = ca.idClasse " +
                    "AND ca.idApprenant = a.idApprenant";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                Apprenant apprenant  = new Apprenant();
                apprenant.setIdApprenant(id);
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setClasse(rs.getString("intitule"));
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                return apprenant;
            }
        }catch (Exception e) {
            throw new DAOException("Error while reading Apprenant" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Apprenant> getList() throws DAOException {
        List<Apprenant> apprenants = new ArrayList<>();

        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT a.idApprenant, a.prenom, a.nom, a.dtNaiss, a.nationalite, a.echeancier, a.sexe," +
                    " a.matricule, c.intitule FROM apprenants a, classes c, classeapprenant ca" +
                    " WHERE c.idClasse = ca.idClasse " +
                    "AND ca.idApprenant = a.idApprenant";
            PreparedStatement statement = connexion.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Apprenant apprenant = new Apprenant();
                apprenant.setIdApprenant(rs.getInt("idApprenant"));
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setClasse(rs.getString("intitule"));
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                apprenants.add(apprenant);
            }
        }catch (Exception e) {
            throw new DAOException("Error while reading Apprenant" + e.getMessage() + e.getCause());
        }
        return apprenants;
    }

    @Override
    public List<Apprenant> search(String stringToSearch) throws DAOException {
        List<Apprenant> apprenants = new ArrayList<>();
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT a.idApprenant, a.prenom, a.nom, a.dtNaiss, a.nationalite, a.echeancier, a.sexe," +
                    "a.matricule, c.intitule FROM apprenants a, classes c, classeapprenant ca " +
                    "WHERE (a.nom LIKE ? OR prenom LIKE ?) AND c.idClasse = ca.idClasse AND ca.idApprenant = a.idApprenant" ;
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, "%" + stringToSearch + "%");
            statement.setString(2, "%" + stringToSearch + "%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Apprenant apprenant = new Apprenant();
                apprenant.setIdApprenant(rs.getInt("idApprenant"));
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setClasse(rs.getString("intitule"));

                //cast the dateNaissance to LocalDate
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                apprenants.add(apprenant);
            }
        }catch(Exception e) {
            throw new DAOException("Error in search Apprenant" + e.getMessage());
        }
        return apprenants;
    }

    //search Apprenant by matricule
    @Override
    public Apprenant searchByMatricule(int matricule) throws DAOException {
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT a.idApprenant, prenom, nom, dtNaiss, nationalite, echeancier, sexe, matricule, c.intitule FROM apprenants a, classeapprenant ca, classes c WHERE matricule = ? AND a.idApprenant = ca.idApprenant AND ca.idClasse = c.idClasse" ;
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, matricule);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Apprenant apprenant = new Apprenant();
                apprenant.setIdApprenant(rs.getInt("idApprenant"));
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setClasse(rs.getString("intitule"));
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                rs.close();
                return apprenant;
            }
        }catch(Exception e) {
            throw new DAOException("Error in search Apprenant" + e.getMessage());
        }
        return null;
    }


    @Override
    public Apprenant searchForCreate(int matricule) throws DAOException {
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT * FROM apprenants WHERE matricule = ?" ;
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, matricule);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                var apprenant = new Apprenant();
                apprenant.setIdApprenant(rs.getInt("idApprenant"));
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                rs.close();
                return apprenant;
            }
        }catch(Exception e) {
            throw new DAOException("Error in search Apprenant" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Apprenant> getApprenantsOfClass(int idClasse) throws DAOException {
        List<Apprenant> apprenants = new ArrayList<>();
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT a.idApprenant, a.prenom, a.nom, a.dtNaiss, a.nationalite, a.echeancier, a.sexe," +
                    "a.matricule, c.intitule FROM apprenants a, classes c, classeapprenant ca " +
                    "WHERE c.idClasse = ? AND c.idClasse = ca.idClasse AND ca.idApprenant = a.idApprenant" ;
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setInt(1, idClasse);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Apprenant apprenant = new Apprenant();
                apprenant.setIdApprenant(rs.getInt("idApprenant"));
                apprenant.setNom(rs.getString("nom"));
                apprenant.setPrenom(rs.getString("prenom"));
                apprenant.setSexe(rs.getString("sexe"));
                apprenant.setMatricule(rs.getInt("matricule"));
                apprenant.setNationalite(rs.getString("nationalite"));
                String dateNaissance = rs.getString("dtNaiss");
                apprenant.setEtatPaiement(rs.getInt("echeancier"));
                apprenant.setClasse(rs.getString("intitule"));
                apprenant.setDateNaissance(LocalDate.parse(dateNaissance, formatter));
                apprenants.add(apprenant);
            }
        }catch(Exception e) {
            throw new DAOException("Error in search Apprenant" + e.getMessage());
        }
        return apprenants;
    }


    @Override
    public void incrementEtatPaiement(Apprenant apprenant) throws DAOException {
        try(Connection connection = DBManager.getConnection()){
            String query = "UPDATE apprenants SET echeancier = ? WHERE idApprenant = ? OR matricule = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, apprenant.getEtatPaiement() + 1);
            statement.setInt(2, apprenant.getIdApprenant());
            statement.setInt(3, apprenant.getMatricule());
            statement.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Error in increment Etat Paiement" + e.getMessage());
        }
    }
}
