package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.matieres.Module;
import java.util.List;

public interface ClasseDAO extends SearchDAO<Classe> {
    public List<Apprenant> getApprenantsOfClass(int idClasse) throws DAOException;
    public List<Module> getModulesOfClass(int idClasse) throws DAOException;
}
