package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.LoginDAO;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.models.users.Utilisateur;

import java.util.List;

public class UserDAOImp implements SearchDAO<Utilisateur>, LoginDAO {
    @Override
    public Utilisateur authenticate(String email, String password) throws DAOException {
        return null;
    }

    @Override
    public Utilisateur search(String name) throws DAOException {
        return null;
    }

    @Override
    public void create(Utilisateur obj) throws DAOException {

    }

    @Override
    public void update(Utilisateur obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Utilisateur read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Utilisateur> getList() throws DAOException {
        return null;
    }
}
