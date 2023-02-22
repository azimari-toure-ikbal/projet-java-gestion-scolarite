package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;

import java.util.List;

public interface DAO<T> {

    public T create(T obj, String user) throws DAOException;
    public void update(T obj, String user) throws DAOException;
    public void delete(int id, String user) throws DAOException;
    public T read(int id) throws DAOException;
    public List<T> getList() throws DAOException;
    public List<T> search(String stringToSearch) throws DAOException;
}
