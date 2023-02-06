package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.paiement.Echeancier;

import java.util.List;

public class EcheancierDAOImp implements SearchDAO<Echeancier> {
    @Override
    public void create(Echeancier obj) throws DAOException {

    }

    @Override
    public void update(Echeancier obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Echeancier read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Echeancier> getList() throws DAOException {
        return null;
    }

    @Override
    public Echeancier search(String name) throws DAOException {
        return null;
    }
}
