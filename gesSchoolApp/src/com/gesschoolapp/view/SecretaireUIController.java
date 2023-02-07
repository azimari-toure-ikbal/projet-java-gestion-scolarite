package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class SecretaireUIController implements Initializable {


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Main mainApp;

    private Route currentRoute;

    private Secretaire currentUser;

    private Classe selectedClass;

    private ClasseDAOImp classesData = new ClasseDAOImp();

    @FXML
    private ClassPreviewItemController classPreview1Controller;
    @FXML
    private ClassPreviewItemController classPreview2Controller;

    public Classe getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Classe selectedClass) {
        List<Node> allClassCards = classesHomeLayout.getChildren();

        for(Node classCard: allClassCards){
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");
        }
        this.selectedClass = selectedClass;

        System.out.println(selectedClass.getIntitule());

        classPreview1Controller.setData(selectedClass);
        classPreview2Controller.setData(selectedClass);

    }



//    Route infos :

    @FXML
    private BorderPane homeView;

    @FXML
    private BorderPane classesView;

    @FXML
    private BorderPane profileView;

    @FXML
    private Circle pp_placeholder;

    @FXML
    private Button btnAccueil;

    @FXML
    private FontAwesomeIcon accueilIcon;

    @FXML
    private FontAwesomeIcon profileIcon;

    @FXML
    private FontAwesomeIcon classesIcon;

    @FXML
    private VBox classesHomeLayout;



    @FXML
    private Button btnClasses;


    @FXML
    private Button btnProfile;

    @FXML
    private Label welcomeText;



    // Reference to the application's stage
    private Stage stage;

    // Reference to the current scene
    private Scene scene;

    // Routes :
    Route home;
    Route classes;
    Route profile;


//    private UserDaoImplDB users = new UserDaoImplDB();

    /**
     * Is called by the controller to give a reference of it's stage back to itself.
     *
     * @param stage stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public Stage getStage() {
//        stage = (Stage) btnAccueil.getScene().getWindow();
        return stage;
    }


    public Secretaire getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Secretaire currentUser) {

        this.currentUser = currentUser;
        welcomeText.setText(" Bonjour " + currentUser.getFullName() + ", bienvenue !");
    }



    /**
     * Is called by the main application to give a reference of its current Scene Root to itself.
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
                    new KeyValue (this.getStage().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((ae) -> System.exit(0));
            timeline.play();
        } catch (Exception e) {e.getMessage();}

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home = new Route("Accueil",homeView,btnAccueil,accueilIcon);
        classes = new Route("Classes",classesView,btnClasses,classesIcon);
        profile = new Route("Mon profil",profileView,btnProfile,profileIcon);

        // SetCurrentRoute :
        setCurrentRoute(home);

        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));
//        class_preview.setImage(new Image("resources/images/plc.png"));

        // Classes récentes :

        try {
            List<Classe> classes = classesData.getList();

            Random rand = new Random();
            int itr = rand.nextInt(classes.size() - 5);

            for(int i=itr;i<=itr+4;i++){

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ClassItem.fxml"));

                HBox hBox = fxmlLoader.load();
                ClassItemController cic = fxmlLoader.getController();
                cic.setSuperController(this);
                cic.setData(classes.get(i));

                if(i==itr){
                    hBox.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
                    this.setSelectedClass(classes.get(i));
                }

                classesHomeLayout.getChildren().add(hBox);

            }

        } catch (Exception e) {
            System.out.println("e.getMessage()");
        }


    }



    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                this.getStage().setX(e1.getScreenX() - e.getSceneX());
                this.getStage().setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }

    @FXML
    public void onMinimize(MouseEvent event){
        this.getStage().setIconified(true);
    };


    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        System.out.println(currentRoute.getRouteView());
        currentRoute.getRouteView().toFront();
        menuStyleReset();
        currentRoute.getNavSelection().setStyle("-fx-background-color: #2C7ABA;-fx-text-fill: white;");
        currentRoute.getRouteIcon().setGlyphStyle("-fx-fill: white;");
        currentRoute.getNavSelection().setBackground(new Background(new BackgroundFill(Color.rgb(113,86,221), CornerRadii.EMPTY, Insets.EMPTY)));
        this.currentRoute = currentRoute;
    }

    private void menuStyleReset() {
        accueilIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        classesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        profileIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        btnAccueil.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnClasses.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnProfile.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
    }

    @FXML
    public void handleHoverOn(MouseEvent e){
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-background-color: #2C7ABA;-fx-text-fill: white;");

        if(menu_section == btnAccueil){
            accueilIcon.setGlyphStyle("-fx-fill: white;");
        }else if(menu_section == btnClasses){
            classesIcon.setGlyphStyle("-fx-fill: white;");
        }else if(menu_section == btnProfile){
            profileIcon.setGlyphStyle("-fx-fill: white;");
        }

    }

    @FXML
    public void handleHoverOff(MouseEvent e){
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");

        if(menu_section == btnAccueil){
            accueilIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        }else if(menu_section == btnClasses){
            classesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        }else if(menu_section == btnProfile){
            profileIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        }

        setCurrentRoute(this.getCurrentRoute());

    }



    @FXML
    void handleNavigation(MouseEvent e) {
        if(e.getSource() == btnAccueil){
            System.out.println(" HOVER ?");
            setCurrentRoute(home);
        }else if(e.getSource() == btnClasses){
            setCurrentRoute(classes);
        }else if(e.getSource() == btnProfile){
            setCurrentRoute(profile);
        }
    }

    @FXML
    void handleDisconnect(ActionEvent event){

        //ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
        alert.setContentText("Voulez-vous continuer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            try {
                Node node = (Node) event.getSource();
                Stage stg = (Stage) node.getScene().getWindow();
                stg.close();

                mainApp.initLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
