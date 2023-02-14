package com.gesschoolapp.view;

import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.view.SecretaireUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        System.out.println("CLICKED MF !");
        if(event.getSource() == btnViewApprenant){
            System.out.println("CLICKED ON VIEW STUDENT");
            superController.openStudentViewDialog(thisApprenant);
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
