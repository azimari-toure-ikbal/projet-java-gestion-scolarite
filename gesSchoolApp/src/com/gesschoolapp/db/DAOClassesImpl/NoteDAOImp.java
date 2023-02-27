package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.NoteDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
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

public class NoteDAOImp implements NoteDAO {
    @Override
    public Note create(Note obj, String user) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "INSERT INTO notes (valeur, idApprenant, idModule) VALUES (?, ?, ?)";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setFloat(1, obj.getNote());
            stmt.setInt(2, obj.getApprenant().getIdApprenant());
            stmt.setInt(3, new ModuleDAOImp().search(obj.getModule()).get(0).getId());
            stmt.executeUpdate();
            return getList().get(getList().size() - 1);
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.create()\n" + e.getMessage());
        }
    }

    /**
     * 
     * You can also use the surronding method to update a note
     * To use the surronding method, you have to pass the 'semestre' of the module
     * 
     * @param obj 
     * @throws DAOException
     */
    @Override
    public void update(Note obj, String user) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            System.out.println("Updating note..." + obj.getId());
            String query = "UPDATE notes SET valeur = ?, idApprenant = ?, idModule = ? WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setFloat(1, obj.getNote());
            stmt.setInt(2, obj.getApprenant().getIdApprenant());
            stmt.setInt(3, new ModuleDAOImp().search(obj.getModule()).get(0).getId());
            stmt.setInt(4, obj.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.update()\n" + e.getMessage());
        }
    }
    /**
     * 
     * This method is desegned to solve to problem of id
     * It may be a bit slower than the other method but
     * we can't have all we want in life smeh...
     *
     * @param obj
     * @param semestre
     * @throws DAOException
     * 
     */
    @Override
    public void update (Note obj, int semestre, String user) throws DAOException {

        try(Connection connexion = DBManager.getConnection()) {
            String preQuery =  "SELECT n.idNote FROM notes n, modules m, apprenants a WHERE n.idApprenant = a.idApprenant AND n.idModule = m.idModule AND m.semestre = ? AND a.matricule = ? AND m.intitule = ?";
            PreparedStatement preparedStatementtmt = connexion.prepareStatement(preQuery);
            preparedStatementtmt.setInt(1, semestre);
            preparedStatementtmt.setInt(2, obj.getApprenant().getMatricule());
            preparedStatementtmt.setString(3, obj.getModule());
            ResultSet rs = preparedStatementtmt.executeQuery();

            if(rs.next()) {
                obj.setId(rs.getInt("idNote"));
            }

            if(!Objects.equals(user, "admin")){
                Action action = new Action();
                action.setActor(user);
                action.setAction(ActionType.UPDATE);
                action.setObject(read(obj.getId()));
                action.setDate(LocalDateTime.now());
                new ActionDAOImp().create(action);
            }

            String query = "UPDATE notes SET valeur = ? WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setFloat(1, obj.getNote());
            stmt.setInt(2, obj.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.update():\n" + e.getMessage());
        }
    }

    @Override
    public List<Note> getNotesOfApprenant(Apprenant apprenant) throws DAOException {

        List<Note> notes = new ArrayList<>();
        try(Connection connexion = DBManager.getConnection()) {
            String query = "SELECT n.idNote, n.valeur, m.intitule as module, n.idApprenant as apprenant " +
                    "FROM notes n, modules m, apprenants a WHERE n.idModule = m.idModule AND n.idApprenant = a.idApprenant AND a.echeancier > 0 AND a.matricule = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, apprenant.getMatricule());
            ResultSet rs = stmt.executeQuery();
            Note note = new Note();
            while (rs.next()) {
                note.setId(rs.getInt("idNote"));
                note.setNote(rs.getInt("valeur"));
                note.setApprenant(new ApprenantDAOImp().read(rs.getInt("apprenant")));
                note.setModule(rs.getString("module"));
                notes.add(note);
            }
            return notes;
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.getNotesOfApprenant()\n" + e.getMessage());
        }
    }

    @Override
    public void delete(int id, String user) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "DELETE FROM notes WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.delete()\n" + e.getMessage());
        }

    }

    @Override
    public Note read(int id) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "SELECT n.valeur, m.intitule as intitule, n.idApprenant FROM notes n, modules m, apprenants a" +
                    " WHERE idNote = ? && n.idModule = m.idModule && n.idApprenant = a.idApprenant ";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Note note = new Note();
                note.setId(id);
                note.setNote(rs.getInt("valeur"));
                note.setApprenant(new ApprenantDAOImp().read(rs.getInt("idApprenant")));
                note.setModule(rs.getString("intitule"));
                return note;
            }
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.read()\n : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Note> getList() throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "SELECT n.idNote, n.valeur, m.intitule as module, n.idApprenant as apprenant " +
                    "FROM notes n, modules m, apprenants a WHERE n.idModule = m.idModule AND a.echeancier > 0 " +
                    "AND n.idApprenant = a.idApprenant";
            PreparedStatement stmt = connexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<Note> notes = new ArrayList<>();
            while (rs.next()) {
                Note note = new Note();
                ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
                note.setId(rs.getInt("idNote"));
                note.setNote(rs.getInt("valeur"));
                note.setApprenant(apprenantDAOImp.read(rs.getInt("apprenant")));
                note.setModule(rs.getString("module"));
                notes.add(note);
            }
            return notes;
        } catch (Exception e) {
            throw new DAOException(e.getMessage() + "\nIn NoteDAOImp.getList()");
        }
    }

    @Override
    public List<Note> search(String stringToSearch) throws DAOException {
        return null;
    }

    @Override
    public List<Note> getNotesOfModule(int idModule) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "SELECT n.idNote, n.valeur, m.intitule as module, n.idApprenant as apprenant " +
                    "FROM notes n, modules m, apprenants a WHERE n.idModule = m.idModule AND m.idModule = ? " +
                    "AND a.echeancier > 0 AND n.idApprenant = a.idApprenant ";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, idModule);
            ResultSet rs = stmt.executeQuery();

            List<Note> notes = new ArrayList<>();
            while (rs.next()) {
                Note note = new Note();
                ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
                note.setId(rs.getInt("idNote"));
                note.setNote(rs.getInt("valeur"));
                note.setApprenant(apprenantDAOImp.read(rs.getInt("apprenant")));
                note.setModule(rs.getString("module"));
                notes.add(note);
            }
            return notes;
        } catch (Exception e) {
            throw new DAOException(e.getMessage() + "\nIn NoteDAOImp.getNotesOfModule()");
        }
    }
}
