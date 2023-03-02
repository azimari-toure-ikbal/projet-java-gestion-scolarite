package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.runtime.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ApprenantViewDialogController implements Initializable {
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
    private Label labelClass;

    @FXML
    private Label labelDNaiss;

    @FXML
    private Label labelGenre;

    @FXML
    private Pane landscape;

    @FXML
    private Label labelEtatPaiement;

    @FXML
    private Label labelMatricule;

    @FXML
    private Label labelName;

    @FXML
    private Label labelNationalite;

    @FXML
    private Circle student_pp;

    @FXML
    private Button btnFees;

    @FXML
    private GridPane infosGrid;

    @FXML
    private ImageView iconCaissierItem;



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr) {

        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        student_pp.setFill(new ImagePattern(pp));
        labelName.setText(appr.getPrenom() + " " + appr.getNom());
        labelClass.setText("Élève en " + appr.getClasse());
        labelMatricule.setText(Integer.toString(appr.getMatricule()));
        labelNationalite.setText(appr.getNationalite());


        if(appr.getEtatPaiement() == 9){
            labelEtatPaiement.setText("Payé (" + superController.getSelectedClass().getEcheancier().get(appr.getEtatPaiement() - 1).getDate() + ")");
        }else{

            if(appr.getEtatPaiement() == 0){
                labelEtatPaiement.setText("Non inscrit");
            }else if(appr.getEtatPaiement() == 1){
                if(!superController.getSelectedClass().isCurrentEcheancePaid(appr)){
                    labelEtatPaiement.setText("Impayé");
                }else{
                    labelEtatPaiement.setText("Inscrit");
                }
            }else{
                if(!superController.getSelectedClass().isCurrentEcheancePaid(appr)){
                    labelEtatPaiement.setText("Impayé");
                }else{
                    labelEtatPaiement.setText("Payé (" + superController.getSelectedClass().getEcheancier().get(appr.getEtatPaiement() - 1).getDate() + ")");
                }
            }
        }

        if(appr.getSexe().equals("M")){
            labelGenre.setText("Masculin");
            landscape.setStyle("-fx-background-color: linear-gradient(to right, #2c7aba, #5AB2D8)");
        }else if(appr.getSexe().equals("F")){
            labelGenre.setText("Féminin");
            landscape.setStyle("-fx-background-color: linear-gradient(to right, #fc67fa, #f4c4f3)");
        }

        if(!superController.isCaissierSession()){
            btnFees.setVisible(false);
            iconCaissierItem.setVisible(false);
            infosGrid.getChildren().remove(infosGrid.lookup(".caissier_item"));
        }

        //      Parsing birthday :
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMM yyyy" ).withLocale( java.util.Locale.FRENCH );
        String dNaiss = appr.getDateNaissance().format(formatter);

        labelDNaiss.setText(dNaiss);

        this.apprenant = appr;

    }

    @FXML
    void openFeesDialogView(ActionEvent event) {
        Timeline timeline = new Timeline();
        KeyFrame key;
        key = new KeyFrame(Duration.millis(50),
                new KeyValue(dialogStage.opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> dialogStage.close());
        timeline.play();

        Platform.runLater(() ->{
        superController.openStudentFeesDialog(apprenant);

        });
    }

    @FXML
    void openBulletinDialogView(ActionEvent event) {
        Timeline timeline = new Timeline();
        KeyFrame key;
        key = new KeyFrame(Duration.millis(50),
                new KeyValue(dialogStage.opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> dialogStage.close());
        timeline.play();

        Platform.runLater(() ->{
            superController.openBulletinViewDialog(apprenant);

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


    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;
    }

}
