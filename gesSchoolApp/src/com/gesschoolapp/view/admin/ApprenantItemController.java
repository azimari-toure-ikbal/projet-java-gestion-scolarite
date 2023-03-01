package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.PDFException;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.docmaker.PDFGenerator;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.utils.Toolbox;
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
    private Button btnVoirCertificat;

    @FXML
    private Button viewAllClasses1;

    @FXML
    void handleNavigation(MouseEvent event) {

    }

    @FXML
    void actionBtnClicked(ActionEvent event) {

        if(event.getSource() == btnCertificat){

            if(thisApprenant.getEtatPaiement() == 0){
                superController.setMainMessageInfo(thisApprenant.getFullName() + " n'est pas inscrit !",0);
            }else{
                try {
                    PDFGenerator.cerficatScolariteGenerator(thisApprenant);
                    superController.setMainMessageInfo("Certificat généré avec succès !");
                    btnVoirCertificat.toFront();
                } catch (PDFException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(event.getSource() == btnVoirCertificat){
            superController.openCertificatViewDialog(thisApprenant);
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
        try{
            Toolbox.getCertificatsImgPath(appr);
        }catch (RuntimeException e){
            btnCertificat.toFront();
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
