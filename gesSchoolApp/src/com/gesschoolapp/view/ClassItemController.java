package com.gesschoolapp.view;

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

    public void setData(String i, String f, String a){
        labelIntitule.setText(i);
        labelFormation.setText(f);
        labelAnnee.setText(a);
    }

    @FXML
    void onClickClass(MouseEvent event) {

        System.out.println("CLICKED");
        superController.setSelectedClass("LPTI1");
    }
}
