package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.ClasseDAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.matieres.Module;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClasseDAOImp implements SearchDAO<Classe> {
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
        return this.getList().get(id - 1);
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
                Timestamp timestamp = rs.getTimestamp("views");
                List<Module> modules = new ModuleDAOImp().getList();
                if (modules != null) {
                    modules = modules.stream().filter(module -> Objects.equals(module.getClasse(), intitule)).collect(Collectors.toList());
                }

                List<Apprenant> apprenants = new ApprenantDAOImp().getList().stream().
                        filter(apprenant -> Objects.equals(apprenant.getClasse(), intitule)).toList();


                Classe classe = new Classe(id, intitule, reference, annee, formation, apprenants, modules,
                        timestamp.toLocalDateTime());
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
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM classes WHERE intitule LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + stringToSearch + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idClasse");
                String intitule = rs.getString("intitule");
                int reference = rs.getInt("reference");
                String formation = rs.getString("formation");
                String annee = rs.getString("annee");
                Timestamp timestamp = rs.getTimestamp("views");

                List<Module> modules = new ModuleDAOImp().getList().stream().
                        filter(module -> Objects.equals(module.getClasse(), intitule)).toList();

                List<Apprenant> apprenants = new ApprenantDAOImp().getList().stream().
                        filter(apprenant -> Objects.equals(apprenant.getClasse(), intitule)).toList();

                Classe classe = new Classe(id, intitule, reference, annee, formation,apprenants, modules,
                               timestamp.toLocalDateTime());
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

}
