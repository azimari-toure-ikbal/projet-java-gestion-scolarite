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
    public void create(Note obj) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "INSERT INTO notes (valeur, idApprenant, idModule) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, obj.getNote());
            stmt.setInt(2, obj.getApprenant().getIdApprenant());
            stmt.setInt(3, new ModuleDAOImp().search(obj.getModule()).get(0).getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("In NoteDAOImp.create()\n" + e.getMessage());
        }
    }

    @Override
    public void update(Note obj) throws DAOException {
        try(Connection connexion = DBManager.getConnection()) {
            String query = "UPDATE notes SET valeur = ?, idApprenant = ?, idModule = ? WHERE idNote = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, obj.getNote());
            stmt.setInt(2, obj.getApprenant().getIdApprenant());
            stmt.setInt(3, new ModuleDAOImp().search(obj.getModule()).get(0).getId());
            stmt.setInt(5, obj.getId());
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
                note.setNote(rs.getInt("note"));
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