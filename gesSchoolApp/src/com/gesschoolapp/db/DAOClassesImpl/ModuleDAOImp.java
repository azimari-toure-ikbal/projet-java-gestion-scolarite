package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModuleDAOImp implements DAO<Module>, SearchDAO<Module> {
    @Override
    public void create(Module obj) throws DAOException {

    }

    @Override
    public void update(Module obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Module read(int id) throws DAOException {
        List<Module> modules = new ArrayList<>();

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT m.idClasse, m.intitule, c.intitule as class FROM modules m, classes c WHERE idModule = ? AND c.intitule = m.idClasse";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Module module = new Module();
                String intitule = rs.getString("intitule");
                List<Note> notes = new NoteDAOImp().getList().stream().
                        filter(note -> Objects.equals(note.getModule(), intitule)).collect(Collectors.toList());

                module.setId(id);
                module.setIntitule(intitule);
                module.setNotes(notes);
                module.setClasse(rs.getString("class"));

                return module;
            }

        } catch (Exception e) {
            throw new DAOException("Error in ModuleDAOImp.read() \n" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Module> getList() throws DAOException {
        List<Module> modules = new ArrayList<>();

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT m.idModule, m.intitule, c.intitule as classe FROM `modules` m, classes c WHERE m.idClasse = c.idClasse";
            PreparedStatement stmt = connexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Module module = new Module();
                int idModule = rs.getInt("idModule");
                String intitule = rs.getString("intitule");
                List<Note> notes = new NoteDAOImp().getList().stream().
                        filter(note -> Objects.equals(note.getModule(), intitule)).collect(Collectors.toList());

                module.setId(idModule);
                module.setIntitule(intitule);
                module.setNotes(notes);
                module.setClasse(rs.getString("classe"));

                modules.add(module);

            }
            return modules;

        } catch (Exception e) {
            throw new DAOException("Error in ModuleDAOImp.getList() \n" + e.getMessage());
        }
    }


    @Override
    public List<Module> search(String stringToSearch) throws DAOException {
        List<Module> modules = new ArrayList<>();

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT m.idModule, m.intitule, c.intitule as classe FROM `modules` m, classes c WHERE m.idClasse = c.idClasse AND m.intitule LIKE ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, "%" + stringToSearch + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Module module = new Module();
                int idModule = rs.getInt("idModule");
                String intitule = rs.getString("intitule");
                List<Note> notes = new NoteDAOImp().getList().stream().
                        filter(note -> Objects.equals(note.getModule(), intitule)).collect(Collectors.toList());

                module.setId(idModule);
                module.setIntitule(intitule);
                module.setNotes(notes);
                module.setClasse(rs.getString("classe"));

                modules.add(module);

            }
            return modules;

        } catch (Exception e) {
            throw new DAOException("Error in ModuleDAOImp.search() \n" + e.getMessage());
        }
    }
}
