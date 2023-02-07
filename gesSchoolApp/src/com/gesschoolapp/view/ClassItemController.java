package com.gesschoolapp.view;

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

    private SecretaireUIController superController;

    public SecretaireUIController getSuperController() {
        return superController;
    }

    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Classe classe){
        labelIntitule.setText(classe.getIntitule());
        labelFormation.setText(classe.getFormation());
        labelAnnee.setText(classe.getAnnee());
    }

    @FXML
    void onClickClass(MouseEvent event) {

        System.out.println("CLICKED");
        superController.setSelectedClass("LPTI1");
    }
}
