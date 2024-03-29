package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.db.DAOInterfaces.ActionDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.serial.ActionManager;
import com.gesschoolapp.utils.ActionComparatorByState;
import com.gesschoolapp.utils.ActionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ActionDAOImp implements ActionDAO {

    @Override
    public void cancelAction(Action action, String admin){
        String message = "Votre action " + action.getAction() + " sur  " + action.getObjectType() +
                " a été annulée par l'administrateur " + admin + ".";

        if(action.getObject() instanceof Apprenant){
            switch (action.getAction()){
                case ADD -> cancelAddApprenant((Apprenant) action.getObject());
                case DELETE -> cancelDeleteApprenant((Apprenant) action.getObject());
                case UPDATE -> cancelUpdateApprenant((Apprenant) action.getObject());
            }
        }
        addNotification(action.getActor(), admin, message);
    }


    @Override
    public void addNotification(String utilisateur, String admin, String message ){
        try(Connection connection = DBManager.getConnection()){
            String query = "INSERT INTO notifications (utilisateur, admin, date, message) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, utilisateur);
            stmt.setString(2, admin);
            stmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmt.setString(4, message);
            stmt.executeUpdate();
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
    public Action read(int idAction){
        try(Connection connection = DBManager.getConnection()){
            String query = "SELECT * FROM actions WHERE idAction = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idAction);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Action action = new Action();
                action.setIdAction(rs.getInt("idAction"));
                action.setDate(rs.getTimestamp("date").toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                action.setActor(rs.getString("actor"));
                action.setAction(ActionType.valueOf(rs.getString("typeAction")));
                action.setObject(ActionManager.DeserializeObjectFromByteArray(rs.getBytes("object")));
                return action;
            }
        }catch (Exception e){
            System.out.println("Error in readAction() : " + e.getMessage());
        }
        return null;
    }


    @Override
    public void create(Action action){
        try(Connection connection = DBManager.getConnection()){
            String query = "INSERT INTO actions (object, actor, date, canceled, typeAction) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBytes(1, ActionManager.SerializeObjectToByteArray(action.getObject()));
            stmt.setString(2, action.getActor());
            stmt.setString(3, String.valueOf(LocalDateTime.now()));
            stmt.setBoolean(4, false);
            stmt.setString(5, String.valueOf(action.getAction()));
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Error in createAction() : " + e.getMessage());
        }
    }


    @Override
    public List<Action> getActions(){
        try(Connection connection = DBManager.getConnection()){
            String query = "SELECT * FROM actions";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<Action> actions = new ArrayList<>();
            while(rs.next()){
                Action action = new Action();
                action.setIdAction(rs.getInt("idAction"));
                action.setDate(rs.getTimestamp("date").toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                action.setActor(rs.getString("actor"));
                action.setAction(ActionType.valueOf(rs.getString("typeAction")));
                action.setObject(ActionManager.DeserializeObjectFromByteArray(rs.getBytes("object")));
                action.setCanceled(rs.getBoolean("canceled"));
                actions.add(action);
            }
            actions.sort(new ActionComparatorByState());
            return actions;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getActions() : " + e.getMessage());
        }
        return null;
    }


    @Override
    public void setActionCanceled(Action action){
        try(Connection connection = DBManager.getConnection()){
            String query = "UPDATE actions SET canceled = ? WHERE idAction = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBoolean(1, true);
            stmt.setInt(2, action.getIdAction());
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Error in setActionCanceled() : " + e.getMessage());
        }
    }


    @Override
    public Object getCurrentObject(Action action){
        try{
            if(action.getObject() instanceof Apprenant){
                ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
                return apprenantDAOImp.read(((Apprenant) action.getObject()).getIdApprenant());
            }
            else if(action.getObject() instanceof Note){
                NoteDAOImp noteDAOImp = new NoteDAOImp();
                return noteDAOImp.read(((Note) action.getObject()).getId());
            }
        }catch (Exception e){
            System.out.println("Error in getActionObject() : " + e.getMessage());
        }
        return null;
    }


    @Override
    public void delete(int idAction){
        try(Connection connection = DBManager.getConnection()){
            String query = "DELETE FROM actions WHERE idAction = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idAction);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Error in deleteAction() : " + e.getMessage());
        }
    }
}
