package com.gesschoolapp.db.DAOInterfaces.extensions;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.DAO;
import com.gesschoolapp.models.matieres.Module;

import java.util.List;

public interface ModuleDAO extends DAO<Module> {
    public List<Module> getModulesOfClass(int idClasse) throws DAOException;

    }
