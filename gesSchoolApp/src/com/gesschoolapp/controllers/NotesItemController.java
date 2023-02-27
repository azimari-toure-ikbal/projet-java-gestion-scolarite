package com.gesschoolapp.controllers;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.utils.Toolbox;
import com.gesschoolapp.view.scolarite.ScolariteUIController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NotesItemController extends Application {

    NoteDAOImp notesData = new NoteDAOImp();

    private ScolariteUIController superController;

    private Note thisNote;

    @FXML
    private TextField editNoteTF;


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
        editNoteTF.setVisible(!editNoteTF.isVisible());
        editNoteTF.requestFocus();
    }

    public void setData(Note note){
        thisNote = note;
        labelEtudiant.setText(note.getApprenant().getPrenom()+" "+note.getApprenant().getNom());
        labelMatricule.setText(Integer.toString(note.getApprenant().getMatricule()));
        labelNote.setText(note.getNote() + "/20");
        editNoteTF.setText(note.getNote() + "/20");
    }

    @FXML
    boolean handleNoteEdit(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            String newNote = editNoteTF.getText();

            if(!Toolbox.checkNoteFormat(newNote)){
                superController.setMainMessageInfo("Format incorrect !",0);
                return false;
            }

            float noteValue = Float.parseFloat(newNote.split("/")[0]);

            if(noteValue > 20 || noteValue < 0){
                superController.setMainMessageInfo("La note doit être comprise entre 0 et 20 !",0);
                return false;
            }

            thisNote.setNote(noteValue);
            try {
                notesData.update(thisNote,superController.getSelectedSemestreIndex(),superController.getCurrentUser().getFullName());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            superController.setMainMessageInfo("Note modifiée avec succès !",1);
            editNoteTF.setVisible(false);

        }
        return true;
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

    }
}
