package com.gesschoolapp.view;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javax.swing.*;

public class LoginUIController implements Initializable  {


    @FXML
    private ImageView btn_close;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label messageInfo;
    @FXML
    private ImageView minIcon;

    // Reference to the main com.gesschoolapp
    private Main main;

    // Reference to the current scene
    private Scene scene;

    private UserDAOImp userDAOImp = new UserDAOImp();

//    private UserDaoImplDB users = new UserDaoImplDB();

    /**
     * Is called by the main com.gesschoolapp to give a reference back to itself.
     *
     * @param main mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    /**
     * Is called by the main com.gesschoolapp to give a reference of its current Scene Root to itself.
     *
     * @param sc  current Scene Parent root
     */
    public void setCurrentScene(Scene sc) {
        this.scene = sc;
    }


    @FXML
    private void handleExit() {
        try {
            Timeline timeline = new Timeline();
            KeyFrame key;
            key = new KeyFrame(Duration.millis(50),
                    new KeyValue (main.getPrimaryStage().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((ae) -> System.exit(0));
            timeline.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    private void handleLogin() {
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            messageInfo.setText("Veuillez remplir tous les champs");
        } else {
            try {
                Utilisateur user = userDAOImp.authenticate(email, password);
                if (user != null) {
                    if (user instanceof Secretaire) {
                        main.displaySecretaireLayout();
                    }
                } else {
                    messageInfo.setText("email ou mot de passe incorrect");
                }
            } catch (DAOException e) {
                // Afficher un message d'erreur
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "Une erreur est survenue");
            }
        }
//        if (!Objects.equals(login, "") && !Objects.equals(password, "")){
//            try {
//                Utilisateur user = userDAOImp.authenticate(login, password);
//                if(user instanceof Secretaire)
//                    main.displaySecretaireLayout();
//            } catch (DAOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }


//    private boolean isInputValid(String login, String password) {
//
//
//        messageInfo.setTextFill(Color.TOMATO);
//
//        if( login == null || password == null || txtUsername.getText().isEmpty() || password.isEmpty()) {
//            messageInfo.setText("Missing informations !");
//            return false;
//        }
//
//        if(login.length() <=3 || password.length() <=3) {
//            messageInfo.setText("Fields must be at least 3 characters or more !");
//            return false;
//        }
//
//
//        return true;
//    }

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                main.getPrimaryStage().setX(e1.getScreenX() - e.getSceneX());
                main.getPrimaryStage().setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }

    @FXML
    private void onMinimize(){
        main.getPrimaryStage().setIconified(true);
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}