package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.Mismatch;
import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.db.DAOClassesImpl.*;
import com.gesschoolapp.docmaker.PDFGenerator;
import com.gesschoolapp.gescsv.ApprenantsCSV;
import com.gesschoolapp.gescsv.NotesCSV;
import com.gesschoolapp.models.actions.Notification;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.concrete.Caissier;
import com.gesschoolapp.models.users.concrete.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import com.gesschoolapp.utils.Route;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

public class ScolariteUIController implements Initializable {


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Main mainApp;

    @FXML
    private Label mainMessageInfo;

    @FXML
    private Label labelTypeUser;

    @FXML
    private Label userFullName;

    @FXML
    private Label userMail;

    @FXML
    private Label userPhoneNumber;

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

    private ApprenantDAOImp apprenantsData = new ApprenantDAOImp();
    private List<Apprenant> allApprenants;
    private PaiementDAOImp paiementsData = new PaiementDAOImp();

    private UserDAOImp usersData = new UserDAOImp();



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
    private AnchorPane panelFeesState;

    @FXML
    private AnchorPane feesJournalierItem;

    @FXML
    private AnchorPane panelEmpty;

    @FXML
    private AnchorPane feesMensuelItem;

    @FXML
    private ChoiceBox<String> monthyFeeDP;

    @FXML
    private ChoiceBox<String> yearFeeDP;

    @FXML
    private ChoiceBox<String> yearFeeDP2;

    @FXML
    private DatePicker weekFeeDP;

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

    @FXML
    private BarChart<String, Double> yearBarChart;


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
    private Circle notifCircle1;
    @FXML
    private Circle notifCircle2;
    @FXML
    private Circle notifCircle3;

    @FXML
    private Button notifBtn1;
    @FXML
    private Button notifBtn2;
    @FXML
    private Button notifBtn3;

    @FXML
    private Circle profile_pic_placeholder;

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
    private Label labelYearlyDue;

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

    private String[] mois = DateFormatSymbols.getInstance(Locale.FRENCH).getMonths();
    @FXML
    private HBox modulesLayout;


    @FXML
    private VBox notifsLayout;

    private HBox notifsLayoutPlaceholder;

    @FXML
    private VBox notesLayout;

    private HBox notesLayoutPlaceholder;

    private Button selectedSemestre;

    @FXML
    private VBox studentsLayout;


    private HBox studentsLayoutPlaceholder;

    @FXML
    private VBox studentsHomeLayout;

    private HBox studentsHomeLayoutPlaceholder;

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

        // ---- Init routes :
        home = new Route("Accueil", homeView, btnAccueil, accueilIcon);
        classes = new Route("Classes", classesView, btnClasses, classesIcon);
        profile = new Route("Mon profil", profileView, btnProfile, profileIcon);
        fees = new Route("Paiements", feesView, btnPaiements, feesIcon);

        // ---- Init placeholders :
        studentsLayoutPlaceholder = (HBox) studentsLayout.getChildren().get(0);
        notesLayoutPlaceholder = (HBox) notesLayout.getChildren().get(0);
        notifsLayoutPlaceholder = (HBox) notifsLayout.getChildren().get(0);
        studentsHomeLayoutPlaceholder = (HBox) studentsHomeLayout.getChildren().get(0);

        listeClasses = null;
        try {
            listeClasses = classesData.getList();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
            try {
                allApprenants = apprenantsData.getList();
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }


        // On donne une référence du super controlleur aux controlleurs filles :
        classPreview1Controller.setSuperController(this);
        classPreview2Controller.setSuperController(this);



        // SetCurrentRoute :
        setCurrentRoute(home);

        if (currentUser instanceof Secretaire) {
            setCaissierSession(false);
            setSecretaireView();
        } else if(currentUser instanceof Caissier) {
            setCaissierSession(true);

        }



        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));
        pp_placeholder1.setFill(new ImagePattern(pp));
        pp_placeholder2.setFill(new ImagePattern(pp));
        profile_pic_placeholder.setFill(new ImagePattern(pp));

        feesSpanSelect.getItems().addAll(etatsDePaiement);
        monthyFeeDP.getItems().addAll(mois);
        try {
            List<String> annees = paiementsData.getAnnees();

            if(annees.size() == 0){
                panelEmpty.toFront();
            }else{
                yearFeeDP.getItems().addAll(paiementsData.getAnnees());
                yearFeeDP.setValue(paiementsData.getAnnees().get(0));
                refreshFeesData(etatsDePaiement[3]);
                yearFeeDP2.getItems().addAll(paiementsData.getAnnees());
                yearFeeDP2.setValue(paiementsData.getAnnees().get(0));
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }


        monthyFeeDP.setValue(mois[0]);
        feesSpanSelect.setValue(etatsDePaiement[0]);
        panelFeesState.toFront();
        feesJournalierItem.toFront();
        dailyFeeDP.setValue(LocalDate.now());
        panelFeesState.toFront();
        if(dailyFeesList.size() == 0){
            panelEmpty.toFront();
        }

        feesSpanSelect.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    switch (newValue) {
                        case "Journalier":
                            panelFeesState.toFront();
                            feesJournalierItem.toFront();
                            dailyFeeDP.setValue(LocalDate.now());
                            refreshFeesData(etatsDePaiement[0]);
                            break;
                        case "Hebdomadaire":
                            panelFeesState.toFront();
                            feesHebdoItem.toFront();
                            weekFeeDP.setValue(LocalDate.now());
                            refreshFeesData(etatsDePaiement[1]);
                            break;
                        case "Mensuel":
                            panelFeesState.toFront();
                            monthyFeeDP.setValue(mois[LocalDate.now().getMonthValue() - 1]);
                            try {
                                yearFeeDP2.setValue(paiementsData.getAnnees().get(0));
                            } catch (DAOException e) {
                                throw new RuntimeException(e);
                            }
                            feesMensuelItem.toFront();
                            refreshFeesData(etatsDePaiement[2]);
                            break;
                        case "Annuel":
                            panelAnn.toFront();
                            feesAnnuelItem.toFront();
                            try {
                                yearFeeDP.setValue(paiementsData.getAnnees().get(0));
                            } catch (DAOException e) {
                                throw new RuntimeException(e);
                            }
                            feesAnnuelItem.toFront();
                            refreshFeesData(etatsDePaiement[3]);
                            break;
                        default:
                    }
                });

        yearFeeDP2.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    refreshFeesData(etatsDePaiement[2]);
                });

        yearFeeDP.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    refreshFeesData(etatsDePaiement[3]);
                });

        monthyFeeDP.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    refreshFeesData(etatsDePaiement[2]);
                });

        idFee.setCellValueFactory(new PropertyValueFactory<Paiement, Integer>("idPaiement"));
        montantFee.setCellValueFactory(new PropertyValueFactory<Paiement, Double>("montant"));
        recuFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("numeroRecu"));
        rubrFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("rubrique"));
        eleveFee.setCellValueFactory(new PropertyValueFactory<Paiement, Apprenant>("nameApprenant"));

        dailyFeeDP.setValue(LocalDate.now());
        dailyFeesList = FXCollections.observableList(Toolbox.paiementsJournalier(LocalDate.now()));
        feesTable.setItems(dailyFeesList);
        double totalEncaisse = 0;
        for (Paiement paiement : dailyFeesList) {
            totalEncaisse += paiement.getMontant();
        };
        feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");
        if(dailyFeesList.size() == 0){
            panelEmpty.toFront();
        }


        for (Rubrique rubr : Toolbox.getRubriques()) {
            double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule())).toList().size();
            double effectifTotal = dailyFeesList.size();
            double pourcentage = (effectifPartiel / effectifTotal) * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
        }

        feesPie.setData(pieData);
        addPieTooltips();


    }

    public void addPieTooltips() {
        feesPie.getData().forEach(data -> {
            List<Paiement> concernedFees = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(data.getName())).toList();
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

        if(e.getSource() == dailyFeeDP){
            refreshFeesData(etatsDePaiement[0]);
        }else if(e.getSource() == weekFeeDP){
            refreshFeesData(etatsDePaiement[1]);
        }
    }

    public void refreshFeesData(String etat) {

        if(etat == etatsDePaiement[0]){
            panelFeesState.toFront();

            LocalDate newDate = dailyFeeDP.getValue();

            dailyFeesList = FXCollections.observableList(Toolbox.paiementsJournalier(newDate));

            if(dailyFeesList.size() == 0){
                panelEmpty.toFront();
            }
            feesTable.setItems(dailyFeesList);
            double totalEncaisse = 0;
            for(Paiement paiement : dailyFeesList) {
                totalEncaisse += paiement.getMontant();
            };
            feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");

            ObservableList<PieChart.Data> newPie = FXCollections.observableList(new ArrayList<PieChart.Data>());


            pieData.removeAll(pieData);


            for (Rubrique rubr : Toolbox.getRubriques()) {
                double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule())).toList().size();
                double effectifTotal = dailyFeesList.size();
                double pourcentage = (effectifPartiel / effectifTotal) * 100;
                pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
            }

            addPieTooltips();

        }else if(etat == etatsDePaiement[1]){
            panelFeesState.toFront();

            LocalDate newDate = weekFeeDP.getValue();

            dailyFeesList = FXCollections.observableList(Toolbox.paiementsHebdomadaire(newDate));
            if(dailyFeesList.size() == 0){
                panelEmpty.toFront();
            }
            feesTable.setItems(dailyFeesList);
            double totalEncaisse = 0;
            for(Paiement paiement : dailyFeesList) {
                totalEncaisse += paiement.getMontant();
            };
            feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");

            ObservableList<PieChart.Data> newPie = FXCollections.observableList(new ArrayList<PieChart.Data>());


            pieData.removeAll(pieData);


            for (Rubrique rubr : Toolbox.getRubriques()) {
                double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule().split(" ")[0])).toList().size();
                double effectifTotal = dailyFeesList.size();
                double pourcentage = (effectifPartiel / effectifTotal) * 100;
                pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
            }

            addPieTooltips();
        } else if(etat == etatsDePaiement[2]){
            panelFeesState.toFront();

            String newMonth = monthyFeeDP.getValue().toString();
            String newYear = yearFeeDP2.getValue().toString();
            dailyFeesList = FXCollections.observableList(Toolbox.paiementsMensuel(newMonth,newYear));
            if(dailyFeesList.size() == 0){
                panelEmpty.toFront();
            }
            feesTable.setItems(dailyFeesList);
            double totalEncaisse = 0;
            for(Paiement paiement : dailyFeesList) {
                totalEncaisse += paiement.getMontant();
            };
            feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");

            ObservableList<PieChart.Data> newPie = FXCollections.observableList(new ArrayList<PieChart.Data>());


            pieData.removeAll(pieData);


            for (Rubrique rubr : Toolbox.getRubriques()) {
                double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule().split(" ")[0])).toList().size();
                double effectifTotal = dailyFeesList.size();
                double pourcentage = (effectifPartiel / effectifTotal) * 100;
                pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
            }

            addPieTooltips();

        }else if(etat == etatsDePaiement[3]){
            panelAnn.toFront();

            yearBarChart.getData().clear();
            yearBarChart.layout();

            String newYear = yearFeeDP.getValue();

            dailyFeesList = FXCollections.observableList(Toolbox.paiementsAnnuel(newYear));
            if(dailyFeesList.size() == 0){
                panelEmpty.toFront();
            }

            for(Rubrique rubr: Toolbox.getRubriques()){
            XYChart.Series months = new XYChart.Series();
                String[] moisFR = mois;
                months.setName(rubr.getIntitule());


                Double yearlyDue = 0.0;
                for(int i=0;i<moisFR.length;i++){
                    Double monthlyDue = 0.0;
                    for(Paiement paiement : dailyFeesList){
                        if(paiement.getDate().getMonthValue() -1 == i && paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule().split(" ")[0])){
                            monthlyDue+= paiement.getMontant();
                            yearlyDue+= monthlyDue;
                        }
                    }
                    labelYearlyDue.setText("Bilan annuel : " + yearlyDue + " FCFA");
                    months.getData().add(new XYChart.Data(moisFR[i],monthlyDue));
                }
                yearBarChart.getData().addAll(months);
            }
        }
    }

    public void resetVue() {
        try {
//            resetSelectedClass(selectedClass);
            classPreview1Controller.setData(selectedClass);
            classPreview2Controller.setData(selectedClass);
            setListeDesApprenants(selectedClass.getApprenants());
            refreshFeesData(feesSpanSelect.getValue());
            setListeDesNotes(selectedModule.getNotes());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // getters et setters :

    public VBox getNotifsLayout() {
        return notifsLayout;
    }
    public ObservableList<Paiement> getDailyFeesList() {
        return this.dailyFeesList;
    }

    public void setDailyFeesList(ObservableList<Paiement> dailyFeesList) {
        this.dailyFeesList = dailyFeesList;

        for (Rubrique rubr : Toolbox.getRubriques()) {
            int nbPaiement = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equalsIgnoreCase(rubr.getIntitule())).toList().size() / dailyFeesList.size() * 100;
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

    public int getSelectedSemestreIndex(){
        int numSemestre;
        if (this.selectedSemestre == btnSemestre1) {
            numSemestre = 1;
        return numSemestre;
        } else if(this.selectedSemestre == btnSemestre2) {
            numSemestre = 2;
        return numSemestre;
        }

        return 0;

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


        if (currentUser instanceof Secretaire) {
            this.currentUser = (Secretaire) currentUser;
            setCaissierSession(false);
            setSecretaireView();

            labelTypeUser.setText("Profil secrétaire");

        } else {
            this.currentUser = (Caissier) currentUser;
            setCaissierSession(true);

            labelTypeUser.setText("Profil caissier");
        }

        userFullName.setText(currentUser.getFullName());
        userMail.setText(currentUser.getEmail());
        userPhoneNumber.setText(currentUser.getNumero());



//        On initialise la vue ici au lieu de l'initializer à cause d'un défaut de synchronisation
        try {

            // Classes récentes :
            setClasseRecentes();
            // Liste de toutes les classes :
            setListeDesClasses(listeClasses);
            // Liste des modules d'une classe :
            setListeDesModules();
            // Notifications :
            // Notifications :
            setListeDesNotifications(usersData.getNotifs(currentUser.getFullName()));


        } catch (Exception e) {
            e.printStackTrace();
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
            setShortcutApprenant(allApprenants);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }


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
    }

    public Module getSelectedModule() {
        return selectedModule;
    }


    void setCurrentRouteLink(String link) {
        setPreviousRouteLink(getCurrentRoute().getRouteLink());
        getCurrentRoute().setRouteLink(link);
        routeLink.setText(getCurrentRoute().getRouteLink());
        setSubView();
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
        } else if (e.getSource() == btnProfile || e.getSource() == pp_placeholder || e.getSource() == pp_placeholder1 || e.getSource() == notifBtn1 || e.getSource() == notifBtn2 || e.getSource() == notifBtn3) {
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
                aCSV.writeCSVFile(file.getPath(), selectedClass.getApprenants());
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
            if (event.getSource() == btnImportNotes) {
                NotesCSV nCSV = new NotesCSV();
                try {

                    List<String> fileContent = nCSV.readFile(file);
                    List<String[]> fileData = nCSV.getData(fileContent);
                    List<Note> importedNotes = nCSV.csvToObject(fileData, selectedModule, selectedClass,this.currentUser, getSelectedSemestreIndex());
                    List<Note> list = new ArrayList<>(selectedModule.getNotes());

                    for(Note oldNote : selectedModule.getNotes()){
                        for(Note newNote : importedNotes){
                            if(oldNote.getId() == newNote.getId()){
                                list.set(list.indexOf(oldNote),newNote);
                            }else{

                            }
                        }
                        selectedModule.setNotes(list);
                    }

                    this.setMainMessageInfo("Note(s) importé(s) avec succès !");
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
                    importedApprenants = aCSV.csvToObject(fileData, selectedClass,this.currentUser);

                    List<Apprenant> list = new ArrayList<>(selectedClass.getApprenants());

                    list.addAll(importedApprenants);

                    selectedClass.setApprenants(list);

                    this.setMainMessageInfo("Apprenant(s) importé(s) avec succès !");
                } catch (CSVException | Mismatch e) {
                    setMainMessageInfo(e.getMessage(), 0);
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    //    Méthodes de gestion du routage :

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
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
        if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule() + "/eleves")) {
            classListName.setText("Élèves en " + getSelectedClass().getIntitule());
            classStudentsView.toFront();
            btnPrecedentIsActive(true);
        } else if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule() + "/notes")) {
            classNotesView.toFront();
            setSelectedSemestre(btnSemestre1);
            btnPrecedentIsActive(true);
        } else if (getCurrentRoute().getRouteLink().equals("/" + getSelectedClass().getIntitule())) {
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

            setListeDesApprenants(newList);

    }

    @FXML
    public void handleStudentSearch(KeyEvent e) {
        if(e.getSource() == searchStudentInput){

            String toSearch = "";
            toSearch += searchStudentInput.getText().toLowerCase();

            List<Apprenant> found = new ArrayList<>();
            try {

                for (Apprenant appr : selectedClass.getApprenants()) {
                    if (appr.getFullName().toLowerCase().contains(toSearch) || appr.getNom().toLowerCase().contains(toSearch) || appr.getPrenom().toLowerCase().contains(toSearch) || Integer.toString(appr.getMatricule()).toLowerCase().contains(toSearch)) {
                        found.add(appr);
                    }
                }
                setListeDesApprenants(found);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }else{
            String toSearch = "";
            toSearch += searchStudentHomeInput.getText().toLowerCase();

            List<Apprenant> found = new ArrayList<>();
            try {

                for (Apprenant appr : allApprenants) {
                    if (appr.getFullName().toLowerCase().contains(toSearch) || appr.getNom().toLowerCase().contains(toSearch) || appr.getPrenom().toLowerCase().contains(toSearch) || Integer.toString(appr.getMatricule()).toLowerCase().contains(toSearch)) {
                        found.add(appr);
                    }
                }
                setShortcutApprenant(found);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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
                setListeDesNotes(module.getNotes());
            }



            modulesLayout.getChildren().add(pane);

        }




    }

    public void setListeDesNotifications(List<Notification> notifs){
        notifsLayout.getChildren().clear();

        if(notifs.stream().filter(notification -> notification.isSeen() == false).toList().size() == 0){
            notifsLayout.getChildren().add(notifsLayoutPlaceholder);
        }

        for (Notification notif : notifs) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("NotificationItem.fxml"));

            Pane pane = null;
            try {
                pane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            NotificationItemController nic = fxmlLoader.getController();
            nic.setSuperController(this);

            if(notif.isSeen() == false){
                nic.setData(notif);
                notifsLayout.getChildren().add(pane);
                toggleNotificationCircle(true);
            }

        }

    }

    public void toggleNotificationCircle(boolean bool){
        this.notifCircle1.setVisible(bool);
        this.notifCircle2.setVisible(bool);
        this.notifCircle3.setVisible(bool);
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

    public void setListeDesApprenants()  {
        setListeDesApprenants(selectedClass.getApprenants());
    }

    public void setListeDesApprenants(List<Apprenant> apprenants) {

//        Je met en commentaire ce code car j'ai un bug sur le dernier apprenant qui saute pas
//        jene sais plus pourquoi je l'avais mis donc oklm
//        if(apprenants.size() != 0){
            studentsLayout.getChildren().removeIf(node -> node instanceof HBox);
//        }

        if(apprenants.size() == 0){
            studentsLayout.getChildren().add(studentsLayoutPlaceholder);
        }



        for (Apprenant apprenant : apprenants) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ApprenantItem.fxml"));

            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ApprenantItemsController aic = fxmlLoader.getController();
            aic.setSuperController(this);
                System.out.println(currentUser);
            if(currentUser instanceof Caissier){
                System.out.println(
                "Voilà, cet individu est un caissier"
                );
                aic.setCaissierView();
            }
            aic.setData(apprenant);


            studentsLayout.getChildren().add(hbox);

        }
    }

    public void setShortcutApprenant(List<Apprenant> apprenants) {


        studentsHomeLayout.getChildren().removeIf(node -> node instanceof HBox);

        if(apprenants.size() == 0){
            studentsHomeLayout.getChildren().add(studentsHomeLayoutPlaceholder);
        }

        if(apprenants.size() != 0){


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ApprenantItem.fxml"));

            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ApprenantItemsController aic = fxmlLoader.getController();
            aic.setSuperController(this);
            aic.setData(apprenants.get(0));
            studentsHomeLayout.getChildren().add(hbox);
        }

    }


    public void setListeDesNotes() throws DAOException, IOException {
        setListeDesNotes(selectedModule.getNotes());
    }

    public void setListeDesNotes(List<Note> notes) throws DAOException, IOException {

        notesLayout.getChildren().clear();

        if(notes.size() == 0){
            notesLayout.getChildren().add(notesLayoutPlaceholder);
        }

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

    public boolean openBulletinViewDialog(Apprenant appr) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BulletinDialogView.fxml"));

                String[] bulletinPath;

            try{
                bulletinPath = Toolbox.getBulletinImgPaths(appr);
            }catch(RuntimeException e){
                setMainMessageInfo(e.getMessage(),0);
                return false;
            }


            Pane page = (Pane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP - Bulletin apprenant");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            BulletinDialogViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSuperController(this);
            controller.setScene(scene);
            controller.setMain(mainApp);
            controller.setApprenant(appr,bulletinPath);
            controller.setDraggable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
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

    public void setSecretaireView() {
        userMenu.getChildren().remove(userMenu.lookup(".caissier_item"));
        classStudentsView.getChildren().remove(classStudentsView.lookup(".caissier_item"));
        classStudentsView.getChildren().remove(classStudentsView.lookup(".caissier_item"));
        caissierEtatDePaiementLabel.setText("Date de naissance");
        classPreview1Controller.hideEcheancier();
        classPreview2Controller.hideEcheancier();
    }

    @FXML
    public void handleBulletinGeneration(ActionEvent e){

        if(getSelectedClass().getApprenants().stream().filter(apprenant -> apprenant.getEtatPaiement() == 0).toList().size() != 0){
            //ask for confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Génération bulletin");
            alert.setHeaderText("Il existe un ou plusieurs élèves non inscrits dans la classe, ils ne seront pas pris en compte.");
            alert.setContentText("Êtes vous sur de vouloir continuer ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                    try {
                        Classe classeToPass = getSelectedClass();
                        List<Module> modulesToPass = new ArrayList<>();
                        modulesToPass.addAll(getSelectedClass().getModules());

                        PDFGenerator.bulletinGenerator(classeToPass,modulesToPass,getSelectedSemestreIndex());
                        setMainMessageInfo("Bulletins générés avec succès (VOIR ELEVES)",1);
                    } catch (PDFException ex) {
                        setMainMessageInfo(ex.getMessage(),0);
                        System.out.println(ex.getMessage());
                    }

            }
        }else{
            Classe classeToPass = getSelectedClass();
            List<Module> modulesToPass = new ArrayList<>();
            modulesToPass.addAll(getSelectedClass().getModules());


            try {
                PDFGenerator.bulletinGenerator(classeToPass,modulesToPass,getSelectedSemestreIndex());
            } catch (PDFException ex) {
                setMainMessageInfo(ex.getMessage(),0);
                System.out.println(ex.getMessage());
            }
            setMainMessageInfo("Bulletins générés avec succès (VOIR ELEVES)",1);
        }
    }

    @FXML
    void editPasswordBtnClicked(ActionEvent event) {
        this.openPasswordEditDialog(currentUser);
    }

    @FXML
    public void openPasswordEditDialog(Utilisateur user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PasswordEdit.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("School UP Admin - Modifier mon mot de passe");
            // Set the application icon.

            dialogStage.getIcons().add(new Image("com/gesschoolapp/resources/images/app_icon.png"));
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);


            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PasswordEditController controller = loader.getController();
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
}
