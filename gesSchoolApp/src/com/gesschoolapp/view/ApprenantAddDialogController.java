package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.view.util.Genre;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApprenantAddDialogController extends Application implements Initializable {

    private Classe currentClass;

    private SecretaireUIController superController;


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
            messageInfo.setText("Le nom et/ou le prénom ne doivent pas faire plus de 256 caractères !");
            return false;
        }

        if(nom.length() == 0 || prenom.length() == 0 || nationalite.length() == 0 || Objects.isNull(dNaiss)){
            messageInfo.setText("Veuillez renseigner tous les champs !");
            return false;
        }

        if(nationalite.length() >= 130){
            messageInfo.setText("Nationalité invalide !");
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
        apprenant.setEmail("random@mail.com");
        apprenant.setClasse(currentClass.getIntitule());

        try {
            apprenantsData.create(apprenant);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        messageInfo.setText("");
//        superController.resetVue();
        dialogStage.close();
        superController.setMainMessageInfo("Élève ajouté avec succès !");
        return true;
    }


    public static void main(String[] args) {
        launch(args);
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
        selectGenre.setValue(Genre.MASCULIN);
    }
    public void setSuperController(SecretaireUIController superController) {
        this.superController = superController;
    }
}
