package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.ClasseDAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.matieres.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAOImp implements SearchDAO<Classe>, ClasseDAO {
    @Override
    public void create(Classe obj) throws DAOException {

    }

    @Override
    public void update(Classe obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Classe read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Classe> getList() throws DAOException {
        //get all classes from table classes and return them
        List<Classe> classes = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM classes";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idClasse");
                String intitule = rs.getString("intitule");
                int reference = rs.getInt("reference");
                String formation = rs.getString("formation");
                String annee = rs.getString("annee");

                Classe classe = new Classe(id, intitule, reference, annee, formation, this.getApprenantsOfClass(id), this.getModulesOfClass(id));
                classes.add(classe);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return classes;
    }

    @Override
    public List<Classe> search(String stringToSearch) throws DAOException {
        return null;
    }

    @Override
    public List<Module> getModulesOfClass(int idClasse) throws DAOException {
        //get all modules of a class, module class has id, intitule
        List<Module> modules = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM modules WHERE idClasse=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idClasse);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idModule");
                String intitule = rs.getString("intitule");
                Module module = new Module(id, intitule);
                modules.add(module);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return modules;
    }

    @Override
    public List<Apprenant> getApprenantsOfClass(int idClasse) throws DAOException{
        //get all apprenants of a class, apprenant has idApprenant, matricule, nom, prenom, dateNaissance, sexe, natioalite, etatPaiement
        List<Apprenant> apprenants = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM apprenants a, classeapprenant c WHERE c.idClasse=? && a.idApprenant=c.idApprenant";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idClasse);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idApprenant");
                int matricule = rs.getInt("matricule");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String dtNaiss = rs.getString("dtNaiss");
                String sexe = rs.getString("sexe");
                String nationalite = rs.getString("nationalite");
                int etatPaiement = rs.getInt("echeancier");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                //convert String to LocalDate
                LocalDate dateNaissance = LocalDate.parse(dtNaiss, formatter);

                Apprenant apprenant = new Apprenant(id, matricule, nom, prenom, dateNaissance, sexe, nationalite, etatPaiement);
                apprenants.add(apprenant);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return apprenants;
    }

}
