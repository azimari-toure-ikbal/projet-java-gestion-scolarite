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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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


    ScolariteUIController superController;
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
    private Label labelScolarite;


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

    private String month;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr) {
        labelApprenant.setText(appr.getPrenom() + " " +  appr.getNom());
        labelClasse.setText(appr.getClasse());
        labelAnneeScolaire.setText(superController.getSelectedClass().getAnnee());
        labelDPaiement.setValue(LocalDate.now());




        this.apprenant = appr;

        Echeance toPay = superController.getSelectedClass().getEcheancier().get(apprenant.getEtatPaiement());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd MMMM yyyy" ).withLocale( java.util.Locale.FRENCH );
        month = toPay.getDate().format(formatter).split(" ")[1];

        labelScolarite.setText("Du mois \"" + month + "\"");

        if(apprenant.getEtatPaiement() == 0){
            selectRubrique.getItems().removeIf(rubr -> !rubr.equals("inscription"));
            selectRubrique.setValue(selectRubrique.getItems().get(0));
        }

        if(apprenant.getEtatPaiement() >= 1){
            selectRubrique.getItems().remove("inscription");
            selectRubrique.setValue(selectRubrique.getItems().get(0));
        }

        if(apprenant.getEtatPaiement() >= 9){
            selectRubrique.getItems().removeAll();
            selectRubrique.setValue(selectRubrique.getItems().get(0));
        }


    }


    @FXML
    void onSubmit(ActionEvent event) {
        PaiementDAOImp pDAO = new PaiementDAOImp();
        Paiement p = new Paiement();
        p.setApprenant(apprenant);
        p.setCaissier(superController.getCurrentUser().getPrenom() + " " +superController.getCurrentUser().getNom());
        p.setClasse(superController.getSelectedClass().getIntitule());
        p.setDate(LocalDate.now());
        p.setRubrique(selectRubrique.getValue());
        p.setMontant(Double.parseDouble(labelMontant.getText().split(" ")[0]));
        p.setObservation(labelObservation.getText());
        try {
            Paiement toGenerate = pDAO.create(p, superController.getCurrentUser().getFullName());
            try {
                PDFGenerator.recuGenerator(toGenerate);
            } catch (PDFException e) {
                throw new RuntimeException(e);
            }

            dialogStage.close();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        Note newNote = new Note();
        newNote.setApprenant(apprenant);

        newNote.setNote(0);
        List<Module> modules = superController.getSelectedClass().getModules();
        NoteDAOImp notesData = new NoteDAOImp();

        if(apprenant.getEtatPaiement() == 0){
            for(Module module : modules){
                List<Note> notesList = new ArrayList<>(module.getNotes());
                newNote.setModule(module.getIntitule());
    //            Note
                notesList.add(newNote);
                module.setNotes(notesList);
            }
        }

        if(selectRubrique.getValue() != "tenue"){
            apprenant.setEtatPaiement(apprenant.getEtatPaiement()+1);
        }
        superController.setMainMessageInfo("Paiement renseigné avec succès ! (VOIR RECU)");

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



    public void setSuperController(ScolariteUIController superController) {
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
                    if(newValue == "inscription"){
                        labelScolarite.setVisible(true);
                        labelScolarite.setText("Scolarité du mois \"" + month + "\", droit d'inscription et autres... ");
                    }else if(newValue == "scolarite"){
                        labelScolarite.setVisible(true);
                        labelScolarite.setText("Du mois \"" + month + "\"");
                    }else{
                        labelScolarite.setVisible(false);
                    }
                } );

    }

}

