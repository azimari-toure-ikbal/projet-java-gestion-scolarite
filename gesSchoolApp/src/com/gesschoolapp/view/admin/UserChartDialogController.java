package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UserChartDialogController implements Initializable {


    private Utilisateur user;


    private Stage dialogStage;


    private Main main;

    // Reference to the current scene
    private Scene scene;


    private AdminUIController superController;


    @FXML
    private Label feeTotal;

    @FXML
    private PieChart feesPie;

    @FXML
    private TableView<Paiement> feesTable;


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

    private PaiementDAOImp paiementsData = new PaiementDAOImp();
    private List<Paiement> dailyFeesList;

    private ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Utilisateur utilisateur){
        user = utilisateur;

        List<Paiement> dailyFeesList = Toolbox.paiementsJournalier(LocalDate.now());
        setDailyFeesList(FXCollections.observableArrayList(dailyFeesList.stream().filter(paiement -> paiement.getCaissier().equalsIgnoreCase(utilisateur.getFullName())).toList()));

    }

    public void setDailyFeesList(ObservableList<Paiement> dailyFeesList) {
        this.dailyFeesList = dailyFeesList;

        idFee.setCellValueFactory(new PropertyValueFactory<Paiement, Integer>("idPaiement"));
        montantFee.setCellValueFactory(new PropertyValueFactory<Paiement, Double>("montant"));
        recuFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("numeroRecu"));
        rubrFee.setCellValueFactory(new PropertyValueFactory<Paiement, String>("rubrique"));
        eleveFee.setCellValueFactory(new PropertyValueFactory<Paiement, Apprenant>("nameApprenant"));

        feesTable.setItems(dailyFeesList);
        double totalEncaisse = 0;
        for (Paiement paiement : dailyFeesList) {
            totalEncaisse += paiement.getMontant();
        };
        feeTotal.setText("Total encaissé : " + totalEncaisse + "FCFA");

        for (Rubrique rubr : Toolbox.getRubriques()) {
            double effectifPartiel = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equals(rubr.getIntitule())).toList().size();
            double effectifTotal = dailyFeesList.size();
            double pourcentage = (effectifPartiel / effectifTotal) * 100;
            pieData.add(new PieChart.Data(rubr.getIntitule(), pourcentage));
        }

        feesPie.setData(pieData);
        addPieTooltips();

    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

    @FXML
    void onClose(ActionEvent event) {
        try {
            Timeline timeline = new Timeline();
            KeyFrame key;
            key = new KeyFrame(Duration.millis(50),
                    new KeyValue(dialogStage.opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((ae) -> dialogStage.close());
            timeline.play();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
    }

    public void addPieTooltips() {
        feesPie.getData().forEach(data -> {
            List<Paiement> concernedFees = dailyFeesList.stream().filter(paiement -> paiement.getRubrique().split(" ")[0].equals(data.getName())).toList();
            Double totalEncaisse = 0.0;
            for (Paiement p : concernedFees) {
                totalEncaisse += p.getMontant();
            }
            String more = (int) Math.ceil((data.getPieValue() / 100) * dailyFeesList.size()) + " paiement(s) encaissé(s), soit " + totalEncaisse + "FCFA";

            Tooltip ttp = new Tooltip(more);
            Tooltip.install(data.getNode(), ttp);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
