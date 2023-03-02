package com.gesschoolapp.db.DAOInterfaces.extensions;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.models.student.Apprenant;

import java.util.List;

public interface ApprenantDAO extends DAO<Apprenant> {
    public Apprenant searchByMatricule(int matricule) throws DAOException;
    public Apprenant searchForCreate(int matricule) throws DAOException;
    public List<Apprenant> getApprenantsOfClass(int idClasse) throws DAOException;
    public void incrementEtatPaiement(Apprenant apprenant) throws DAOException;

}

