package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.ClasseDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.paiement.Echeance;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.utils.Toolbox;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClasseDAOImp implements ClasseDAO {
    @Override
    public Classe create(Classe obj) throws DAOException {
        //Not used
        return null;
    }

    @Override
    public void update(Classe obj) throws DAOException {
        //Not used
    }

    @Override
    public void delete(int id) throws DAOException {
        //Not used
    }

    @Override
    public Classe read(int id) throws DAOException {
        try(Connection connection = DBManager.getConnection()){
            String query = "SELECT * FROM classes WHERE idClasse = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String intitule = rs.getString("intitule");
                int reference = rs.getInt("reference");
                String formation = rs.getString("formation");
                String annee = rs.getString("annee");
                Timestamp timestamp = rs.getTimestamp("views");

                Classe classe = new Classe(id, intitule, reference, annee, formation,
                        new ApprenantDAOImp().getApprenantsOfClass(id),  new ModuleDAOImp().getModulesOfClass(id),
                        timestamp.toLocalDateTime());

                classe = new ClasseDAOImp().setRubriques(classe);
                classe.setEcheancier(new ClasseDAOImp().getEcheancier(id));

                return classe;
            }
        }catch (Exception e){
            throw new DAOException("Error in ClasseDAOImp.read() \n" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Classe> getList() throws DAOException {
        //get all classes from table classes and return them
        List<Classe> classes = new ArrayList<>();
        int id;
        String intitule;
        int reference;
        String formation;
        String annee;
        Timestamp timestamp;
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM classes";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getInt("idClasse");
                intitule = rs.getString("intitule");
                reference = rs.getInt("reference");
                formation = rs.getString("formation");
                annee = rs.getString("annee");
                timestamp = rs.getTimestamp("views");

                Classe classe = new Classe(id, intitule, reference, annee, formation,
                        new ApprenantDAOImp().getApprenantsOfClass(id), new ModuleDAOImp().getModulesOfClass(id),
                        timestamp.toLocalDateTime());
                classe = new ClasseDAOImp().setRubriques(classe);
                classe.setEcheancier(new ClasseDAOImp().getEcheancier(id));
                classes.add(classe);
            }
        } catch (Exception e) {
            throw new DAOException("Error in ClasseDAOImp.getList() \n" + e.getMessage());
        }
        Collections.sort(classes);
        return classes;
    }

    @Override
    public List<Classe> search(String stringToSearch) throws DAOException {
        //get all classes from table classes and return them
        List<Classe> classes = new ArrayList<>();
        int id;
        int reference;
        String intitule;
        String formation;
        String annee;
        Timestamp timestamp;
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM classes WHERE intitule LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + stringToSearch + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("idClasse");
                intitule = rs.getString("intitule");
                reference = rs.getInt("reference");
                formation = rs.getString("formation");
                annee = rs.getString("annee");
                timestamp = rs.getTimestamp("views");

                Classe classe = new Classe(id, intitule, reference, annee, formation,
                        new ApprenantDAOImp().getApprenantsOfClass(id), new ModuleDAOImp().getModulesOfClass(id),
                        timestamp.toLocalDateTime());
                classe = new ClasseDAOImp().setRubriques(classe);
                classe.setEcheancier(new ClasseDAOImp().getEcheancier(id));
                classes.add(classe);
            }
        } catch (Exception e) {
            throw new DAOException("Error in ClasseDAOImp.search()" + e.getMessage());
        }
        return classes;
    }

    public void setLastView(Classe classe) throws DAOException{

        try (Connection connection = DBManager.getConnection()) {
            String query = "UPDATE classes SET views = ? WHERE idClasse = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            statement.setTimestamp(1, timestamp);
            statement.setInt(2, classe.getId());
            statement.executeUpdate();
            classe.setViews(localDateTime);
        } catch (Exception e) {
            throw new DAOException("Error in ClasseDAOImp.incrementViews()" + e.getMessage());
        }
    }

    public Classe setRubriques(Classe classe) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM rubriques WHERE reference = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, classe.getReference());
            ResultSet rs = statement.executeQuery();
            List<Rubrique> rubriquesTemp = Toolbox.getRubriques();
            List<Rubrique> classeRubriques = new ArrayList<>();
            classe.setRubriques(classeRubriques);
            double montant;
            if (rs.next()) {
                for(Rubrique rubrique : rubriquesTemp){
                    montant = rs.getDouble(rubrique.getIntitule());
                    if(montant != 0){
                        classeRubriques.add(new Rubrique(rubrique.getIntitule(), montant));
                    }
                }
                classe.setRubriques(classeRubriques);
                return classe;
            }

        } catch (Exception e) {
            throw new DAOException("Error in ClasseDAOImp.setRubriques()" + e.getMessage() + e.getLocalizedMessage());
        }
        return classe;
    }

    public List<Echeance> getEcheancier(int idClasse) throws DAOException {
        List<Echeance> echeancier = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM echeancier WHERE  idClasse = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idClasse);
            ResultSet rs = statement.executeQuery();
            int id;
            String intitule;
            while (rs.next()){
                id = rs.getInt("idEcheancier");
                intitule = rs.getString("intitule");
                LocalDate date = rs.getDate("date").toLocalDate();
                echeancier.add(new Echeance(id, intitule, date));
            }
        } catch (Exception e) {
            throw new DAOException("Error in ClasseDAOImp.getEcheancier()" + e.getMessage());
        }
        return echeancier;
    }

}
