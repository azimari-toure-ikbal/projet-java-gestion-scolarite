package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ApprenantItemsController implements Initializable {

    private ScolariteUIController superController;

    private Apprenant thisApprenant;

    @FXML
    private Label labelGenre;

    @FXML
    private Label labelId;

    @FXML
    private Label labelNom;

    @FXML
    private ToolBar notSubscribedWarning;

    @FXML
    private Label labeldNaiss;

    @FXML
    private Button etatPayement;

    @FXML
    private Button btnDeleteApprenant;

    @FXML
    private Button btnEditApprenant;

    @FXML
    private Button btnViewApprenant;

    @FXML
    void actionBtnClicked(ActionEvent event) {
        if(event.getSource() == btnViewApprenant){
            superController.openStudentViewDialog(thisApprenant);
        }
    }

    @FXML
    void editBtnClicked(ActionEvent event) {
        superController.openStudentEditDialog(thisApprenant);
    }

    @FXML
    void deleteBtnClicked(ActionEvent event) {
        //ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer l'élève " + thisApprenant.getPrenom() + " " + thisApprenant.getNom() + " de matricule " + thisApprenant.getMatricule());
        alert.setContentText("Voulez-vous continuer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ApprenantDAOImp apprData = new ApprenantDAOImp();

            try {
                apprData.delete(thisApprenant.getIdApprenant(), superController.getCurrentUser().getFullName());
                List<Apprenant> list = new ArrayList<>(superController.getSelectedClass().getApprenants());
                System.out.println(superController.getSelectedClass().getApprenants().get(0).getIdApprenant());
                System.out.println(thisApprenant.getIdApprenant());
                list.removeIf(appr -> appr.getIdApprenant() == thisApprenant.getIdApprenant());
                System.out.println(list);
                superController.getSelectedClass().setApprenants(list);
                if(thisApprenant.getEtatPaiement() != 0){
                    for(Module module : superController.getSelectedClass().getModules()){

                        List<Note> notesList = new ArrayList<>(module.getNotes());
                        notesList.removeIf(note -> note.getApprenant().getIdApprenant() == thisApprenant.getIdApprenant());
                        module.setNotes(notesList);
                    }
                }

                superController.setMainMessageInfo("Élève supprimé avec succès !");
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public void setData(Apprenant apprenant){
        thisApprenant = apprenant;
        labelId.setText(Integer.toString(apprenant.getMatricule()));
        labelNom.setText(apprenant.getPrenom() + " " + apprenant.getNom());
//      Parsing birthday :
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        String dNaiss = apprenant.getDateNaissance().format(formatters);

            if(apprenant.getEtatPaiement() == 0){
                notSubscribedWarning.setVisible(true);
                etatPayement.setText("Non inscrit");
                etatPayement.setStyle("-fx-background-color: #F0B606;");
            }else if(apprenant.getEtatPaiement() == 1){
                    notSubscribedWarning.setVisible(false);
                if(!getSuperController().getSelectedClass().isCurrentEcheancePaid(apprenant)){
                    etatPayement.setText("Impayé");
                    etatPayement.setStyle("-fx-background-color: #E9243B;");
                }else{
                    etatPayement.setText("Inscrit");
                    etatPayement.setStyle("-fx-background-color: #57AD57;");
                }
            }else{
                notSubscribedWarning.setVisible(false);
                if(!getSuperController().getSelectedClass().isCurrentEcheancePaid(apprenant)){
                    etatPayement.setText("Impayé");
                    etatPayement.setStyle("-fx-background-color: #E9243B;");
                }else{
                    etatPayement.setText("Payé");
                    etatPayement.setStyle("-fx-background-color: #57AD57;");
                }
            }


        labeldNaiss.setText(dNaiss);
        labelGenre.setText(apprenant.getSexe());

        if(superController.isCaissierSession()){
            etatPayement.setVisible(true);
        }
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;

    }

    public void setCaissierView(){
            etatPayement.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
