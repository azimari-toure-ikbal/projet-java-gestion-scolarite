package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.matieres.Note;

import java.util.List;

public interface NoteDAO extends DAO<Note>{
    public List<Note> getNotesOfModule(int idModule) throws DAOException ;

}
