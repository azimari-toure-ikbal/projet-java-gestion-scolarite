package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.models.classroom.Classe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClassPreviewItemController implements Initializable {
    @FXML
    private ImageView class_preview;

    @FXML
    private Label labelAnnee;

    @FXML
    private Label labelEcheance;


    @FXML
    private ImageView echeanceIcon;


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

    private ScolariteUIController superController;


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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMMM yyyy" ).withLocale( java.util.Locale.FRENCH );
        String echeance = classe.getCurrentEcheance().getDate().format(formatter);
        labelEcheance.setText(echeance);

    }

    public void setSuperController(ScolariteUIController controller){
        this.superController = controller;
    }

    public void hideEcheancier(){
        labelEcheance.setVisible(false);
        echeanceIcon.setVisible(false);
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
