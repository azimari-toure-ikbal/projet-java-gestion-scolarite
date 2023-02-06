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
//        try {
//            Timeline timeline = new Timeline();
//            KeyFrame key;
//            key = new KeyFrame(Duration.millis(50),
//                    new KeyValue (main.getPrimaryStage().opacityProperty(), 0));
//            timeline.getKeyFrames().add(key);
//            timeline.setOnFinished((ae) -> System.exit(0));
//            timeline.play();
//        } catch (Exception e) {e.getMessage();}
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=BOpvlTRfL9g"));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleLogin() {
        String login = txtUsername.getText();
        List<Utilisateur> users;
        try {
            users = userDAOImp.search(login);
            //foreach user use toString
            for (Utilisateur user : users) {
                System.out.println(user.toString());
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

//        String password = txtPassword.getText();
//
//        //if login and password are correct and not empty then use UserDaoImplDB.authenticate and get the returned user
//
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
        Timeline timeline = new Timeline();
        KeyFrame key;
        key = new KeyFrame(Duration.millis(50),
                new KeyValue (main.getPrimaryStage().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> main.getPrimaryStage().setIconified(true));
        timeline.play();
//        NOTE : Tu devrais avoir une animation spécifique à la réduction de page !

    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}