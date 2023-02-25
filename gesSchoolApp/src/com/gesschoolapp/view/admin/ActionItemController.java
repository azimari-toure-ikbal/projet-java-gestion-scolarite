package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ActionDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.serial.ActionManager;
import com.gesschoolapp.view.util.ActionType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionItemController {

    AdminUIController superController;

    @FXML
    private Line canceledBar;

    @FXML
    private HBox panelCard;

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

    @FXML
    void actionBtnClicked(ActionEvent event) {
        if (event.getSource() == btnCancel1) {
                //ask for confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText("Vous êtes sur le point d'annuler une action de " + thisAction.getActor());
                alert.setContentText("Voulez-vous continuer ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    thisAction.cancelAction(superController.getCurrentUser().getFullName());
                    superController.setListeDesActions();
                    superController.setMainMessageInfo("Action annulée avec succès !", 1);
                }
        }
    }

    public void setData(Action act){

        if(act.isCanceled()){
            panelCard.setDisable(true);
            canceledBar.setVisible(true);
        }

        labelId1.setText(act.getIdAction()+"");
        int minute = act.getDate().getMinute();
        int hour = act.getDate().getHour();
        if(minute<10){
            labelDate1.setText(hour+"h:0"+ minute);
        }else{
            labelDate1.setText(hour+"h:"+ minute);
        }
        labelAuteur1.setText(act.getActor());
        labelObjet1.setText(act.getObjectType());
        System.out.println(act.getObjectType());
        Tooltip tip = null;
        if(act.getObjectType().equals("Apprenant")){
            Image pp = new Image("com/gesschoolapp/resources/images/mini_icon_user.png");
            iconTypeObject.setImage(pp);
            tip = new Tooltip(((Apprenant) act.getObject()).getPrenom() + " " + ((Apprenant) act.getObject()).getNom());
        }else if(act.getObjectType().equals("Note")){
            Image pp = new Image("com/gesschoolapp/resources/images/mini_icon_note.png");
            iconTypeObject.setImage(pp);
            tip = new Tooltip("Note de l'élève " + (((Note) act.getObject()).getApprenant().getPrenom() + " " + ((Note) act.getObject()).getApprenant().getNom() + " (" + ((Note) act.getObject()).getNote() + "/20)"));
        }else if(act.getObjectType().equals("Paiement")){
            Image pp = new Image("com/gesschoolapp/resources/images/mini_icon_fee.png");
            iconTypeObject.setImage(pp);
            tip = new Tooltip("Paiement associé au reçu de référence " + ((Paiement) act.getObject()).getNumeroRecu());
        }else{

        }

        Tooltip.install(iconTypeObject, tip);
        if(act.getAction() == ActionType.ADD){
            labelType1.setText("AJOUT");
            labelType1.setStyle("-fx-background-color:  #48A74C;-fx-text-fill: white;-fx-background-radius: 10px;-fx-padding: 5;");
        }else if(act.getAction() == ActionType.UPDATE){
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

