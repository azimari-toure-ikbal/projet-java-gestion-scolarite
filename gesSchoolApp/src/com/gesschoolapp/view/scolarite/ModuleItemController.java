package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.models.matieres.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModuleItemController implements Initializable {

    private ScolariteUIController superController;


    @FXML
    private Label moduleName;

    @FXML
    private Pane moduleCard;

    private Module thisModule;

    @FXML
    private ImageView selectedMark;

    public void setData(Module module){
        thisModule = module;
        moduleName.setText(module.getIntitule());
    }

    @FXML
    void onClickModule(ActionEvent event) {
        this.setAsSelected();
    }

    public void setAsSelected(){
        superController.setSelectedModule(thisModule);
        Image opened = new Image("com/gesschoolapp/resources/images/opened_folder.png");
        ((ImageView) ((Pane) moduleCard).getChildren().get(0)).setImage(opened);
        ((Label) ((Pane) moduleCard).getChildren().get(1)).setStyle("-fx-font-size:7px;-fx-font-weight:bold;-fx-font-style:italic;-fx-padding: 8px 0 0 0;");
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
