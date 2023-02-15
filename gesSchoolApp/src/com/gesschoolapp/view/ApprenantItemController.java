package com.gesschoolapp.view;

import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.view.SecretaireUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ApprenantItemController {

    private SecretaireUIController superController;

    private Apprenant thisApprenant;

    @FXML
    private Label labelGenre;

    @FXML
    private Label labelId;

    @FXML
    private Label labelNom;

    @FXML
    private Label labeldNaiss;

    @FXML
    private Button btnDeleteApprenant;

    @FXML
    private Button btnEditApprenant;

    @FXML
    private Button btnViewApprenant;

    @FXML
    void actionBtnClicked(ActionEvent event) {
        if(event.getSource() == btnViewApprenant){
            superController.openStudentViewDialog(thisApprenant);
        }
    }

    @FXML
    void deleteBtnClicked(MouseEvent event) {
        System.out.println("clc ce fdp");
        //ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer l'élève" + thisApprenant.getNom() + " " + thisApprenant.getPrenom() + " de matricule " + thisApprenant.getMatricule());
        alert.setContentText("Voulez-vous continuer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }
    }




    public void setData(Apprenant apprenant){
        thisApprenant = apprenant;
        labelId.setText(Integer.toString(apprenant.getIdApprenant()));
        labelNom.setText(apprenant.getPrenom() + " " + apprenant.getNom());
//      Parsing birthday :
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        String dNaiss = apprenant.getDateNaissance().format(formatters);

        labeldNaiss.setText(dNaiss);
        labelGenre.setText(apprenant.getSexe());
    }

    public SecretaireUIController getSuperController() {
        return superController;
    }

    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }
}
