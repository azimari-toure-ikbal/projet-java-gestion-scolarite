package com.gesschoolapp.runtime;

import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.view.LoginUIController;
import com.gesschoolapp.view.LoginUIController;
import com.gesschoolapp.view.SecretaireUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    // ceci est un commentaire venant du bazard personnel a al amine

    public Stage primaryStage;
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            this.primaryStage = primaryStage;
            primaryStage.initStyle(StageStyle.UNDECORATED);

            initLayout();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the root layout.
     */
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/LoginUI.fxml"));
            Parent dash = loader.load();
            Scene scene = new Scene(dash);

            // Set the com.gesschoolapp scene,title and Icon.
            primaryStage.setScene(scene);
            primaryStage.setTitle("SchoolUp - Login");
            primaryStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));

            // Set the main and current scene references into controller
            LoginUIController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCurrentScene(scene);

            // Makes the stage draggable
            controller.setDraggable();

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }


    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void displaySecretaireUI(Stage stg, Secretaire user, Main main) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/SecretaireUI.fxml"));
            Parent dash = loader.load();

            Scene scene = new Scene(dash);

            SecretaireUIController controller = loader.getController();

            // Set the current stage and scene references into controller
            controller.setCurrentScene(scene);
            controller.setStage(stg);
            controller.setCurrentUser((Secretaire) user);
            controller.setMainApp(main);
            // Makes the stage draggable
            controller.setDraggable();

            stg.setScene(scene);
            stg.show();
        }catch (Exception e){
            System.out.println("NTM");
        }

    }


}