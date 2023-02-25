package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileEditController {

    private Utilisateur user;

    private AdminUIController superController;

    private Scene scene;

    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }

    private Stage dialogStage;


    @FXML
    private Button btnAjouter;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPasswordConfirm;

    @FXML
    private PasswordField oldPassword;


    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField labelEmail;

    @FXML
    private TextField labelNom;

    @FXML
    private TextField labelNumero;

    @FXML
    private TextField labelPrenom;

    @FXML
    private Label messageInfo;



    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    @FXML
    void onFormSubmit(ActionEvent event) {
        String nom = labelNom.getText();
        String prenom = labelPrenom.getText();
        String email = labelEmail.getText();
        String numéro = labelNumero.getText();
//        String newPasswordConfirm = newPasswordConfirm.getText();
//        String oldPassword = oldPassword.getText();

    }


    public void setData(Utilisateur utilisateur){
        labelNom.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
        labelPrenom.setText(utilisateur.getPrenom());
        labelNumero.setText(utilisateur.getNumero());
        labelEmail.setText(utilisateur.getEmail());
        this.user = utilisateur;
    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

    @FXML
    void onClose(ActionEvent event) {
        try {
            Timeline timeline = new Timeline();
            KeyFrame key;
            key = new KeyFrame(Duration.millis(50),
                    new KeyValue(dialogStage.opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((ae) -> dialogStage.close());
            timeline.play();
        } catch (Exception e) {e.printStackTrace();}


    }

}
