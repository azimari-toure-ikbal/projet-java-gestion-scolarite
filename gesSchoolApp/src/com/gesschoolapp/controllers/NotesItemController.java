package com.gesschoolapp.controllers;

import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.view.SecretaireUIController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotesItemController extends Application {

    private SecretaireUIController superController;

    private Note thisNote;

    @FXML
    private Button btnEditNote;

    @FXML
    private Button btnViewNote;

    @FXML
    private Label labelEtudiant;

    @FXML
    private Label labelMatricule;

    @FXML
    private Label labelNote;

    @FXML
    void actionBtnClicked(ActionEvent event) {
        System.out.println("CLICKED ON ACTIONS");
//        if(event.getSource() == btnViewApprenant){
//            superController.openStudentViewDialog(thisApprenant);
//        }
    }

    public void setData(Note note){
        thisNote = note;
        labelEtudiant.setText(note.getApprenant().getPrenom()+" "+note.getApprenant().getNom());
        labelMatricule.setText(Integer.toString(note.getApprenant().getMatricule()));
        labelNote.setText(note.getNote() + "/20");
    }

    public SecretaireUIController getSuperController() {
        return superController;
    }

    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

    }
}
