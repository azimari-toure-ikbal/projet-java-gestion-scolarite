package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.users.Utilisateur;

public interface LoginDAO{

    public Utilisateur   authenticate(String email, String password) throws DAOException;
}
