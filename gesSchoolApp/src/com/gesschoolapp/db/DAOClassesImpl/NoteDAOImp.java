package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.matieres.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoteDAOImp implements DAO<Note> {
    @Override
    public Note create(Note obj) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "INSERT INTO notes (valeur, idApprenant, idModule) VALUES (?, ?, ?)";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, obj.getNote());
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
    public void update(Note obj) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "UPDATE notes SET valeur = ?, idApprenant = ?, idModule = ? WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, obj.getNote());
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
     * It may be a little bit slower than the other method but
     * we can't have all we want in life smeh...
     *
     * @param obj
     * @param semestre
     * @throws DAOException
     */
    public void update (Note obj, int semestre) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "UPDATE notes n, modules m, apprenants a SET n.valeur = ? WHERE n.idApprenant = a.idApprenant AND n.idModule = m.idModule AND m.semestre = ? AND a.matricule = ? AND m.intitule = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, obj.getNote());
            stmt.setInt(2, semestre);
            stmt.setInt(3, obj.getApprenant().getMatricule());
            stmt.setString(4, obj.getModule());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.update()\n" + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
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
            String query = "SELECT n.valeur, m.intitule as intitule, n.idApprenant FROM notes n, modules m WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Note note = new Note();
                ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
                note.setId(id);
                note.setNote(rs.getInt("valeur"));
                note.setApprenant(apprenantDAOImp.read(rs.getInt("idApprenant")));
                note.setModule(rs.getString("intitule"));
                return note;
            }
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.read()\n" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Note> getList() throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "SELECT n.idNote, n.valeur, m.intitule as module, n.idApprenant as apprenant " +
                    "FROM notes n, modules m WHERE n.idModule = m.idModule";
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

}
