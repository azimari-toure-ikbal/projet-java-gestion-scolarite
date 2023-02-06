package com.gesschoolapp.view;

import com.gesschoolapp.runtime.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class SecretaireUIController implements Initializable {
    @FXML
    private Circle pp_placeholder;
    // Reference to the main com.gesschoolapp
    private Main main;

    @FXML
    private ImageView class_preview;

    // Reference to the current scene
    private Scene scene;

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
//        try{
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));
        class_preview.setImage(new Image("com/gesschoolapp/resources/images/plc.png"));
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

}
