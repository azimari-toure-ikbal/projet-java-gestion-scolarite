package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.models.users.Utilisateur;
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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserItemController {

    private Utilisateur user;

    private AdminUIController superController;

    @FXML
    private Button btnDeleteUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private HBox panelActions;


    @FXML
    private Button btnViewChartUser;

    @FXML
    private Button btnViewUser;

    @FXML
    private HBox cardHbox;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelNum;

    @FXML
    private Circle pp_placeholder;

    @FXML
    void actionBtnClicked(ActionEvent event) {
        if(event.getSource() == btnViewChartUser){
            superController.openUserChartDialog(user);
        }

        if(event.getSource() == btnEditUser){
            superController.openUserEditDialog(user);
        }

        if(event.getSource() == btnDeleteUser){
            //ask for confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Vous êtes sur le point de supprimer l'utilisateur " + user.getFullName());
            alert.setContentText("Voulez-vous continuer ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                UserDAOImp uData = new UserDAOImp();

                try {
                    uData.delete(user.getId(), superController.getCurrentUser().getFullName());
                    List<Utilisateur> list = new ArrayList<>(superController.getUsersList());
                    list.removeIf(usr -> usr.getId() == user.getId());
                    superController.setUsersList(list);
                    superController.setMainMessageInfo("Utilisateur supprimé avec succès !");
                    superController.setListeDesUtilisateurs();
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setData(Utilisateur utilisateur){
        labelNom.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
        labelLogin.setText(utilisateur.getEmail());
        labelNum.setText(utilisateur.getNumero());
        this.user = utilisateur;
        // --- Init components :
        Image pp = new Image("com/gesschoolapp/resources/images/pp_placeholder.jpg");
        pp_placeholder.setFill(new ImagePattern(pp));

        if(!utilisateur.getType().equals("caissier")){
            panelActions.getChildren().remove(panelActions.lookup(".caissier_item"));
        }
    }

    public AdminUIController getSuperController() {
        return superController;
    }

    public void setSuperController(AdminUIController superController) {
        this.superController = superController;
    }

    @FXML
    void handleOpenUserView(ActionEvent event) {
        superController.openUserViewDialog(user);
    }

}
