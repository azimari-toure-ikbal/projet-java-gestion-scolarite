package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.paiement.Paiement;

import java.util.List;

public class PaiementDAOImp implements SearchDAO<Paiement> {
    @Override
    public void create(Paiement obj) throws DAOException {

    }

    @Override
    public void update(Paiement obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Paiement read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Paiement> getList() throws DAOException {
        return null;
    }

    @Override
    public Paiement search(String name) throws DAOException {
        return null;
    }
}
