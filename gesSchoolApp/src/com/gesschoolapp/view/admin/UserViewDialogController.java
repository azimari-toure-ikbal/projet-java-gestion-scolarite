package com.gesschoolapp.view.admin;

import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.view.scolarite.ScolariteUIController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserViewDialogController {

    private Stage dialogStage;

    private Utilisateur selectedUser;


    private Main main;

    // Reference to the current scene
    private Scene scene;

    AdminUIController superController;

    @FXML
    private Button btnAnnuler;

    @FXML
    private GridPane infosGrid;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelName;

    @FXML
    private Label labelNationalite;

    @FXML
    private Label labelTelephone;

    @FXML
    private Label labelType;

    @FXML
    private Pane landscape;

    @FXML
    private Circle user_pp;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setSuperController(AdminUIController superController) {this.superController = superController;}

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
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

    public void setData(Utilisateur user){
        labelName.setText(user.getFullName());
        labelType.setText(user.getType().toUpperCase());
        labelLogin.setText(user.getEmail());
        labelTelephone.setText(user.getNumero());

        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        user_pp.setFill(new ImagePattern(pp));

    }


}
