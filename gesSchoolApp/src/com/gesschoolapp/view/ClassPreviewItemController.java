package com.gesschoolapp.view;

import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClassPreviewItemController implements Initializable {
    @FXML
    private ImageView class_preview;

    @FXML
    private Label labelAnnee;

    @FXML
    private Label labelFormation;

    @FXML
    private Label labelIntitule;



    @FXML
    private Label nbStudents;


    @FXML
    private Button btnClassesEleves;

    @FXML
    private Button btnClassesNotes;

    private SecretaireUIController superController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.
        class_preview.setImage(new Image("com/gesschoolapp/resources/images/plc.png"));
    }

    public void setData(Classe classe){
        nbStudents.setText(classe.getApprenants().size() + " élèves, " + classe.getModules().size() + " modules");
        labelIntitule.setText(classe.getIntitule());
        labelFormation.setText(classe.getFormation());
        labelAnnee.setText(classe.getAnnee());
    }

    public void setSuperController(SecretaireUIController controller){
        this.superController = controller;
    }

    @FXML
    void handleNavigation(ActionEvent event) {
        String newRouteLink = null;
        if(event.getSource() == btnClassesEleves){
            newRouteLink = "/"+superController.getSelectedClass().getIntitule()+"/eleves";
        }else if(event.getSource() == btnClassesNotes){
            newRouteLink = "/"+superController.getSelectedClass().getIntitule()+"/notes";
        }
            superController.setCurrentRoute();
            superController.setCurrentRouteLink(newRouteLink);
    }

}
