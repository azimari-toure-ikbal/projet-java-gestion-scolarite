package com.gesschoolapp.db.DAOInterfaces;

import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;

public interface ActionDAO{
    public void cancelAction(Action action, String admin);
    void addNotification(String utilisateur, String admin, String message );
    void cancelAddApprenant(Apprenant apprenant);
    void cancelDeleteApprenant(Apprenant apprenant);
    void cancelUpdateApprenant(Apprenant apprenant);
    void cancelAddModule(Module module);
    void cancelUpdateModule(Module module);
    void cancelDeleteModule(Module module);
    void cancelUpdateNote(Note note);
    void cancelAddPaiement(Paiement paiement);
}
