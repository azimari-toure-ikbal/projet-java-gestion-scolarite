package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.actions.Notification;
import com.gesschoolapp.models.users.Utilisateur;

import java.util.List;

public interface UserDAO{
    public Utilisateur   authenticate(String email, String password) throws DAOException;
    void setNotifSeen(int id) throws DAOException;
    public List<Notification> getNotifs(String name) throws DAOException;

}
