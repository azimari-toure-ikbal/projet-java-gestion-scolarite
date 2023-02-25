package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.ModuleDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.serial.ActionManager;
import com.gesschoolapp.view.util.ActionType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleDAOImp implements ModuleDAO {
    @Override
    public Module create(Module obj, String user) throws DAOException {

        try(Connection connection =  DBManager.getConnection() ){

            if(obj.getId() == 0 ){
                String query = "INSERT INTO modules (intitule, idClasse, semestre ) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, obj.getIntitule());
                stmt.setInt(2, new ClasseDAOImp().search(obj.getClasse()).get(0).getId());
                stmt.setInt(3, obj.getSemestre());
                stmt.executeUpdate();
            }else{
                String query = "INSERT INTO modules (idModule, intitule, idClasse, semestre ) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, obj.getId());
                stmt.setString(2, obj.getIntitule());
                stmt.setInt(3, new ClasseDAOImp().search(obj.getClasse()).get(0).getId());
                stmt.setInt(4, obj.getSemestre());
                stmt.executeUpdate();
            }

            List<Apprenant> apprenants = new ClasseDAOImp().search(obj.getClasse()).get(0).getApprenants();
;           int idModule = new ModuleDAOImp().search(obj.getIntitule()).get(0).getId();
            for (Apprenant apprenant : apprenants) {
                String query2 = "INSERT INTO notes (idApprenant, idModule, valeur) VALUES (?, ?, ?)";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setInt(1, apprenant.getIdApprenant());
                stmt2.setInt(2, idModule);
                stmt2.setDouble(3, 0);
                stmt2.executeUpdate();
            }

            if(!Objects.equals(user, "admin")){
                Action action = new Action();
                action.setActor(user);
                action.setAction(ActionType.ADD);
                action.setObject(obj);
                action.setDate(LocalDateTime.now());
                ActionManager.add(action);
            }
            return new ModuleDAOImp().read(idModule);
        }catch(Exception e){
            throw new DAOException("Error while creating module : " + e.getMessage());
        }
    }

    @Override
    public void update(Module obj, String user) throws DAOException {

        try(Connection connection =  DBManager.getConnection() ){
            String query = "UPDATE modules SET intitule = ?, idClasse = ?, semestre = ? WHERE idModule = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, obj.getIntitule());
            stmt.setInt(2, new ClasseDAOImp().search(obj.getClasse()).get(0).getId());
            stmt.setInt(3, obj.getSemestre());
            stmt.setInt(4, obj.getId());
            stmt.executeUpdate();

            if(!Objects.equals(user, "admin")){
                Action action = new Action();
                action.setActor(user);
                action.setAction(ActionType.UPDATE);
                action.setObject(obj);
                action.setDate(LocalDateTime.now());
                ActionManager.add(action);
            }

        }catch(Exception e){
            throw new DAOException("Error while updating module : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id, String user) throws DAOException {

        try(Connection connection =  DBManager.getConnection() ){
            String query = "DELETE FROM modules WHERE idModule = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            String query2 = "DELETE FROM notes WHERE idModule = ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();

            if(!Objects.equals(user, "admin")){
                Action action = new Action();
                action.setActor(user);
                action.setAction(ActionType.DELETE);
                action.setObject(new ModuleDAOImp().read(id));
                action.setDate(LocalDateTime.now());
                ActionManager.add(action);
            }
        }catch(Exception e){
            throw new DAOException("Error while deleting module : " + e.getMessage());
        }

    }

    @Override
    public Module read(int id) throws DAOException {
        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT m.idClasse, m.intitule, c.intitule as class, m.semestre FROM modules m, classes c WHERE idModule = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Module module = new Module();
                String intitule = rs.getString("intitule");
                List<Note> notes = new NoteDAOImp().getNotesOfModule(id);

                module.setId(id);
                module.setIntitule(intitule);
                module.setNotes(notes);
                module.setClasse(rs.getString("class"));
                module.setSemestre(rs.getInt("semestre"));
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
            String query = "SELECT m.idModule, m.intitule, c.intitule as classe, m.semestre FROM `modules` m, classes c WHERE m.idClasse = c.idClasse";
            PreparedStatement stmt = connexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int idModule;
            while (rs.next()) {
                Module module = new Module();
                idModule = rs.getInt("idModule");
                module.setId(idModule);
                module.setIntitule(rs.getString("intitule"));
                module.setNotes(new NoteDAOImp().getNotesOfModule(idModule));
                module.setClasse(rs.getString("classe"));
                module.setSemestre(rs.getInt("semestre"));
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
            String query = "SELECT m.idModule, m.intitule, c.intitule as classe, m.semestre FROM `modules` m, classes c WHERE m.idClasse = c.idClasse AND m.intitule LIKE ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, "%" + stringToSearch + "%");
            ResultSet rs = stmt.executeQuery();
            int idModule;
            while (rs.next()) {
                Module module = new Module();
                idModule = rs.getInt("idModule");

                module.setId(idModule);
                module.setIntitule(rs.getString("intitule"));
                module.setNotes(new NoteDAOImp().getNotesOfModule(idModule));
                module.setClasse(rs.getString("classe"));
                module.setSemestre(rs.getInt("semestre"));

                modules.add(module);
            }
            return modules;

        } catch (Exception e) {
            throw new DAOException("Error in ModuleDAOImp.search() \n" + e.getMessage());
        }
    }

    public List<Module> getModulesOfClass(int idClasse) throws DAOException {
        List<Module> modules = new ArrayList<>();

        try(Connection connexion = DBManager.getConnection()){
            String query = "SELECT m.idModule, m.intitule, c.intitule as classe, m.semestre FROM `modules` m, classes c" +
                    " WHERE m.idClasse = c.idClasse AND m.idClasse = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, idClasse);
            ResultSet rs = stmt.executeQuery();

            int idModule;
            while (rs.next()) {
                Module module = new Module();
                idModule = rs.getInt("idModule");

                module.setId(idModule);
                module.setIntitule( rs.getString("intitule"));
                module.setNotes(new NoteDAOImp().getNotesOfModule(idModule));
                module.setClasse(rs.getString("classe"));
                module.setSemestre(rs.getInt("semestre"));

                modules.add(module);
            }
            return modules;

        } catch (Exception e) {
            throw new DAOException("Error in ModuleDAOImp.getModulesOfClass() \n" + e.getMessage());
        }
    }
}
