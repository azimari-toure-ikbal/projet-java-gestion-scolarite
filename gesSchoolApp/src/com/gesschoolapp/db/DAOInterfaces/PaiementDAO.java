package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.paiement.Paiement;

import java.util.List;

public interface PaiementDAO extends DAO<Paiement>{
    public List<String> getAnnees() throws DAOException;

}
