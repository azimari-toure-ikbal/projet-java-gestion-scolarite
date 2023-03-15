package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.users.concrete.Admin;
import com.gesschoolapp.models.users.concrete.Caissier;
import com.gesschoolapp.models.users.concrete.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAddDialogController implements Initializable {


    // Reference to the main com.gesschoolapp
    private Main main;

    // Reference to the current scene
    private Scene scene;

    private AdminUIController superController;

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

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField labelEmail;

    @FXML
    private TextField labelNom;

    @FXML
    private TextField labelNumero;

    @FXML
    private TextField labelPrenom;

    @FXML
    private Label messageInfo;

    @FXML
    private ChoiceBox<String> selectTypeUser;

    private String[] types = {"administrateur","secretaire","caissier"};

    private Stage dialogStage;

    private UserDAOImp usersData = new UserDAOImp();

    @FXML
    boolean onFormSubmit(ActionEvent event) {
        String nom = labelNom.getText();
        String prenom = labelPrenom.getText();
        String email = labelEmail.getText();
        String numero = labelNumero.getText();
        String habilitation = selectTypeUser.getValue();

        if(nom.length() >= 256 || prenom.length() >= 256){
            messageInfo.setVisible(true);
            messageInfo.setText("Le nom et/ou le prénom ne doivent pas faire plus de 256 caractères !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }else if(nom.length() == 0 || prenom.length() == 0 || email.length() == 0 || numero.length() == 0){
            messageInfo.setVisible(true);
            messageInfo.setText("Veuillez renseigner tous les champs !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }else if(!Toolbox.emailFormatChecker(email)){
            messageInfo.setVisible(true);
            messageInfo.setText("L'email doit être au format exemple@exemple.com !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }else if(!Toolbox.phoneNumberFormatChecker(numero)){
            messageInfo.setVisible(true);
            messageInfo.setText("Le numéro doit être au format 7* *** ** ** !");
            messageInfo.setTextFill(Color.web("#e83636"));
            return false;
        }



        Utilisateur user = null;
        if(habilitation.equals("secretaire")){
            user = new Secretaire();
        }else if(habilitation.equals("caissier")){
            user = new Caissier();
        }else if(habilitation.equals("administrateur")){
            user = new Admin();
        }

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setNumero(numero);
        user.setEmail(email);

        try {

            dialogStage.close();
            Utilisateur newUser = usersData.create(user,superController.getCurrentUser().getFullName());
            List<Utilisateur> list = new ArrayList<>(superController.getUsersList());
            list.add(newUser);
            superController.setUsersList(list);
        } catch (DAOException e) {
            superController.setMainMessageInfo("Email n'existe pas");
            return false;
        }

//        System.out.println("Utilisateur ajouté avec succès !");
        superController.setMainMessageInfo("Utilisateur ajouté avec succès !");
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectTypeUser.getItems().addAll(types);
        messageInfo.setVisible(false);
    }
    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
        selectTypeUser.setValue(superController.getSelectedUserTypeStringified());
    }



}
