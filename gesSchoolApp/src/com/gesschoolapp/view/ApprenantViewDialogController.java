package com.gesschoolapp.view;

import com.gesschoolapp.models.student.Apprenant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ApprenantViewDialogController implements Initializable {
    private Stage dialogStage;
    private Apprenant apprenant;


    @FXML
    private Label labelClass;

    @FXML
    private Label labelDNaiss;

    @FXML
    private Label labelGenre;

    @FXML
    private Pane landscape;

    @FXML
    private Label labelMail;

    @FXML
    private Label labelMatricule;

    @FXML
    private Label labelName;

    @FXML
    private Label labelNationalite;

    @FXML
    private Circle student_pp;


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
        if(appr.getSexe().equals("M")){
            labelGenre.setText("Masculin");
            landscape.setStyle("-fx-background-color: linear-gradient(to right, #2c7aba, #5AB2D8)");
        }else if(appr.getSexe().equals("F")){
            labelGenre.setText("Féminin");
            landscape.setStyle("-fx-background-color: linear-gradient(to right, #fc67fa, #f4c4f3)");
        }
        //      Parsing birthday :
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMM yyyy" ).withLocale( java.util.Locale.FRENCH );
        String dNaiss = appr.getDateNaissance().format(formatter);

        labelDNaiss.setText(dNaiss);
        labelMail.setText(appr.getEmail());

        this.apprenant = appr;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
