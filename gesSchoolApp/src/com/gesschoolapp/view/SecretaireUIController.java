package com.gesschoolapp.view;

import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.Mismatch;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.gescsv.ApprenantsCSV;
import com.gesschoolapp.gescsv.NotesCSV;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.Caissier;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.controllers.NotesItemController;
import com.gesschoolapp.utils.Toolbox;
import com.gesschoolapp.view.util.Mois;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class SecretaireUIController implements Initializable {


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Main mainApp;

    @FXML
    private Label mainMessageInfo;

    @FXML
    private Button btnSemestre1;

    @FXML
    private Button btnSemestre2;

    @FXML
    private Label mainAwaitInfo;


    @FXML
    private TableView<Paiement> feesTable;

    @FXML
    private Label feeTotal;

    @FXML
    private TableColumn<Paiement, Integer> idFee;

    @FXML
    private TableColumn<Paiement, Double> montantFee;

    @FXML
    private TableColumn<Paiement, String> recuFee;

    @FXML
    private TableColumn<Paiement, String> rubrFee;

    @FXML
    private TableColumn<Paiement, Apprenant> eleveFee;

    private Route currentRoute;

    private String previousRouteLink;

    private Utilisateur currentUser;

    private boolean isCaissierSession;


    private Classe selectedClass;


    private Module selectedModule;

    private ClasseDAOImp classesData = new ClasseDAOImp();

    // On récupère la liste ici même des classes pour éviter les répétitions de requête

    private List<Classe> listeClasses;


    //    Preview items com.gesschoolapp.controllers :

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
    private AnchorPane panelJourn;

    @FXML
    private AnchorPane feesJournalierItem;

    @FXML
    private AnchorPane panelMens;

    @FXML
    private AnchorPane feesMensuelItem;

    @FXML
    private ChoiceBox<Mois> monthyFeeDP;

    @FXML
    private AnchorPane panelHebdo;

    @FXML
    private AnchorPane feesHebdoItem;

    @FXML
    private AnchorPane panelAnn;

    @FXML
    private AnchorPane feesAnnuelItem;

    @FXML
    private AnchorPane classStudentsView;


    @FXML
    private PieChart feesPie;


    private ObservableList<Paiement> dailyFeesList = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();


    @FXML
    private AnchorPane classNotesView;

    @FXML
    private AnchorPane classMainView;

    @FXML
    private BorderPane profileView;

    @FXML
    private BorderPane feesView;

    //    Route infos :

    @FXML
    private Button viewAllClasses;

    @FXML
    private Circle pp_placeholder;

    @FXML
    private Circle pp_placeholder1;

    @FXML
    private Circle pp_placeholder2;

    @FXML
    private Label routeLink;

    @FXML
    private Button btnAccueil;

    @FXML
    private Label caissierEtatDePaiementLabel;

    @FXML
    private TextField searchClassInput;

    @FXML
    private TextField searchStudentInput;

    @FXML
    private MenuButton FilterStudentInput;

    @FXML
    private MenuItem getAll;

    @FXML
    private MenuItem getPaye;

    @FXML
    private MenuItem getImpaye;

    @FXML
    private MenuItem getNonInscrit;

    @FXML
    private TextField searchStudentHomeInput;

    @FXML
    private Label classListName;


    @FXML
    private FontAwesomeIcon accueilIcon;

    @FXML
    private FontAwesomeIcon profileIcon;

    @FXML
    private FontAwesomeIcon feesIcon;

    @FXML
    private FontAwesomeIcon classesIcon;

    @FXML
    private VBox classesHomeLayout;

    @FXML
    private ChoiceBox<String> feesSpanSelect;

    private String[] etatsDePaiement = {"Journalier", "Hebdomadaire", "Mensuel", "Annuel"};

    private Mois[] mois = {Mois.JANVIER, Mois.FÉVRIER, Mois.MARS, Mois.AVRIL, Mois.MAI, Mois.JUIN, Mois.JUILLET, Mois.AOÛT, Mois.SEPTEMBRE, Mois.OCTOBRE, Mois.NOVEMBRE, Mois.DÉCEMBRE};

    @FXML
    private HBox modulesLayout;

    @FXML
    private VBox notesLayout;

    private Button selectedSemestre;

    @FXML
    private VBox studentsLayout;

    @FXML
    private ImageView btnPrecedent;

    @FXML
    private Button btnImportNotes;

    @FXML
    private Button btnImportStudents;

    @FXML
    private VBox classesClassesLayout;

    @FXML
    private VBox userMenu;

    @FXML
    private Button btnClasses;

    @FXML
    private Button btnPaiements;

    @FXML
    private DatePicker dailyFeeDP;


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

    Route fees;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home = new Route("Accueil", homeView, btnAccueil, accueilIcon);
        classes = new Route("Classes", classesView, btnClasses, classesIcon);
        profile = new Route("Mon profil", profileView, btnProfile, profileIcon);
        fees = new Route("Paiements", feesView, btnPaiements, feesIcon);

        listeClasses = null;
        try {
            listeClasses = classesData.getList();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }


        // On donne une référence du super controlleur aux controlleurs filles :
        classPreview1Controller.setSuperController(this);
        classPreview2Controller.setSuperController(this);

        // SetCurrentRoute :
        setCurrentRoute(home);


        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));
        pp_placeholder1.setFill(new ImagePattern(pp));
        pp_placeholder2.setFill(new ImagePattern(pp));
//        class_preview.setImage(new Image("resources/images/plc.png"));

        if (currentUser instanceof Secretaire) {
            setCaissierSession(false);
            setSecretaireView();
        } else {
            setCaissierSession(true);
        }
        System.out.println(isCaissierSession);


        try {

            // Classes récentes :
            setClasseRecentes();
            // Liste de toutes les classes :
            setListeDesClasses(listeClasses);
            // Liste des modules d'une classe :
            setListeDesModules();
//            setListeDesApprenants();

        } catch (Exception e) {
            e.printStackTrace();
        }

        feesSpanSelect.getItems().addAll(etatsDePaiement);
        monthyFeeDP.getItems().addAll(mois);
        monthyFeeDP.setValue(mois[0]);
        feesSpanSelect.setValue(etatsDePaiement[0]);
        feesJournalierItem.toFront();

        feesSpanSelect.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    switch (newValue) {
                        case "Journalier":
                            panelJourn.toFront();
                            feesJournalierItem.toFront();

                            break;
                        case "Hebdomadaire":
                            panelHebdo.toFront();
                            feesHebdoItem.toFront();
                            break;
                        case "Mensuel":
                            panelJourn.toFront();
                            feesMensuelItem.toFront();
                            break;
                        case "Annuel":
                            panelAnn.toFront();
                            feesAnnuelItem.toFront();
                            break;
                    }
                });

        idFee.setCellValueFactory(new PropertyValueFactory<Paiement, Integer>("idPaiement"));
        montantFee.setCellValueFactory(new PropertyValueFactory<Paiement, Double>("montant"));
        recuFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("numeroRecu"));
        rubrFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("rubrique"));
        eleveFee.setCellValueFactory(new PropertyValueFactory<Paiement, Apprenant>("apprenant"));

        dailyFeeDP.setValue(LocalDate.now());
        dailyFeesList = FXCollections.observableList(Toolbox.paiementsJournalier(LocalDate.now()));
        feesTable.setItems(dailyFeesList);
        double totalEncaisse = 0;
        for (Paiement paiement : dailyFeesList) {
            totalEncaisse += paiement.getMontant();
        }
        ;
        feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");


        for (Rubrique rubr : Toolbox.getRubriques()) {
            double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().equals(rubr.getIntitule())).toList().size();
            double effectifTotal = dailyFeesList.size();
            double pourcentage = (effectifPartiel / effectifTotal) * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
        }

        feesPie.setData(pieData);
        addPieTooltips();


    }

    public void addPieTooltips() {
        feesPie.getData().forEach(data -> {
            List<Paiement> concernedFees = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().equals(data.getName())).toList();
            Double totalEncaisse = 0.0;
            for (Paiement p : concernedFees) {
                totalEncaisse += p.getMontant();
            }
            String more = (int) Math.ceil((data.getPieValue() / 100) * dailyFeesList.size()) + " paiement(s) encaissé(s), soit " + totalEncaisse + "FCFA";

            Tooltip ttp = new Tooltip(more);
            Tooltip.install(data.getNode(), ttp);
        });
    }

    @FXML
    void getDailyFeeDate(ActionEvent e) {
        LocalDate newDate = dailyFeeDP.getValue();

        dailyFeesList = FXCollections.observableList(Toolbox.paiementsJournalier(newDate));
        feesTable.setItems(dailyFeesList);
        double totalEncaisse = 0;
        for(Paiement paiement : dailyFeesList) {
            totalEncaisse += paiement.getMontant();
        };
        feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");


        ObservableList<PieChart.Data> newPie = FXCollections.observableList(new ArrayList<PieChart.Data>());


        pieData.removeAll(pieData);


        for (Rubrique rubr : Toolbox.getRubriques()) {
            double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().equals(rubr.getIntitule())).toList().size();
            double effectifTotal = dailyFeesList.size();
            double pourcentage = (effectifPartiel / effectifTotal) * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
        }

    }

    public void refreshFeesData() {
        LocalDate newDate = dailyFeeDP.getValue();

        dailyFeesList = FXCollections.observableList(Toolbox.paiementsJournalier(newDate));
        feesTable.setItems(dailyFeesList);
        double totalEncaisse = 0;
        for(Paiement paiement : dailyFeesList) {
            totalEncaisse += paiement.getMontant();
        };
        feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");

        ObservableList<PieChart.Data> newPie = FXCollections.observableList(new ArrayList<PieChart.Data>());


        pieData.removeAll(pieData);


        for (Rubrique rubr : Toolbox.getRubriques()) {
            double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().equals(rubr.getIntitule())).toList().size();
            double effectifTotal = dailyFeesList.size();
            double pourcentage = (effectifPartiel / effectifTotal) * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
        }

        addPieTooltips();
    }

    public void resetVue() {
        try {
            resetSelectedClass(selectedClass);
            setListeDesApprenants(selectedClass.getApprenants());
            refreshFeesData();

        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // getters et setters :
    public ObservableList<Paiement> getDailyFeesList() {
        return this.dailyFeesList;
    }

    public void setDailyFeesList(ObservableList<Paiement> dailyFeesList) {
        this.dailyFeesList = dailyFeesList;

        for (Rubrique rubr : Toolbox.getRubriques()) {
            int nbPaiement = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().equals(rubr.getIntitule())).toList().size() / dailyFeesList.size() * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), nbPaiement));
        }

        feesPie.setData(pieData);
    }


    public boolean isCaissierSession() {
        return isCaissierSession;
    }

    public void setCaissierSession(boolean caissierSession) {
        isCaissierSession = caissierSession;
    }


    public List<Classe> getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(List<Classe> listeClasses) {
        this.listeClasses = listeClasses;
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

    public Button getSelectedSemestre() {
        return selectedSemestre;
    }

    public void setSelectedSemestre(Button selectedSemestre) {
        this.selectedSemestre = selectedSemestre;
        int numSemestre;
        if (selectedSemestre == btnSemestre1) {
            numSemestre = 1;
        } else {
            numSemestre = 2;
        }
        btnSemestre2.setStyle("-fx-border-width: 0;");
        btnSemestre1.setStyle("-fx-border-width: 0;");
        selectedSemestre.setStyle("-fx-border-width : 0 0 2px 0;-fx-border-color: #84898d;");
        List<Module> listeModulesSemestre = selectedClass.getModules().stream().
                filter(module -> Objects.equals(module.getSemestre(), numSemestre)).toList();

        try {
            setListeDesModules(listeModulesSemestre);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SELECTED " + selectedSemestre);
    }

    public String getPreviousRouteLink() {
        return previousRouteLink;
    }

    public void setPreviousRouteLink(String previousRouteLink) {
        this.previousRouteLink = previousRouteLink;
    }


    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilisateur currentUser) {

        this.currentUser = currentUser;

        if (currentUser instanceof Secretaire) {
            setCaissierSession(false);
            setSecretaireView();
        } else {
            setCaissierSession(true);
            System.out.println(isCaissierSession);
        }

        try {
            setListeDesApprenants();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

            String currentClassName = ((Label) ((HBox) classCard).lookup(".classItemLabel")).getText();

            if (currentClassName == selectedClass.getIntitule()) {
                classCard.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }
        }

        for (Node classCard : classesRecentesCards) {
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");

            String currentClassName = ((Label) ((HBox) classCard).lookup(".classItemLabel")).getText();

            if (currentClassName == selectedClass.getIntitule()) {
                classCard.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }
        }

        this.selectedClass = selectedClass;
        try {
            classesData.setLastView(selectedClass);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(selectedClass.getIntitule());

        classPreview1Controller.setData(selectedClass);
        classPreview2Controller.setData(selectedClass);
        setCurrentRouteLink("/" + getSelectedClass().getIntitule());

        try {
            setListeDesModules();
            setListeDesApprenants();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void resetSelectedClass(Classe selectedClass) {
        List<Node> classesRecentesCards = classesHomeLayout.getChildren();
        List<Node> allClassCards = classesClassesLayout.getChildren();

        for (Node classCard : allClassCards) {
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");
            String currentClassName = ((Label) ((HBox) classCard).lookup(".classItemLabel")).getText();

            if (currentClassName == selectedClass.getIntitule()) {
                classCard.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }

        }

        for (Node classCard : classesRecentesCards) {
            ((HBox) classCard).setStyle("-fx-background-color: #F2F5FA;-fx-background-radius: 7px;-fx-cursor: hand;");

            String currentClassName = ((Label) ((HBox) classCard).lookup(".classItemLabel")).getText();

            if (currentClassName == selectedClass.getIntitule()) {
                classCard.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }
        }

        this.selectedClass = selectedClass;

        System.out.println(selectedClass.getIntitule());

        classPreview1Controller.setData(selectedClass);
        classPreview2Controller.setData(selectedClass);

        try {
            Module oldSelectedModule = selectedModule;
            resetListeDesModules(selectedModule);
            setListeDesApprenants();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void setMainMessageInfo(String msg, int status) {
        mainMessageInfo.setText(msg);
        System.out.println(msg);

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
            setTimeout(() -> mainMessageInfo.setVisible(false), 10000);
        }

        if (status != 0)
            resetVue();

    }

    public void setMainMessageInfo(String msg) {
        setMainMessageInfo(msg, 1);
    }

    public void setSelectedModule(Module selectedModule) {
        List<Node> modulesCard = modulesLayout.getChildren();
        for (Node moduleCard : modulesCard) {
            Image closed = new Image("com/gesschoolapp/resources/images/closed_folder.png");
            ((ImageView) ((Pane) moduleCard).getChildren().get(0)).setImage(closed);
            ((Label) ((Pane) moduleCard).getChildren().get(1)).setStyle("-fx-font-size:9px;-fx-font-weight:bold;-fx-font-style:italic;-fx-padding: 0px 0 0 0;");

        }

        this.selectedModule = selectedModule;
        try {
            setListeDesNotes(selectedModule.getNotes());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        feesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        btnAccueil.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnClasses.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnProfile.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
        btnPaiements.setStyle("-fx-background-color: #F4F4F4; -fx-text-fill: #959da5;");
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
        } else if (menu_section == btnPaiements) {
            feesIcon.setGlyphStyle("-fx-fill: white;");
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
        } else if (menu_section == btnPaiements) {
            feesIcon.setGlyphStyle("-fx-fill: #2C7ABA;");
        }

        setCurrentRoute(this.getCurrentRoute());

    }

    @FXML
    void handleNavigation(MouseEvent e) {
        if (e.getSource() == btnAccueil) {
            setCurrentRoute(home);
        } else if (e.getSource() == btnClasses || e.getSource() == viewAllClasses) {
            setCurrentRoute(classes);
            setCurrentRouteLink("/" + getSelectedClass().getIntitule());
            searchClassInput.requestFocus();
        } else if (e.getSource() == btnProfile || e.getSource() == pp_placeholder || e.getSource() == pp_placeholder1) {
            setCurrentRoute(profile);
        } else if (e.getSource() == btnPrecedent) {
            setCurrentRouteLink(extractPreviousRouteLink());
        } else if (e.getSource() == btnPaiements) {
            setCurrentRoute(fees);
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

    @FXML
    public void handleFileExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".csv")) {
                file = new File(file.getPath() + ".csv");
            }
            ApprenantsCSV aCSV = new ApprenantsCSV();
            try {
                aCSV.writeCSVFile(file.getName(), selectedClass.getApprenants());
            } catch (CSVException e) {
                throw new RuntimeException(e);
            }
            this.setMainMessageInfo("Fichier " + file.getName() + " exporté avec succès !");
            mainApp.setPersonFilePath(file);
        }
    }

    public void handleFileImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            System.out.println(file.getName());
            if (event.getSource() == btnImportNotes) {
                NotesCSV nCSV = new NotesCSV();
                try {

                    List<String> fileContent = nCSV.readFile(file);
                    List<String[]> fileData = nCSV.getData(fileContent);
                    List<Note> importedNotes = nCSV.csvToObject(fileData, selectedModule, selectedClass);
                    List<Note> list = new ArrayList<>(selectedModule.getNotes());
                    list.addAll(importedNotes);
                    selectedModule.setNotes(list);

                    this.setMainMessageInfo("Notes importés avec succès !");
                } catch (CSVException | Mismatch e) {
                    setMainMessageInfo(e.getMessage(), 0);
                }
            }

            if (event.getSource() == btnImportStudents) {
                ApprenantsCSV aCSV = new ApprenantsCSV();
                try {
                    List<String> fileContent = aCSV.readFile(file);
                    List<String[]> fileData = aCSV.getData(fileContent);
                    List<Apprenant> importedApprenants;
                    importedApprenants = aCSV.csvToObject(fileData, selectedClass);
                    for (Apprenant appr : importedApprenants) {
                        System.out.println(
                                appr.getIdApprenant()
                        );
                    }
                    List<Apprenant> list = new ArrayList<>(selectedClass.getApprenants());

                    list.addAll(importedApprenants);

                    for (Apprenant appr : importedApprenants) {
                        Note newNote = new Note();
                        newNote.setApprenant(appr);

                        newNote.setNote(0);
                        List<Module> modules = getSelectedClass().getModules();
                        NoteDAOImp notesData = new NoteDAOImp();

                        for (Module module : modules) {
                            List<Note> notesList = new ArrayList<>(module.getNotes());
                            newNote.setModule(module.getIntitule());
                            //                newNote.setId();
                            notesList.add(newNote);
                            module.setNotes(notesList);
                        }
                    }
                    selectedClass.setApprenants(list);

                    this.setMainMessageInfo("Apprenants importés avec succès !");
                } catch (CSVException | Mismatch e) {
                    setMainMessageInfo(e.getMessage(), 0);
                }
            }
        }
    }


    //    Méthodes de gestion du routage :

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        System.out.println(currentRoute.getRouteView());
        currentRoute.getRouteView().toFront();
        menuStyleReset();
        currentRoute.getNavSelection().setStyle("-fx-background-color: #2C7ABA;-fx-text-fill: white;");
        currentRoute.getRouteIcon().setGlyphStyle("-fx-fill: white;");
        currentRoute.getNavSelection().setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));
        this.currentRoute = currentRoute;

    }

    public void setCurrentRoute() {
        setCurrentRoute(classes);
    }

    private void setSubView() {
        System.out.println(getCurrentRoute().getRouteLink());
        if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule() + "/eleves")) {
            classListName.setText("Élèves en " + getSelectedClass().getIntitule());
            classStudentsView.toFront();
            btnPrecedentIsActive(true);
        } else if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule() + "/notes")) {
            classNotesView.toFront();
            setSelectedSemestre(btnSemestre1);
            btnPrecedentIsActive(true);
        } else if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule())) {
            System.out.println("AFFICHE LE MAIN FDP");
            classMainView.toFront();
            btnPrecedentIsActive(false);
        }

    }

    private void btnPrecedentIsActive(boolean state) {
        if (state) {
            btnPrecedent.toFront();
            btnPrecedent.setDisable(false);
            btnPrecedent.setCursor(Cursor.HAND);
        } else {
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
        toSearch += searchClassInput.getText();

        List<Classe> found = new ArrayList<>();
        System.out.println(toSearch);
        try {

            for (Classe classe : listeClasses) {
                if (classe.getIntitule().contains(toSearch)) {
                    found.add(classe);
                }
            }
            clearListeDesClasses();
            setListeDesClasses(found);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void handleStudentsFilter(ActionEvent e) {
        List<Apprenant> newList = null;
        if (e.getSource() == getAll) {
            newList = selectedClass.getApprenants();
        } else if (e.getSource() == getPaye) {
            newList = selectedClass.getApprenants().stream().
                    filter(appr -> appr.getEtatPaiement() >= 1 && getSelectedClass().isCurrentEcheancePaid(appr)).toList();
        } else if (e.getSource() == getImpaye) {
            newList = selectedClass.getApprenants().stream().
                    filter(appr -> appr.getEtatPaiement() >= 1 && !getSelectedClass().isCurrentEcheancePaid(appr)).toList();
        } else {
            newList = selectedClass.getApprenants().stream().filter(appr -> appr.getEtatPaiement() == 0).toList();
        }

        try {
            setListeDesApprenants(newList);
        } catch (DAOException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void handleStudentSearch(KeyEvent e) {
        String toSearch = "";
        toSearch += searchStudentInput.getText();

        List<Apprenant> found = new ArrayList<>();
        System.out.println(toSearch);
        try {

            for (Apprenant appr : selectedClass.getApprenants()) {
                if (appr.getNom().contains(toSearch) || appr.getPrenom().contains(toSearch) || Integer.toString(appr.getMatricule()).contains(toSearch)) {
                    found.add(appr);
                }
            }
            setListeDesApprenants(found);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void setListeDesClasses(List<Classe> classes) throws DAOException, IOException {
        classesClassesLayout.getChildren().clear();
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
        setListeDesModules(selectedClass.getModules());
    }

    private void setListeDesModules(List<Module> modules) throws DAOException, IOException {
        modulesLayout.getChildren().clear();

        for (Module module : modules) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModuleItem.fxml"));

            Pane pane = fxmlLoader.load();
            ModuleItemController mic = fxmlLoader.getController();
            mic.setSuperController(this);
            mic.setData(module);
            if (modules.get(0) == module) {
                setSelectedModule(module);
                mic.setAsSelected();
            }

            modulesLayout.getChildren().add(pane);

        }


    }

    private void resetListeDesModules(Module toSelect) throws DAOException, IOException {
        resetListeDesModules(getSelectedClass().getModules(), toSelect);
    }

    private void resetListeDesModules(List<Module> modules, Module toSelect) throws DAOException, IOException {
        modulesLayout.getChildren().clear();

        for (Module module : modules) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModuleItem.fxml"));

            Pane pane = fxmlLoader.load();
            ModuleItemController mic = fxmlLoader.getController();
            mic.setSuperController(this);
            mic.setData(module);
            if (toSelect == module) {
                setSelectedModule(module);
                mic.setAsSelected();
            }

            modulesLayout.getChildren().add(pane);

        }


    }

    public void setListeDesApprenants() throws DAOException, IOException {
        setListeDesApprenants(selectedClass.getApprenants());
    }

    public void setListeDesApprenants(List<Apprenant> apprenants) throws DAOException, IOException {
        studentsLayout.getChildren().clear();

        for (Apprenant apprenant : apprenants) {
            System.out.println(apprenant.getNom());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ApprenantItem.fxml"));

            HBox hbox = fxmlLoader.load();
            ApprenantItemsController aic = fxmlLoader.getController();
            aic.setSuperController(this);
            aic.setData(apprenant);


            studentsLayout.getChildren().add(hbox);

        }
    }


    public void setListeDesNotes() throws DAOException, IOException {
        setListeDesNotes(selectedModule.getNotes());
    }

    public void setListeDesNotes(List<Note> notes) throws DAOException, IOException {
        System.out.println(" SETTED LISTE DES NOTES ");
        notesLayout.getChildren().clear();
        for (Note note : notes) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("NotesItem.fxml"));

            HBox hbox = fxmlLoader.load();

            NotesItemController nic = fxmlLoader.getController();
            nic.setSuperController(this);
            nic.setData(note);


            notesLayout.getChildren().add(hbox);

        }
    }

    private void setClasseRecentes() throws DAOException, IOException {
        List<Classe> classes = listeClasses;

        for (int i = 0; i <= 6; i++) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClassItem.fxml"));

            HBox hBox = fxmlLoader.load();
            ClassItemController cic = fxmlLoader.getController();

            cic.setSuperController(this);
            cic.setData(classes.get(i));

            if (i == 0) {
                hBox.setStyle("-fx-background-color: #fff;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
                this.setSelectedClass(classes.get(i));
            }

            classesHomeLayout.getChildren().add(hBox);
        }
    }


    private void clearListeDesClasses() {

        while (classesClassesLayout.getChildren().size() != 0) {
            classesClassesLayout.getChildren().remove(classesClassesLayout.lookup(".class_card"));
        }

    }

    @FXML
    private void onSemestreClicked(ActionEvent e) {
        setSelectedSemestre((Button) e.getSource());
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

    public void openStudentFeesDialog(Apprenant appr) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FeesDialog.fxml"));

            AnchorPane page = null;
            page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Renseigner un paiement");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            FeesDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setApprenant(appr);
            controller.setDraggable();

//        this.dialogStage.close();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void openStudentViewDialog(Apprenant appr) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ApprenantViewDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Profil élève");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ApprenantViewDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setApprenant(appr);
            controller.setDraggable();


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openStudentAddDialog(ActionEvent event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ApprenantAddDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Ajouter un élève");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
//                dialogStage.stageStyle(stageStyle.I);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ApprenantAddDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(mainApp);
            controller.setScene(scene);
            controller.setSuperController(this);
            controller.setCurrentClass(selectedClass);
            controller.setDraggable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStudentEditDialog(Apprenant selectedAppr) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ApprenantEditDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Modifier un élève");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
//                dialogStage.stageStyle(stageStyle.I);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ApprenantEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCurrentClass(selectedClass);
            controller.setMain(mainApp);
            controller.setScene(scene);
            controller.setSuperController(this);
            controller.setDraggable();
            controller.setSelectedStudent(selectedAppr);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public void setSecretaireView() {
        System.out.println(userMenu.lookup(".caissier_item"));
        userMenu.getChildren().remove(userMenu.lookup(".caissier_item"));
        classStudentsView.getChildren().remove(classStudentsView.lookup(".caissier_item"));
        classStudentsView.getChildren().remove(classStudentsView.lookup(".caissier_item"));
        caissierEtatDePaiementLabel.setText("Date de naissance");
    }

}
