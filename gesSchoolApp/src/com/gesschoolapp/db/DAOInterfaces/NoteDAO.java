package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;

import java.util.List;

public interface NoteDAO extends DAO<Note>{
    public List<Note> getNotesOfModule(int idModule) throws DAOException ;
    public void update (Note obj, int semestre, String user) throws DAOException;
    public List<Note> getNotesOfApprenant(Apprenant apprenant) throws DAOException;


    }
