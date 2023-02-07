package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.classroom.Classe;

import java.util.List;

public class ClasseDAOImp implements SearchDAO<Classe> {
    @Override
    public void create(Classe obj) throws DAOException {

    }

    @Override
    public void update(Classe obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Classe read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Classe> getList() throws DAOException {
        return null;
    }

    @Override
    public List<Classe> search(String stringToSearch) throws DAOException {
        return null;
    }
}
