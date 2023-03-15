package com.gesschoolapp.db.DAOInterfaces.extensions;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;

import java.util.List;

public interface NoteDAO extends DAO<Note> {
    public List<Note> getNotesOfModule(int idModule) throws DAOException ;
    public void update (Note obj, int semestre, String utilisateur) throws DAOException;
    public List<Note> getNotesOfApprenant(Apprenant apprenant) throws DAOException;

    }
