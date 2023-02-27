package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.Utilisateur;
import de.jensd.fx.glyphs.testapps.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApprenantItemController {

    private Apprenant thisApprenant;

    private AdminUIController superController;

    @FXML
    private Button etatPayement;

    @FXML
    private Label labelGenre;

    @FXML
    private Label labelMatricule;

    @FXML
    private Label labelNom;

    @FXML
    private Button btnCertificat;

    @FXML
    private Button viewAllClasses1;

    @FXML
    void handleNavigation(MouseEvent event) {

    }

    @FXML
    void openCertificatDialog(ActionEvent event) {

    }


    @FXML
    void actionBtnClicked(ActionEvent event) {

        if(event.getSource() == btnCertificat){

            if(thisApprenant.getEtatPaiement() == 0){
                //ask for confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Génération du certificat de scolarité");
                alert.setHeaderText("Vous êtes sous le point de générer le certificat de scolarité d'un élève non inscrit : " + thisApprenant.getFullName());
                alert.setContentText("Voulez-vous continuer ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    System.out.println("ouvre la vue du certificat");
                }
            }else{
                    System.out.println("ouvre la vue du certificat");
            }
        }
    }

    public void setData(Apprenant appr){
        labelMatricule.setText(appr.getMatricule()+"");
        labelNom.setText(appr.getPrenom() + " " + appr.getNom());
        labelGenre.setText(appr.getSexe());
        if(appr.getEtatPaiement() == 0){
            etatPayement.setText("Non inscrit");
            etatPayement.setStyle("-fx-background-color: #F0B606;");
        }

        thisApprenant = appr;
    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

}
