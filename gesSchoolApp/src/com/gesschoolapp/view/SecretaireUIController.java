package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;

public class SecretaireUIController implements Initializable {


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Main mainApp;

    private Route currentRoute;

    private String previousRouteLink;

    private Secretaire currentUser;


    private Classe selectedClass;


    private Module selectedModule;

    private ClasseDAOImp classesData = new ClasseDAOImp();

    // On récupère la liste ici même des classes pour éviter les répétitions de requête

    private List<Classe> listeClasses;

    {
        try {
            listeClasses = classesData.getList();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Preview items controllers :

    @FXML
    private ClassPreviewItemController classPreview1Controller;
    @FXML
    private ClassPreviewItemController classPreview2Controller;


    // Views :

    @FXML
    private BorderPane homeView;

    @FXML
    private BorderPane classesView;

    @FXML
    private AnchorPane classStudentsView;

    @FXML
    private AnchorPane classNotesView;

    @FXML
    private AnchorPane classMainView;

    @FXML
    private BorderPane profileView;

    //    Route infos :

    @FXML
    private Button viewAllClasses;

    @FXML
    private Circle pp_placeholder;

    @FXML
    private Circle pp_placeholder1;

    @FXML
    private Label routeLink;

    @FXML
    private Button btnAccueil;

    @FXML
    private TextField searchClassInput;

    @FXML
    private TextField searchStudentHomeInput;

    @FXML
    private FontAwesomeIcon accueilIcon;

    @FXML
    private FontAwesomeIcon profileIcon;

    @FXML
    private FontAwesomeIcon classesIcon;

    @FXML
    private VBox classesHomeLayout;

    @FXML
    private HBox modulesLayout;

    @FXML
    private ImageView btnPrecedent;

    @FXML
    private VBox classesClassesLayout;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home = new Route("Accueil", homeView, btnAccueil, accueilIcon);
        classes = new Route("Classes", classesView, btnClasses, classesIcon);
        profile = new Route("Mon profil", profileView, btnProfile, profileIcon);

        // On donne une référence du super controlleur aux controlleurs filles :
        classPreview1Controller.setSuperController(this);
        classPreview2Controller.setSuperController(this);

        // SetCurrentRoute :
        setCurrentRoute(home);
        searchStudentHomeInput.requestFocus();


        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));
        pp_placeholder1.setFill(new ImagePattern(pp));
//        class_preview.setImage(new Image("resources/images/plc.png"));


        try {
            // Classes récentes :
            setClasseRecentes();

            // Liste de toutes les classes :
            setListeDesClasses(listeClasses);

            // Liste des modules d'une classe :
            setListeDesModules();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // getters et setters :

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


    public String getPreviousRouteLink() {
        return previousRouteLink;
    }

    public void setPreviousRouteLink(String previousRouteLink) {
        this.previousRouteLink = previousRouteLink;
    }


    public Secretaire getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Secretaire currentUser) {

        this.currentUser = currentUser;
        welcomeText.setText(" Bonjour " + currentUser.getFullName() + ", bienvenue !");
    }


    public void setCurrentScene(Scene sc) {
        this.scene = sc;
    }

    public Classe getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Classe selectedClass) {
        List<Node> classesRecentesCards = classesHomeLayout.getChildren();
        List<Node> allClassCards = classesClassesLayout.getChildren();

        for (Node classCard : allClassCards) {
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");
        }

        for (Node classCard : classesRecentesCards) {
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");
        }

        this.selectedClass = selectedClass;

        System.out.println(selectedClass.getIntitule());

        classPreview1Controller.setData(selectedClass);
        classPreview2Controller.setData(selectedClass);


        setCurrentRouteLink("/"+getSelectedClass().getIntitule());

        try {
            setListeDesModules();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setSelectedModule(Module selectedModule) {
        List<Node> modulesCard = modulesLayout.getChildren();
        for (Node moduleCard : modulesCard) {
            ((Pane) moduleCard).getChildren().get(2).setVisible(false);
        }

        this.selectedModule = selectedModule;
        System.out.println("MODULE SELECTIONNE :" + this.selectedModule.getIntitule());
    }

    public Module getSelectedModule() {
        return selectedModule;
    }


    void setCurrentRouteLink(String link) {
        setPreviousRouteLink(getCurrentRoute().getRouteLink());
        getCurrentRoute().setRouteLink(link);
        routeLink.setText(getCurrentRoute().getRouteLink());
        setSubView();
        System.out.println("############### ACTUAL ROUTE : " + getCurrentRoute().getRouteLink() + " - " + getCurrentRoute().getRouteMainName());
    }


//    Méthodes de style pour la scène :

    private void menuStyleReset() {
        accueilIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        classesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        profileIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        btnAccueil.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnClasses.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnProfile.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
    }

    @FXML
    public void handleHoverOn(MouseEvent e) {
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-background-color: #2C7ABA;-fx-text-fill: white;");

        if (menu_section == btnAccueil) {
            accueilIcon.setGlyphStyle("-fx-fill: white;");
        } else if (menu_section == btnClasses) {
            classesIcon.setGlyphStyle("-fx-fill: white;");
        } else if (menu_section == btnProfile) {
            profileIcon.setGlyphStyle("-fx-fill: white;");
        }

    }

    @FXML
    public void handleHoverOff(MouseEvent e) {
        Button menu_section = (Button) e.getSource();

        menu_section.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");

        if (menu_section == btnAccueil) {
            accueilIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        } else if (menu_section == btnClasses) {
            classesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        } else if (menu_section == btnProfile) {
            profileIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        }

        setCurrentRoute(this.getCurrentRoute());

    }

    @FXML
    void handleNavigation(MouseEvent e) {
        if (e.getSource() == btnAccueil) {
            setCurrentRoute(home);
            searchStudentHomeInput.requestFocus();
        } else if (e.getSource() == btnClasses || e.getSource() == viewAllClasses) {
            setCurrentRoute(classes);
            setCurrentRouteLink("/"+getSelectedClass().getIntitule());
            searchClassInput.requestFocus();
        } else if (e.getSource() == btnProfile || e.getSource() == pp_placeholder || e.getSource() == pp_placeholder1) {
            setCurrentRoute(profile);
        } else if (e.getSource() == btnPrecedent){

            setCurrentRouteLink(extractPreviousRouteLink());
        }
    }

    private String extractPreviousRouteLink() {
        StringBuilder sb = new StringBuilder();
        String currentRouteLink = getCurrentRoute().getRouteLink();
        String[] arr = currentRouteLink.split("/");

        for(int i=0;i<arr.length-1;i++){
            if(i==0){
                sb.append(arr[i]);
            }else{
                sb.append("/"+arr[i]);
            }
        }
        return sb.toString();
    }


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

    ;

    //    Méthodes de gestion du routage :

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        System.out.println(currentRoute.getRouteView());
        currentRoute.getRouteView().toFront();
        menuStyleReset();
        if(currentRoute == home){
            searchStudentHomeInput.requestFocus();
        }
        currentRoute.getNavSelection().setStyle("-fx-background-color: #2C7ABA;-fx-text-fill: white;");
        currentRoute.getRouteIcon().setGlyphStyle("-fx-fill: white;");
        currentRoute.getNavSelection().setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));
        this.currentRoute = currentRoute;

    }

    public void setCurrentRoute(){
        setCurrentRoute(classes);
    }

    private void setSubView() {
        System.out.println(getCurrentRoute().getRouteLink());
        if(getCurrentRoute().getRouteLink().equals("/"+getSelectedClass().getIntitule()+"/eleves")){
            classStudentsView.toFront();
            btnPrecedentIsActive(true);
        }else if(getCurrentRoute().getRouteLink().equals("/"+getSelectedClass().getIntitule()+"/notes")){
            classNotesView.toFront();
            btnPrecedentIsActive(true);
        }else if(getCurrentRoute().getRouteLink().equals("/"+getSelectedClass().getIntitule())){
            System.out.println("AFFICHE LE MAIN FDP");
            classMainView.toFront();
            btnPrecedentIsActive(false);
        }

    }

    private void btnPrecedentIsActive(boolean state){
        if(state == true){
            btnPrecedent.toFront();
            btnPrecedent.setDisable(false);
            btnPrecedent.setCursor(Cursor.HAND);
        }else{
            btnPrecedent.toBack();
            btnPrecedent.setDisable(true);
            btnPrecedent.setCursor(Cursor.TEXT);
        }
    }

    //    Méthodes de fonctionnalités de la vue :

    @FXML
    void handleDisconnect(ActionEvent event) {

        //ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
        alert.setContentText("Voulez-vous continuer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
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


    @FXML
    public void handleClassSearch(KeyEvent e) {
        String toSearch = "";
        toSearch +=searchClassInput.getText();

        List<Classe> found = new ArrayList<>();
        System.out.println(toSearch);
        try {

            for (Classe classe : listeClasses) {
                if(classe.getIntitule().contains(toSearch)){
                    found.add(classe);
                }
            }
                    clearListeDesClasses();
                    setListeDesClasses(found);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
//        }
    }

        private void setListeDesClasses(List<Classe> classes) throws DAOException, IOException {
        classesClassesLayout.getChildren(). clear();
        for (Classe classe : classes) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClassItem.fxml"));

            HBox hBox = fxmlLoader.load();
            ClassItemController cic = fxmlLoader.getController();
            cic.setSuperController(this);
            cic.setData(classe);

            if (selectedClass.getId() == classe.getId()) {
                hBox.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }

            classesClassesLayout.getChildren().add(hBox);
        }
    }

    private void setListeDesModules() throws DAOException, IOException {
        modulesLayout.getChildren().clear();
        List<Module> modules = selectedClass.getModules();
        for (Module module : modules) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModuleItem.fxml"));

            Pane pane = fxmlLoader.load();
            ModuleItemController mic = fxmlLoader.getController();
            mic.setSuperController(this);
            mic.setData(module);
//            ((FontAwesomeIcon) pane.getChildren().get(0)).setGlyphStyle(mic.getRandomTheme());

            if (selectedClass.getModules().get(1) == module) {
                setSelectedModule(module);
                mic.setAsSelected();
            }

            modulesLayout.getChildren().add(pane);


        }
    }

    private void setClasseRecentes() throws DAOException, IOException {
        List<Classe> classes = listeClasses;

        Random rand = new Random();
        int itr = rand.nextInt(classes.size() - 5);

        for (int i = itr; i <= itr + 4; i++) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClassItem.fxml"));

            HBox hBox = fxmlLoader.load();
            ClassItemController cic = fxmlLoader.getController();

            cic.setSuperController(this);
            cic.setData(classes.get(i));

            if (i == itr) {
                hBox.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
                this.setSelectedClass(classes.get(i));
            }

            classesHomeLayout.getChildren().add(hBox);
        }
    }

    private void clearListeDesClasses(){

        while (classesClassesLayout.getChildren().size() != 0){
        classesClassesLayout.getChildren().remove(classesClassesLayout.lookup(".class_card"));
        }


    }

    @FXML
    private void handleExit() {
        try {
            Timeline timeline = new Timeline();
            KeyFrame key;
            key = new KeyFrame(Duration.millis(50),
                    new KeyValue(this.getStage().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((ae) -> System.exit(0));
            timeline.play();
        } catch (Exception e) {
            e.getMessage();
        }

    }


}
