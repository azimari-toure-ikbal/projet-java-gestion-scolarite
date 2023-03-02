package com.gesschoolapp.db.DAOInterfaces.extensions;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.models.paiement.Paiement;

import java.util.List;

public interface PaiementDAO extends DAO<Paiement> {
    public List<String> getAnnees() throws DAOException;

}
