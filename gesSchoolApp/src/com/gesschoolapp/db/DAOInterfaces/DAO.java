package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;

import java.util.List;

public interface DAO<T> {

//    create, read, getList, update, delete

    public void create(T obj) throws DAOException;
    public void update(T obj) throws DAOException;
    public void delete(int id) throws DAOException;
    public T read(int id) throws DAOException;
    public List<T> getList() throws DAOException;

}
