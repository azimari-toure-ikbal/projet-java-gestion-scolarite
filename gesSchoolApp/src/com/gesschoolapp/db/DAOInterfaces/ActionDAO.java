package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;

import java.util.List;

public interface ActionDAO{
    void cancelAction(Action action, String admin);
    void addNotification(String utilisateur, String admin, String message);
    void cancelAddApprenant(Apprenant apprenant);
    void cancelDeleteApprenant(Apprenant apprenant);
    void cancelUpdateApprenant(Apprenant apprenant);
    List<Action> getActions();
    void create(Action action);
    void setActionCanceled(Action action);
    Object getCurrentObject(Action action);
    void delete(int idAction);
    Action read(int idAction);

}
