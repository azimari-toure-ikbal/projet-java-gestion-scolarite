package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.users.Admin;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.tools.Tool;
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
    boolean onFormSubmit(ActionEvent event) {

        System.out.println("HEY !");

        String nom = labelNom.getText();
        String prenom = labelPrenom.getText();
        String email = labelEmail.getText();
        String numero = labelNumero.getText();
        String nPassword = newPassword.getText();
        String nPasswordConfirm = newPasswordConfirm.getText();
        String oPassword = oldPassword.getText();

        String[] champs = {nom,prenom,email,numero};

        messageInfo.setVisible(true);

        for(int i=0;i<champs.length;i++){
            if(champs[i].isEmpty()){
                messageInfo.setText("Veuillez remplir tous les champs obligatoire");
                messageInfo.setTextFill(Color.web("#E74C3C"));
                return false;
            }
        }

        if(nom.length() >= 256 || prenom.length() >= 256){
            messageInfo.setVisible(true);
            messageInfo.setText("Le nom et/ou le prénom ne doivent pas faire plus de 256 caractères !");
            messageInfo.setTextFill(Color.web("#E74C3C"));
            return false;
        }

        if(!Toolbox.emailFormatChecker(email)){
            messageInfo.setVisible(true);
            messageInfo.setText("L'email doit être au format exemple@exemple.com !");
            messageInfo.setTextFill(Color.web("#E74C3C"));
            return false;
        }

        if(!Toolbox.phoneNumberFormatChecker(numero)){
            messageInfo.setVisible(true);
            messageInfo.setText("Le numéro doit être au format 7******** !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }

        user = superController.getCurrentUser();

        if(!oPassword.isEmpty() || !nPassword.isEmpty() || !nPasswordConfirm.isEmpty()){
            String[] pwds = {oPassword,nPasswordConfirm,nPassword};

            messageInfo.setVisible(true);

            for(int i=0;i<pwds.length;i++){
                if(pwds[i].isEmpty()){
                    messageInfo.setText("Pour changer votre mot de passe, veuillez remplir tous les champs");
                    messageInfo.setTextFill(Color.web("#E74C3C"));
                    return false;
                }
            }


            if(!Toolbox.verifyPassword(oPassword,superController.getCurrentUser().getPassword())){
                messageInfo.setVisible(true);
                messageInfo.setText("L'ancien mot de passe est incorrect.");
                messageInfo.setTextFill(Color.web("#e83636"));
                return false;
            }

            if(!nPassword.equals(nPasswordConfirm)){
                messageInfo.setVisible(true);
                messageInfo.setText("Les mots de passe doivent être identique.");
                messageInfo.setTextFill(Color.web("#e83636"));
                return false;
            }

            user.setPassword(nPassword);

        }

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setNumero(numero);

        UserDAOImp userDAO = new UserDAOImp();
        try {
            userDAO.update(user,superController.getCurrentUser().getFullName());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        dialogStage.close();
        superController.setMainMessageInfo("Votre profil a été modifié avec succès");
        superController.setCurrentUser((Admin) user);
        return true;
    }


    public void setData(Utilisateur utilisateur){
        labelNom.setText(utilisateur.getNom());
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
