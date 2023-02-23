package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.utils.Toolbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassItemController {

    private Classe classe;

    private AdminUIController superController;

    @FXML
    private Label messageInfo;

    @FXML
    private Button btnGesModules;

    @FXML
    private Button btnViewClass;

    @FXML
    private ImageView classImg;

    @FXML
    private Label labelFormation;

    @FXML
    private Label labelIntitule;

    @FXML
    private Label labelNbEleves;

    @FXML
    private Label labelNbModules;

    @FXML
    void actionBtnClicked(ActionEvent event) {
        if(event.getSource() == btnGesModules){
            superController.openGesModulesView(classe);
        }
    }

    public void setData(Classe selectedClass){
        this.classe = selectedClass;
//        System.out.println(classe);
        labelIntitule.setText(classe.getIntitule());
        labelFormation.setText(classe.getFormation());
        labelNbEleves.setText(classe.getApprenants().size()+"");
        labelNbModules.setText(classe.getModules().size()+"");
    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }



}
