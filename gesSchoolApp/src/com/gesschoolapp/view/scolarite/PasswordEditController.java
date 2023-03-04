package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.users.concrete.Admin;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PasswordEditController {

    private Utilisateur user;

    private ScolariteUIController superController;

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


        String nPassword = newPassword.getText();
        String nPasswordConfirm = newPasswordConfirm.getText();
        String oPassword = oldPassword.getText();


        messageInfo.setVisible(true);

        user = superController.getCurrentUser();

        String[] pwds = {oPassword,nPasswordConfirm,nPassword};

        messageInfo.setVisible(true);

        for(int i=0;i<pwds.length;i++){
            if(pwds[i].isEmpty()){
                messageInfo.setText("Veillez renseigner tous les champs.");
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

        UserDAOImp userDAO = new UserDAOImp();
        try {
            userDAO.update(user,superController.getCurrentUser().getFullName());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        dialogStage.close();
        superController.setMainMessageInfo("Votre mot de passe a été modifié avec succès");
        superController.setCurrentUser(user);
        return true;
    }


    public void setData(Utilisateur utilisateur){
        this.user = utilisateur;
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
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
