package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ActionDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.concrete.Admin;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import com.gesschoolapp.utils.ActionType;
import com.gesschoolapp.utils.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private Circle profile_pic_placeholder;


    @FXML
    private MenuItem getSuppression;

    @FXML
    private MenuItem getModification;

    @FXML
    private MenuItem getAjout;

    @FXML
    private MenuItem getAll;

    @FXML
    private Label mainMessageInfo;

    @FXML
    private Label labelUserMail;

    @FXML
    private Button btnUtilisateurs;

    @FXML
    private BorderPane classesView;

    @FXML
    private BorderPane usersView;

    @FXML
    private BorderPane profileView;

    @FXML
    private TextField globalSearchInput;

    @FXML
    private BorderPane dashView;

    @FXML
    private BorderPane logsView;

    @FXML
    private VBox usersLayout;


    private HBox usersLayoutPlaceholder;

    @FXML
    private VBox studentsHomeLayout;

    private HBox studentsHomeLayoutPlaceholder;

    @FXML
    private VBox classesLayout;

    private HBox classesLayoutPlaceholder;

    @FXML
    private VBox actionsLayout;


    private HBox actionsLayoutPlaceholder;

    @FXML
    private Label userFullName;

    @FXML
    private Label userMail;

    @FXML
    private Label userPhoneNumber;


    @FXML
    private DatePicker labelLogsDate;

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
    private Label labelNbStudents;

    @FXML
    private Label labelWelcome;

    @FXML
    private Label labelNbUsers;

    @FXML
    private Label labelBilanJourn;

    @FXML
    private Button btnCaissiers;

    @FXML
    private Button btnAdmins;

    //  #### DAOS :

    UserDAOImp userData = new UserDAOImp();

    ClasseDAOImp classData = new ClasseDAOImp();

    ActionDAOImp actionData = new ActionDAOImp();

    ApprenantDAOImp apprenantsData = new ApprenantDAOImp();
    List<Apprenant> apprenantsList;

    List<Utilisateur> usersList;

    List<Classe> classesList;

    List<Action> actionsList;

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

        // --- init placeholders :

        studentsHomeLayoutPlaceholder = (HBox) studentsHomeLayout.getChildren().get(0);
        classesLayoutPlaceholder = (HBox) classesLayout.getChildren().get(0);
        actionsLayoutPlaceholder = (HBox) actionsLayout.getChildren().get(0);
        usersLayoutPlaceholder = (HBox) usersLayout.getChildren().get(0);


        // --- Init route :
        setCurrentRoute(dashboard);

        // --- Init values :
        setSelectedUserType(btnSecretaires);

        try {
            usersList = userData.getList();
            classesList = classData.getList();
            actionsList = actionData.getActions();
            apprenantsList = apprenantsData.getList();

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        // --- Set lists :
        setListeDesUtilisateurs(usersList);
        setListeDesClasses(classesList);
        setListeDesApprenants();
        labelLogsDate.setValue(LocalDate.now());
        setListeDesActions(actionsList.stream().filter(action -> action.getDate().toLocalDate().equals(LocalDate.now())).toList());

        // -- set Dashboard view cards :

        labelNbStudents.setText(apprenantsList.size()+"");
        List<Paiement> dailyFees = Toolbox.paiementsJournalier(LocalDate.now());

        Double dailyReport = 0.0;
        for(Paiement p : dailyFees){
            dailyReport+= p.getMontant();
        }
        labelBilanJourn.setText(dailyReport+" FCFA");

        labelNbUsers.setText(usersList.size()+"");
    }


    // ##### GETTERS AND SETTERS :

    public String getSelectedUserTypeString(){
        if (getSelectedUserType() == btnCaissiers) {
            return "caissier";
        } else if (getSelectedUserType() == btnSecretaires) {
            return "secretaire";
        } else if (getSelectedUserType() == btnAdmins) {
            return "administrateur";
        }
    return null;
    }

    @FXML
    void getLogsDate(ActionEvent event) {
        System.out.println("NEW LOG LIST !");
        LocalDate newDate = labelLogsDate.getValue();
        setListeDesActions(actionsList.stream().filter(action -> action.getDate().toLocalDate().equals(newDate)).toList());
    }

    public List<Utilisateur> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Utilisateur> usersList) {
        this.usersList = usersList;
    }


    public Button getSelectedUserType() {
        return selectedUserType;
    }

    public String getSelectedUserTypeStringified() {
        if(getSelectedUserType() == btnAdmins){
            return "administrateur";
        }else if(getSelectedUserType() == btnCaissiers){
            return "caissier";
        }else if(getSelectedUserType() == btnSecretaires){
            return "secretaire";
        }
        return null;
    }


    public void setSelectedUserType(Button selectedUserType) {
        this.selectedUserType = selectedUserType;
        if(selectedUserType == btnCaissiers){
            btnSecretaires.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnAdmins.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnCaissiers.setStyle("   -fx-text-fill: #3c3a3a;\n" +
                    "    -fx-border-width: 0 0 2px 0;\n" +
                    "    -fx-border-color: #5099D0;");
        }else if(selectedUserType == btnSecretaires){
            btnCaissiers.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnAdmins.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnSecretaires.setStyle("   -fx-text-fill: #3c3a3a;\n" +
                    "    -fx-border-width: 0 0 2px 0;\n" +
                    "    -fx-border-color: #5099D0;");
        }else if(selectedUserType == btnAdmins){
            btnCaissiers.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnSecretaires.setStyle("    -fx-text-fill: #BABABA;\n" +
                    "    -fx-border-width: 0 0 0px 0;");
            btnAdmins.setStyle("   -fx-text-fill: #3c3a3a;\n" +
                    "    -fx-border-width: 0 0 2px 0;\n" +
                    "    -fx-border-color: #5099D0;");
        }
    }

    public Admin getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Admin currentUser) {
        // --- Init user profile :
        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        profile_pic_placeholder.setFill(new ImagePattern(pp));
        userFullName.setText(currentUser.getFullName());
        userMail.setText(currentUser.getEmail());
        userPhoneNumber.setText(currentUser.getNumero());
        labelWelcome.setText("Bienvenue, " + currentUser.getFullName() + " !");
        labelUserMail.setText(currentUser.getEmail());
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
        globalSearchInput.setText("");
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

    public void setMainMessageInfo(String msg) {
        setMainMessageInfo(msg, 1);
    }

    public void setMainMessageInfo(String msg, int status) {
        mainMessageInfo.setText(msg);

        if (status == 0) {
            mainMessageInfo.setStyle("-fx-background-color: #CE4F4B;-fx-background-radius: 0 0 5px 5px;");
        } else if (status == 1) {
            mainMessageInfo.setStyle("-fx-background-color:  #5CB85C;-fx-background-radius: 0 0 5px 5px;");
        } else {
            mainMessageInfo.setStyle("-fx-background-color: #007AF2;-fx-background-radius: 0 0 5px 5px;");
        }

        if (msg.length() == 0) {
            mainMessageInfo.setVisible(false);
        } else {
            mainMessageInfo.setVisible(true);
            Toolbox.setTimeout(() -> mainMessageInfo.setVisible(false), 3000);
        }

        if (status != 0)
            resetVue();

    }

    @FXML
    public void handleGlobalSearch(KeyEvent e) {
        if(currentRoute == dashboard){
            String toSearch = "";
            toSearch += globalSearchInput.getText().toLowerCase();

            List<Apprenant> found = new ArrayList<>();
            try {

                for (Apprenant appr : apprenantsList) {
                    if (appr.getNom().toLowerCase().contains(toSearch) || appr.getPrenom().toLowerCase().contains(toSearch) || Integer.toString(appr.getMatricule()).toLowerCase().contains(toSearch)) {
                        found.add(appr);
                    }
                }
                setListeDesApprenants(found);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if(currentRoute == classes){
            String toSearch = "";
            toSearch += globalSearchInput.getText().toLowerCase();

            List<Classe> found = new ArrayList<>();
            try {

                for (Classe cls : classesList) {
                    if (cls.getIntitule().toLowerCase().contains(toSearch) || cls.getFormation().toLowerCase().contains(toSearch)) {
                        found.add(cls);
                    }
                }
                setListeDesClasses(found);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if(currentRoute == users){
            String toSearch = "";
            toSearch += globalSearchInput.getText().toLowerCase();

            List<Utilisateur> found = new ArrayList<>();
            try {

                for (Utilisateur user : usersList) {
                    if (user.getFullName().toLowerCase().contains(toSearch) && user.getType().equalsIgnoreCase(getSelectedUserTypeString())) {
                        found.add(user);
                    }
                }
                setListeDesUtilisateurs(found);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    public void handleActionsFilter(ActionEvent e) {
        List<Action> newList = actionsList;
        System.out.println();
        if(e.getSource() == getAjout){
            newList = newList.stream().filter(action -> action.getAction() == ActionType.ADD && action.getDate().toLocalDate().equals(labelLogsDate.getValue())).toList();
            setListeDesActions(newList);
        }else if(e.getSource() == getModification){
            newList = newList.stream().filter(action -> action.getAction() == ActionType.UPDATE && action.getDate().toLocalDate().equals(labelLogsDate.getValue())).toList();
            setListeDesActions(newList);
        }else if(e.getSource() == getSuppression){
            newList = newList.stream().filter(action -> action.getAction() == ActionType.DELETE && action.getDate().toLocalDate().equals(labelLogsDate.getValue())).toList();
            setListeDesActions(newList);
        }else{
            newList = newList.stream().filter(action -> action.getDate().toLocalDate().equals(labelLogsDate.getValue())).toList();
            setListeDesActions(newList);
        }
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

    @FXML
    void handleStudentsFilter(ActionEvent event) {

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
        setSelectedUserType((Button) event.getSource());
        setListeDesUtilisateurs();
    }

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


    // ##### VUE SETTERS,RESETTERS :

    private void resetVue() {
        setListeDesUtilisateurs();
    }


    public void setListeDesUtilisateurs(){
        setListeDesUtilisateurs(usersList);
    }
    public void setListeDesUtilisateurs(List<Utilisateur> users) {
        usersLayout.getChildren().clear();

        if (users.size() == 0) {
            usersLayout.getChildren().add(usersLayoutPlaceholder);
        }

        if (getSelectedUserType() == btnCaissiers) {
            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("caissier")).toList();
        } else if (getSelectedUserType() == btnSecretaires) {
            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("secretaire")).toList();
        } else if (getSelectedUserType() == btnAdmins) {
            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("administrateur")).toList();
        }

        if (users.size() == 0) {
            usersLayout.getChildren().add(usersLayoutPlaceholder);
        }

        for (Utilisateur user : users) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("UserItem.fxml"));
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UserItemController uic = fxmlLoader.getController();
            uic.setSuperController(this);
            uic.setData(user);


            usersLayout.getChildren().add(hbox);

        }
    }

    public void setListeDesApprenants(){
        setListeDesApprenants(apprenantsList);
    }
    public void setListeDesApprenants(List<Apprenant> apprs) {

        if (apprs.size() != 0) {
            studentsHomeLayout.getChildren().clear();
        }

        for (Apprenant appr : apprs) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ApprenantItem.fxml"));
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ApprenantItemController aic = fxmlLoader.getController();
            aic.setSuperController(this);
            aic.setData(appr);


            studentsHomeLayout.getChildren().add(hbox);

        }
    }

    public void setListeDesClasses(){
        setListeDesClasses(classesList);
    }
    public void setListeDesClasses(List<Classe> classes) {

        classesLayout.getChildren().clear();

        if (classes.size() == 0) {
            classesLayout.getChildren().add(classesLayoutPlaceholder);
        }


//        if (getSelectedUserType() == btnCaissiers) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("caissier")).toList();
//        } else if (getSelectedUserType() == btnSecretaires) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("secretaire")).toList();
//        } else if (getSelectedUserType() == btnAdmins) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("administrateur")).toList();
//        }

        for (Classe classe : classes) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClassItem.fxml"));
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClassItemController uic = fxmlLoader.getController();
            uic.setSuperController(this);
            uic.setData(classe);

            classesLayout.getChildren().add(hbox);

        }
    }
    public void setListeDesActions() {
        setListeDesActions(actionData.getActions());
    }

    public void setListeDesActions(List<Action> actions){
        actionsLayout.getChildren().clear();

        if(actions.size() == 0){
            actionsLayout.getChildren().add(actionsLayoutPlaceholder);
        }

        for (Action action : actions) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ActionItem.fxml"));
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ActionItemController aic = fxmlLoader.getController();
            aic.setSuperController(this);
            aic.setData(action);

            actionsLayout.getChildren().add(hbox);
        }
    }

        // #### HANDLE DIALOG BOXES :

    @FXML
    void openUserAddDialog(ActionEvent event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserAddDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Ajouter un utilisateur");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserAddDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void openUserViewDialog(Utilisateur user) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserViewDialog.fxml"));

                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("School UP Admin - Profil utilisateur");
                // Set the application icon.

                dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
                dialogStage.initStyle(StageStyle.UNDECORATED);
                dialogStage.setResizable(false);


                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainApp.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the person into the controller.
                UserViewDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setSuperController(this);
                controller.setScene(scene);
                controller.setMain(mainApp);
                controller.setData(user);
                controller.setDraggable();


                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void openUserChartDialog(Utilisateur user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserChartDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Etat de paiement journalier");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserChartDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setData(user);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUserEditDialog(Utilisateur user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserEditDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Modifier un utilisateur");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setData(user);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGesModulesView(Classe classe) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ClassGesModuleView.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Gérer les modules");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ClassGesModuleViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setData(classe);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProfileBtnClicked(ActionEvent event) {
        this.openProfileEditDialog(currentUser);
    }

    @FXML
    public void openProfileEditDialog(Utilisateur user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ProfileEdit.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Modifier mon profil");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ProfileEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setData(user);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean openCertificatViewDialog(Apprenant appr) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CertificatDialogView.fxml"));

            String certifPath;

            try{
                certifPath = Toolbox.getCertificatsImgPath(appr);
            }catch(RuntimeException e){
                setMainMessageInfo(e.getMessage(),0);
                return false;
            }


            Pane page = (Pane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Certificat apprenant");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CertificatDialogViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setApprenant(appr,certifPath);
            controller.setDraggable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}


