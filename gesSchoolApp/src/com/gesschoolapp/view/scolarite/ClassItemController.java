package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.models.classroom.Classe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassItemController implements Initializable {

    private ScolariteUIController superController;


    @FXML
    private ImageView classImg;

    @FXML
    private Label labelAnnee;

    @FXML
    private Label labelFormation;

    @FXML
    private Label labelIntitule;

    @FXML
    private HBox classCard;

    public void setThisClass(Classe thisClass) {
        this.thisClass = thisClass;
    }

    private Classe thisClass;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Classe classe){
        thisClass = classe;
        labelIntitule.setText(classe.getIntitule());
        labelFormation.setText(classe.getFormation());
        labelAnnee.setText(classe.getAnnee());
    }

    @FXML
    void onClickClass(MouseEvent event) {
        superController.setSelectedClass(thisClass);
        classCard.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;
    }

}
