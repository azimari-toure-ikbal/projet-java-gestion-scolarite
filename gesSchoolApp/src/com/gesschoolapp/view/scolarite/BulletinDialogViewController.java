package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.docmaker.PDFGenerator;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Echeance;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BulletinDialogViewController {

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


    ScolariteUIController superController;

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

    private String[] paths;



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr,String[] bulletinsPath) {

        this.apprenant = appr;
        this.paths = bulletinsPath;
        File file = new File(bulletinsPath[0]);
        Image bt = new Image(file.toURI().toString());
        bulletinImg.setImage(bt);
        fileNameLabel.setText(bulletinsPath[0].split("/")[3]);

        if(bulletinsPath[1] != null){
            labelPage.setText("1/2");
            btnLeft.setVisible(true);
            btnRight.setVisible(true);
        }


    }

    @FXML
    void handlePagination(MouseEvent event) {
            if(event.getSource() == btnLeft){
                File file = new File(paths[0]);
                Image bt = new Image(file.toURI().toString());
                bulletinImg.setImage(bt);
                labelPage.setText("1/2");
            }else{
                File file = new File(paths[1]);
                Image bt = new Image(file.toURI().toString());
                bulletinImg.setImage(bt);
                labelPage.setText("2/2");

            }
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


    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;

    }

}
