package com.gesschoolapp.view.admin;

import com.gesschoolapp.db.DAOClassesImpl.ActionDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.view.util.ActionType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ActionItemController {

    AdminUIController superController;

    Action thisAction = null;

    ActionDAOImp aDao = new ActionDAOImp();

    @FXML
    private Button btnCancel1;

    @FXML
    private ImageView iconTypeObject;

    @FXML
    private Label labelDate1;

    @FXML
    private Label labelId1;

    @FXML
    private Label labelAuteur1;

    @FXML
    private Label labelObjet1;

    @FXML
    private Label labelType1;

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

    public void setData(Action act){
        labelId1.setText(act.getIdAction()+"");
        int minute = act.getDate().getMinute();
        int hour = act.getDate().getHour();
        if(minute<10){
            labelDate1.setText(hour+"h:0"+ minute);
        }else{
            labelDate1.setText(hour+"h:"+ minute);
        }
        labelAuteur1.setText(act.getActor());
//        System.out.println(act.getObject());
//        labelObjet1.setText(act.getObject().toString());
        if(act.getAction() == ActionType.ADD){
            labelType1.setText("AJOUT");
            labelType1.setStyle("-fx-background-color:  #48A74C;-fx-text-fill: white;-fx-background-radius: 10px;-fx-padding: 5;");
        }else if(act.getObject() == ActionType.UPDATE){
            labelType1.setText("MODIFICATION");
            labelType1.setStyle("-fx-background-color: #F0B606;-fx-text-fill: white;-fx-background-radius: 10px;-fx-padding: 5;");
        }else{
            labelType1.setText("SUPPRESSION");
            labelType1.setStyle("-fx-background-color: #E9243B;-fx-text-fill: white;-fx-background-radius: 10px;-fx-padding: 5;");
        }

        thisAction = act;
    }

    @FXML
    void onCancelAction(ActionEvent event) {

    }

}

