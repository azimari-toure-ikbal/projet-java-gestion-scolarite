package com.gesschoolapp.view;

import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.view.util.Genre;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FeesDialogController  implements Initializable {
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


    SecretaireUIController superController;
    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField labelAnneeScolaire;

    @FXML
    private TextField labelApprenant;

    @FXML
    private TextField labelClasse;

    @FXML
    private DatePicker labelDPaiement;

    @FXML
    private TextField labelMontant;

    @FXML
    private TextArea labelObservation;

    @FXML
    private Pane landscape;

    @FXML
    private ChoiceBox<String> selectRubrique;

    private Rubrique[] rubriqueList;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr) {
        labelApprenant.setText(appr.getPrenom() + " " +  appr.getNom());
        labelClasse.setText(appr.getClasse());
        labelAnneeScolaire.setText(superController.getSelectedClass().getAnnee());
        labelDPaiement.setValue(LocalDate.now());

        this.apprenant = appr;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }



    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
        rubriqueList = new ArrayList<>(superController.getSelectedClass().getRubriques()).toArray(new Rubrique[superController.getSelectedClass().getRubriques().size()]);
        for(Rubrique rubr : rubriqueList){
            selectRubrique.getItems().add(rubr.getIntitule());
        }
//        messageInfo.setVisible(false);
        selectRubrique.setValue(rubriqueList[0].getIntitule());
        labelMontant.setText(rubriqueList[0].getMontant() + " FCFA");

        selectRubrique.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    Rubrique item = Arrays.stream(rubriqueList).filter(rubr -> rubr.getIntitule() == newValue).toList().get(0);
                    labelMontant.setText(item.getMontant() + " FCFA");
                } );

    }

}

