package com.gesschoolapp.view.login;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

public class LoginUIController implements Initializable  {


    @FXML
    private ImageView btn_close;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    public void setFocus(){
        this.txtPassword.requestFocus();
    }

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
        } catch (Exception e) {e.printStackTrace();}

    }



    @FXML
    void handleLogin(ActionEvent event) {
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        if (login.isEmpty() || password.isEmpty()) {
            messageInfo.setText("Veuillez remplir tous les champs");
        } else {
            try {
                Utilisateur user = userDAOImp.authenticate(login, password);
                if (user != null) {
                    if (Toolbox.verifyPassword(password, user.getPassword())) {
                        Node node = (Node) event.getSource();
                        Stage stg = (Stage) node.getScene().getWindow();
                        stg.close();
                        if (user instanceof Secretaire) {
                            main.displaySecretaireUI(stg, (Secretaire) user, main);
                        } else if (user instanceof Caissier) {
                            main.displayCaissierUI(stg, (Caissier) user, main);
                        } else {
                            main.displayAdminUI(stg, (Admin) user, main);
                        }
                    } else {
                        messageInfo.setText("Mot de passe incorrect");
                    }
                } else {
                    messageInfo.setText("Login et/ou mot de passe incorrect");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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