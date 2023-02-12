package com.gesschoolapp.view;

import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ModuleItemController implements Initializable {

    private SecretaireUIController superController;

    private List<String> moduleThemes = new ArrayList<>();
    {
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #e12538 0.0%, #d0747d 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #7326e0 0.0%, #9782d7 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #547558 0.0%, #83d68c 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #bf7225 0.0%, #e1852a 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #e02683 0.0%, #eeacbf 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #e02683 0.0%, #eeacbf 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #28bf4e 0.0%, #a4ffbf 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #b09509 0.0%, #f9ffa6 100.0%)");
        moduleThemes.add("-fx-fill: linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #ad4009 0.0%, #da7645 100.0%)");

    }
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
    void onClickModule(MouseEvent event) {
        System.out.println("CLICKED ON MODULE : " + thisModule.getIntitule());
        this.setAsSelected();
    }

    public void setAsSelected(){
        superController.setSelectedModule(thisModule);
        ((Pane) moduleCard).getChildren().get(2).setVisible(true);

    }

    public SecretaireUIController getSuperController() {
        return superController;
    }

    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }

    public String getRandomTheme(){
        Random rand = new Random();
        return moduleThemes.get(rand.nextInt(moduleThemes.size()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
