package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.db.DAOInterfaces.ActionDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.view.util.ActionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Objects;

public class ActionDAOImp implements ActionDAO {

    @Override
    public void cancelAction(Action action, String admin){
        String message = "";
        if(action.getObject() instanceof Apprenant){
            message = "L'action " + action.getAction() + " sur l'apprenant " + ((Apprenant)action.getObject()).getFullName() + " a été annulée par l'administrateur " + admin + " le " + LocalDateTime.now() + ".";
            switch (action.getAction()){
                case ADD -> cancelAddApprenant((Apprenant) action.getObject());
                case DELETE -> cancelDeleteApprenant((Apprenant) action.getObject());
                case UPDATE -> cancelUpdateApprenant((Apprenant) action.getObject());
            }
        }
        else if(action.getObject() instanceof Module){
            message = "L'action " + action.getAction() + " sur le module " + ((Module)action.getObject()).getIntitule() + " a été annulée par l'administrateur " + admin + " le " + LocalDateTime.now() + ".";
            switch (action.getAction()){
                case ADD -> cancelAddModule((Module) action.getObject());
                case DELETE -> cancelDeleteModule((Module) action.getObject());
                case UPDATE -> cancelUpdateModule((Module) action.getObject());
            }
        }
        else if(action.getObject() instanceof Note){
            message = "L'action " + action.getAction() + " sur la note de " + ((Note)action.getObject()).getApprenant().getFullName() + " dans le module " + ((Note)action.getObject()).getModule() + " a été annulée par l'administrateur " + admin + " le " + LocalDateTime.now() + ".";
            if (Objects.requireNonNull(action.getAction()) == ActionType.UPDATE) {
                cancelUpdateNote((Note) action.getObject());
            }
        }
        else if(action.getObject() instanceof Paiement){
            message = "L'action " + action.getAction() + " sur le paiement de " + ((Paiement)action.getObject()).getApprenant().getFullName() + " a été annulée par l'administrateur " + admin + " le " + LocalDateTime.now() + ".";
            if (Objects.requireNonNull(action.getAction()) == ActionType.ADD) {
                cancelAddPaiement((Paiement) action.getObject());
            }
        }
        if(!message.equals(""))
            addNotification(action.getActor(), admin, message);
    }

    @Override
    public void addNotification(String utilisateur, String admin, String message ){
        try(Connection connection = DBManager.getConnection()){
            String query = "INSERT INTO notifications (utilisateur, admin, date, message) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, utilisateur);
            stmt.setString(2, admin);
            stmt.setString(3, String.valueOf(LocalDateTime.now()));
            stmt.setString(4, message);
        }catch (Exception e){
            System.out.println("Error in addNotification() : " + e.getMessage());
        }
    }

    @Override
    public void cancelAddApprenant(Apprenant apprenant){
        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
        try{
            apprenantDAOImp.delete(apprenant.getIdApprenant(), "admin");
        }catch (Exception e){
            System.out.println("Error in cancelAddApprenant() : " + e.getMessage());
        }
    }

    @Override
    public void cancelDeleteApprenant(Apprenant apprenant){
        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
        try{
            apprenantDAOImp.create(apprenant, "admin");
        }catch (Exception e){
            System.out.println("Error in cancelDeleteApprenant() : " + e.getMessage());
        }
    }

    @Override
    public void cancelUpdateApprenant(Apprenant apprenant){
        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
        try{
            apprenantDAOImp.update(apprenant, "admin");
        }catch (Exception e){
            System.out.println("Error in cancelUpdateApprenant() : " + e.getMessage());
        }
    }

    @Override
    public void cancelAddModule(Module module){
        ModuleDAOImp moduleDAOImp = new ModuleDAOImp();
        try{
            moduleDAOImp.delete(module.getId(), "admin");
        }catch (Exception e){
            System.out.println("Error in cancelAddModule() : " + e.getMessage());
        }
    }

    @Override
    public void cancelUpdateModule(Module module){
        ModuleDAOImp moduleDAOImp = new ModuleDAOImp();
        try{
            moduleDAOImp.update(module, "admin");
        }catch (Exception e){
            System.out.println("Error in cancelUpdateModule() : " + e.getMessage());
        }
    }

    @Override
    public void cancelDeleteModule(Module module){
        ModuleDAOImp moduleDAOImp = new ModuleDAOImp();
        try{
            moduleDAOImp.create(module, "admin");
        }catch (Exception e){
            System.out.println("Error in cancelDeleteModule() : " + e.getMessage());
        }
    }

    @Override
    public void cancelUpdateNote(Note note){
        NoteDAOImp noteDAOImp = new NoteDAOImp();
        try{
            noteDAOImp.update(note, "admin");
        }catch (Exception e){
            System.out.println("Error in cancelUpdateNote() : " + e.getMessage());
        }
    }

    @Override
    public void cancelAddPaiement(Paiement paiement){
        try(Connection connection = DBManager.getConnection()){
            PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
            paiementDAOImp.delete(paiement.getIdPaiement(), "admin");
        }catch (Exception e){
            System.out.println("Error in cancelAddPaiement() : " + e.getMessage());
        }
    }
}
