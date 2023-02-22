package com.gesschoolapp.view.admin;

import com.gesschoolapp.models.users.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.sql.SQLOutput;

public class UserItemController {

    private Utilisateur user;

    private AdminUIController superController;

    @FXML
    private Button btnDeleteUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private HBox panelActions;


    @FXML
    private Button btnViewChartUser;

    @FXML
    private Button btnViewUser;

    @FXML
    private HBox cardHbox;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelNum;

    @FXML
    private Label labelPassword;

    @FXML
    private Circle pp_placeholder;

    @FXML
    void actionBtnClicked(ActionEvent event) {

    }

    public void setData(Utilisateur utilisateur){
        labelNom.setText(utilisateur.getFullName());
        labelLogin.setText(utilisateur.getEmail());
        labelPassword.setText(utilisateur.getPassword());
        labelNum.setText(utilisateur.getNumero());
        this.user = utilisateur;
        // --- Init components :
        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));

        if(!utilisateur.getType().equals("caissier")){
            panelActions.getChildren().remove(panelActions.lookup(".caissier_item"));
        }
    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

    @FXML
    void handleOpenUserView(ActionEvent event) {
        superController.openUserViewDialog(user);
    }

}
