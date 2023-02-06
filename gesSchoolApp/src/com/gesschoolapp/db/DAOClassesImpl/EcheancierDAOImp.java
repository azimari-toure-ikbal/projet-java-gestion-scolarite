package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.paiement.Echeance;
import com.gesschoolapp.models.paiement.Echeancier;

import java.util.List;

public class EcheancierDAOImp implements SearchDAO<Echeance> {
    @Override
    public void create(Echeance obj) throws DAOException {

    }

    @Override
    public void update(Echeance obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Echeance read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Echeance> getList() throws DAOException {
        return null;
    }

    @Override
    public List<Echeance> search(String stringToSearch) throws DAOException {
        return null;
    }
}
