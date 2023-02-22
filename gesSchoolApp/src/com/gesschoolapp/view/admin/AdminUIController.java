package com.gesschoolapp.view.admin;

import com.gesschoolapp.models.users.Admin;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.view.util.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminUIController implements Initializable {

    // ##### MAIN :

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Main mainApp;

    // Reference to the application's stage
    private Stage stage;

    // Reference to the current scene
    private Scene scene;

    private Admin currentUser;

    private Button selectedUserType;


    // ##### JAVAFX NODES :

    public Button getSelectedUserType() {
        return selectedUserType;
    }

    public void setSelectedUserType(Button selectedUserType) {
        this.selectedUserType = selectedUserType;
    }

    // -- Menu :

    @FXML
    private Button btnClasses;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnLogs;

    @FXML
    private Button btnProfile;

    // -- Others :

    @FXML
    private Circle pp_placeholder;

    @FXML
    private Button btnUtilisateurs;

    @FXML
    private BorderPane classesView;

    @FXML
    private BorderPane usersView;

    @FXML
    private BorderPane profileView;

    @FXML
    private BorderPane dashView;

    @FXML
    private BorderPane logsView;


    //  #### ROUTES :

    private Route currentRoute;

    private Route dashboard;

    private Route users;

    private Route classes;

    private Route logs;

    private Route profile;


    private String previousRouteLink;

    // ##### OTHERS :

    @FXML
    private Button btnSecretaires;

    @FXML
    private Button btnCaissiers;


    // ##### INIT :

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // --- Init routes :
        dashboard = new Route("Dashboard", dashView, btnDashboard);
        classes = new Route("Classes", classesView, btnClasses);
        profile = new Route("Mon profil", profileView, btnProfile);
        users = new Route("Utilisateurs", usersView, btnUtilisateurs);
        logs = new Route("Logs", logsView, btnLogs);

        // --- Init components :
        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));

        // --- Init route :
        setCurrentRoute(users);


    }


    // ##### GETTERS AND SETTERS :

    public Admin getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Admin currentUser) {
        this.currentUser = currentUser;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        currentRoute.getRouteView().toFront();
        System.out.println("SETTING");
        menuStyleReset();
        currentRoute.getNavSelection().setStyle("-fx-border-width:0 0 0 3px; -fx-border-color: white; -fx-background-color: #a2d6eb3d;");
        this.currentRoute = currentRoute;

    }


    public void setCurrentScene(Scene sc) {
        this.scene = sc;
    }


    /**
     * Is called by the controller to give a reference of it's stage back to itself.
     *
     * @param stage stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public Stage getStage() {
        return stage;
    }

    // #### METHODES DE STYLE FONCTIONNELS :

    //    Méthodes de style et fonctionnels pour le stage :

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e -> {
            scene.getRoot().setOnMouseDragged(e1 -> {
                this.getStage().setX(e1.getScreenX() - e.getSceneX());
                this.getStage().setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }

    @FXML
    public void onMinimize(MouseEvent event) {
        this.getStage().setIconified(true);
    }


    // ##### NAVIGATION & ROUTAGE :


    private void menuStyleReset() {
        btnDashboard.setStyle("-fx-border-width:0; -fx-background-color: transparent;");
        btnUtilisateurs.setStyle("-fx-border-width:0; -fx-background-color: transparent;");
        btnClasses.setStyle("-fx-border-width:0; -fx-background-color: transparent;");
        btnLogs.setStyle("-fx-border-width:0; -fx-background-color: transparent;");
        btnProfile.setStyle("-fx-border-width:0; -fx-background-color: transparent;");
    }

    @FXML
    public void handleHoverOn(MouseEvent e) {
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-background-color: #a2d6eb3d;-fx-border-width: 0 0 0 3px;-fx-border-color: white;");
    }

    @FXML
    public void handleHoverOff(MouseEvent e) {
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-border-width: 0 0 0 0px; -fx-background-color: transparent");
        setCurrentRoute(this.getCurrentRoute());

    }

    @FXML
    void handleNavigation(MouseEvent e) {
            System.out.println("NAVIG");
        if (e.getSource() == btnDashboard) {
            System.out.println("DASH");
            setCurrentRoute(dashboard);
        } else if (e.getSource() == btnClasses) {
            setCurrentRoute(classes);
        } else if (e.getSource() == btnProfile || e.getSource() == pp_placeholder) {
            setCurrentRoute(profile);
        } else if (e.getSource() == btnUtilisateurs) {
            setCurrentRoute(users);
        } else if (e.getSource() == btnLogs) {
            setCurrentRoute(logs);
        }
    }

    private String extractPreviousRouteLink() {
        StringBuilder sb = new StringBuilder();
        String currentRouteLink = getCurrentRoute().getRouteLink();
        String[] arr = currentRouteLink.split("/");

        for (int i = 0; i < arr.length - 1; i++) {
            if (i == 0) {
                sb.append(arr[i]);
            } else {
                sb.append("/" + arr[i]);
            }
        }
        return sb.toString();
    }

    @FXML
    void handleSelectedUserType(ActionEvent event) {
        if(event.getSource() == btnCaissiers){
        btnSecretaires.setStyle("    -fx-text-fill: #BABABA;\n" +
                "    -fx-border-width: 0 0 0px 0;");
        btnCaissiers.setStyle("   -fx-text-fill: #3c3a3a;\n" +
                "    -fx-border-width: 0 0 2px 0;\n" +
                "    -fx-border-color: #5099D0;");
        }else{
        btnCaissiers.setStyle("    -fx-text-fill: #BABABA;\n" +
                "    -fx-border-width: 0 0 0px 0;");
        btnSecretaires.setStyle("   -fx-text-fill: #3c3a3a;\n" +
                    "    -fx-border-width: 0 0 2px 0;\n" +
                    "    -fx-border-color: #5099D0;");
        }
    }
}


