package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.student.Apprenant;

import java.util.List;

public class ApprenantDAOImp implements SearchDAO<Apprenant> {

    @Override
    public void create(Apprenant obj) throws DAOException {

    }

    @Override
    public void update(Apprenant obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Apprenant read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Apprenant> getList() throws DAOException {
        return null;
    }

    @Override
    public List<Apprenant> search(String stringToSearch) throws DAOException {
        return null;
    }
}
