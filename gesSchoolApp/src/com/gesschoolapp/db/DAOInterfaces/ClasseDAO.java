package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.paiement.Echeance;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.matieres.Module;
import java.util.List;

public interface ClasseDAO extends DAO<Classe> {
    public void setLastView(Classe classe) throws DAOException;
    public Classe setRubriques(Classe classe) throws DAOException;
    public List<Echeance> getEcheancier(int idClasse) throws DAOException;

}
