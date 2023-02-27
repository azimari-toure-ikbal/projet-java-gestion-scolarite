package com.gesschoolapp.view.admin;

import com.gesschoolapp.models.student.Apprenant;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class CertificatDialogViewController {

    private Stage dialogStage;
    private Apprenant apprenant;

    private Main main;

    // Reference to the current scene
    private Scene scene;



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


    AdminUIController superController;

    @FXML
    private ImageView bulletinImg;

    @FXML
    private Label fileNameLabel;

    @FXML
    private Label labelPage;


    @FXML
    private Button btnLeft;

    @FXML
    private Button btnRight;

    private String path;



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr,String certifPath) {

        this.apprenant = appr;
        this.path = certifPath;
        File file = new File(certifPath);
        Image bt = new Image(file.toURI().toString());
        bulletinImg.setImage(bt);
        fileNameLabel.setText(certifPath.split("/")[3].replace(".png",".pdf"));

    }

    @FXML
    void onSubmit(ActionEvent event) {

    }

    @FXML
    void openFeesDialogView(ActionEvent event) {

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

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }


    public void setSuperController(AdminUIController superController) {
        this.superController = superController;

    }

}
