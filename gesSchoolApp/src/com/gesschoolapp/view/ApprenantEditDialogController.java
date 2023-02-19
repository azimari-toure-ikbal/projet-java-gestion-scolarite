package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.view.util.Genre;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApprenantEditDialogController extends Application implements Initializable {

    private Classe currentClass;
    private Apprenant selectedStudent;


    // Reference to the main com.gesschoolapp
    private Main main;

    // Reference to the current scene
    private Scene scene;

    private SecretaireUIController superController;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Scene getScene() {
        return scene;
    }
    public void setSelectedStudent(Apprenant selectedStudent) {
        this.selectedStudent = selectedStudent;
        labelNom.setText(selectedStudent.getNom());
        labelPrenom.setText(selectedStudent.getPrenom());
        labelNationalite.setText(selectedStudent.getNationalite());
        if(selectedStudent.getSexe().equals("M")){
            selectGenre.setValue(Genre.MASCULIN);
        }else{
            selectGenre.setValue(Genre.FEMININ);
        }

        labelDNaiss.setValue(selectedStudent.getDateNaissance());
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    ApprenantDAOImp apprenantsData = new ApprenantDAOImp();

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField labelClass;

    @FXML
    private DatePicker labelDNaiss;

    @FXML
    private TextField labelName;

    @FXML
    private TextField labelNationalite;

    @FXML
    private TextField labelNom;

    @FXML
    private TextField labelPrenom;

    @FXML
    private Pane landscape;

    @FXML
    private Label messageInfo;

    @FXML
    private ChoiceBox<Genre> selectGenre;

    private Genre[] genres = {Genre.MASCULIN,Genre.FEMININ,Genre.AUTRES};

    private Stage dialogStage;

    @FXML
    boolean onFormSubmit(ActionEvent event) {
        String nom = labelNom.getText();
        String prenom = labelPrenom.getText();
        String nationalite = labelNationalite.getText();
        Genre genre = selectGenre.getValue();
        LocalDate dNaiss = null;
        try{
            dNaiss = labelDNaiss.getValue();

        }catch (DateTimeParseException e){
            messageInfo.setText("Veuillez entrer une date valide !");
            return false;
        }


        if(nom.length() >= 256 || prenom.length() >= 256){
            messageInfo.setVisible(true);
            messageInfo.setText("Le nom et/ou le prénom ne doivent pas faire plus de 256 caractères !");
            return false;
        }else if(nom.length() == 0 || prenom.length() == 0 || nationalite.length() == 0 || Objects.isNull(dNaiss)){
            messageInfo.setVisible(true);
            messageInfo.setText("Veuillez renseigner tous les champs !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }else if(nationalite.length() >= 130){
            messageInfo.setVisible(true);
            messageInfo.setText("Nationalité invalide !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }

        Apprenant apprenant = new Apprenant();
        apprenant.setNom(nom);
        apprenant.setPrenom(prenom);
        apprenant.setNationalite(nationalite);
        if(genre == Genre.MASCULIN){
            apprenant.setSexe("M");
        }else if(genre == Genre.FEMININ){
            apprenant.setSexe("F");
        }

        apprenant.setDateNaissance(dNaiss);
        apprenant.setClasse(currentClass.getIntitule());
        apprenant.setIdApprenant(selectedStudent.getIdApprenant());
        apprenant.setEtatPaiement(selectedStudent.getEtatPaiement());
        apprenant.setIdApprenant(selectedStudent.getIdApprenant());
        apprenant.setMatricule(selectedStudent.getMatricule());

        try {
            dialogStage.close();
            apprenantsData.update(apprenant);
            List<Apprenant> list = new ArrayList<>(superController.getSelectedClass().getApprenants());
            list.set(list.indexOf(selectedStudent),apprenant);
            superController.getSelectedClass().setApprenants(list);
            Note newNote = new Note();
            if(apprenant.getEtatPaiement() != 0){

            newNote.setApprenant(apprenant);
            List<Module> modules = superController.getSelectedClass().getModules();
            NoteDAOImp notesData = new NoteDAOImp();

            for(Module module : modules){
                List<Note> notesList = new ArrayList<>(module.getNotes());
                newNote.setModule(module.getIntitule());
                Note studentNote = notesList.stream().filter(note -> note.getApprenant().getIdApprenant() == apprenant.getIdApprenant()).toList().get(0);
                newNote.setNote(studentNote.getNote());
//                newNote.setId();
                notesList.set(notesList.indexOf(studentNote), newNote);
                module.setNotes(notesList);
            }
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        superController.setMainMessageInfo("Élève modifié avec succès !");
        return true;
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
    public void start(Stage primaryStage) {

    }

    public void setCurrentClass(Classe currentClass) {
        labelClass.setText(currentClass.getIntitule());
        this.currentClass = currentClass;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectGenre.getItems().addAll(genres);
        System.out.println(messageInfo);
        messageInfo.setVisible(false);
//        System.out.println(labelName);
//        System.out.println(selectedStudent);
        selectGenre.setValue(Genre.MASCULIN);

    }
    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }
}
