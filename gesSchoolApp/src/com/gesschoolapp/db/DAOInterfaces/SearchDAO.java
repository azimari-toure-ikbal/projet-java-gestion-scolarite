package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;

public interface SearchDAO<T> extends DAO<T>{

    public T search(String name) throws DAOException;
}
