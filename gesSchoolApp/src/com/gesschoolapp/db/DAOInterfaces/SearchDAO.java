package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;

import java.util.List;

public interface SearchDAO<T> extends DAO<T>{

    public List<T> search(String stringToSearch) throws DAOException;
}
