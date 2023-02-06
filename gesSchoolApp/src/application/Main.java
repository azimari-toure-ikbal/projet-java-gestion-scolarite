package application;

import application.view.LoginUIController;
import application.view.SecretaireUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public Stage primaryStage;

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
     * person fi    le.
     */
    public void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/SecretaireUI.fxml"));
            Parent dash = loader.load();
            Scene scene = new Scene(dash);

            // Set the application scene,title and Icon.
            primaryStage.setScene(scene);
            primaryStage.setTitle("SchoolUp - Login");
            primaryStage.getIcons().add(new Image("file:C:\\Users\\TOSHIBA PC\\intellij-workspace\\gesSchoolApp\\src\\resources\\images\\app_icon.png"));

            // Set the main and current scene references into controller
            SecretaireUIController controller = loader.getController();
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

}