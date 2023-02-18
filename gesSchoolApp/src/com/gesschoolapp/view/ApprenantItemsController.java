package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.view.SecretaireUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ApprenantItemsController implements Initializable {

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
    private Button etatPayement;

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
    void editBtnClicked(ActionEvent event) {
        superController.openStudentEditDialog(thisApprenant);
    }

    @FXML
    void deleteBtnClicked(ActionEvent event) {
        System.out.println("clc ce fdp");
        //ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer l'élève " + thisApprenant.getPrenom() + " " + thisApprenant.getNom() + " de matricule " + thisApprenant.getMatricule());
        alert.setContentText("Voulez-vous continuer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ApprenantDAOImp apprData = new ApprenantDAOImp();

            try {
                apprData.delete(thisApprenant.getIdApprenant());
                List<Apprenant> list = new ArrayList<>(superController.getSelectedClass().getApprenants());
                list.removeIf(appr -> appr.getIdApprenant() == thisApprenant.getIdApprenant());
                superController.getSelectedClass().setApprenants(list);

                for(Module module : superController.getSelectedClass().getModules()){

                    List<Note> notesList = new ArrayList<>(module.getNotes());
                    notesList.removeIf(note -> note.getApprenant().getIdApprenant() == thisApprenant.getIdApprenant());
                    module.setNotes(notesList);
                }

            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public void setData(Apprenant apprenant){
        thisApprenant = apprenant;
        labelId.setText(Integer.toString(apprenant.getMatricule()));
        labelNom.setText(apprenant.getPrenom() + " " + apprenant.getNom());
//      Parsing birthday :
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        String dNaiss = apprenant.getDateNaissance().format(formatters);
        if (apprenant.getSexe().equals("F")) {
            etatPayement.setText("Impayé");
            etatPayement.setStyle("-fx-background-color: #CE4F4B;");
            System.out.println(etatPayement);
        }


        labeldNaiss.setText(dNaiss);
        labelGenre.setText(apprenant.getSexe());

        System.out.println(superController.isCaissierSession());
        if(superController.isCaissierSession()){
            etatPayement.setVisible(true);
        }
    }

    public SecretaireUIController getSuperController() {
        return superController;
    }

    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
