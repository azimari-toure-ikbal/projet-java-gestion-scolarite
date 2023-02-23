package com.gesschoolapp.view.admin;

import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class ClassGesModuleViewController {

    private Stage dialogStage;

    private Classe selectedClass;

    @FXML
    private ScrollPane scroll; //this must match the fx:id of the ScrollPane element


    public Classe getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Classe selectedClass) {
        this.selectedClass = selectedClass;
    }

    private Main main;

    // Reference to the current scene
    private Scene scene;

    AdminUIController superController;

    @FXML
    private VBox modulesLayout;

    @FXML
    private Label labelInfos;

    @FXML
    private Label messageInfo;

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
    public void setSuperController(AdminUIController superController) {this.superController = superController;}

    public void setDraggable() {

        scene.getRoot().setOnMousePressed(e ->{
            scene.getRoot().setOnMouseDragged(e1 ->{
                dialogStage.setX(e1.getScreenX() - e.getSceneX());
                dialogStage.setY(e1.getScreenY() - e.getSceneY());
            });
        });
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

    public void setData(Classe classe){
            labelInfos.setText(classe.getIntitule() + " - " + classe.getAnnee());
            setListeDesModules(classe.getModules());
            selectedClass = classe;

        modulesLayout.heightProperty().addListener(observable -> scroll.setVvalue(1D));

    }

    public void setMainMessage(String msg,int i){
        if(i==0){
            messageInfo.setVisible(true);
            messageInfo.setText(msg);
            messageInfo.setTextFill(Color.web("#E74C3C"));
            Toolbox.setTimeout(() -> messageInfo.setVisible(false),5000);
        }else if(i==1){
            messageInfo.setVisible(true);
            messageInfo.setText(msg);
            messageInfo.setTextFill(Color.web("#007E34"));
            Toolbox.setTimeout(() -> messageInfo.setVisible(false),5000);
        }
    }

    public void setListeDesModules(List<Module> modules) {

        if (modules.size() != 0) {
            modulesLayout.getChildren().clear();
        }

//        if (getSelectedUserType() == btnCaissiers) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("caissier")).toList();
//        } else if (getSelectedUserType() == btnSecretaires) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("secretaire")).toList();
//        } else if (getSelectedUserType() == btnAdmins) {
//            users = users.stream().filter(utilisateur -> utilisateur.getType().equals("administrateur")).toList();
//        }

        for (Module module : modules) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModuleItem.fxml"));
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ModuleItemController mic = fxmlLoader.getController();
            mic.setSuperController(this);
            mic.setData(module);

            modulesLayout.getChildren().add(hbox);

        }
    }

    @FXML
    void addRow(ActionEvent event) {
        if(modulesLayout.getChildren().size() >= 40){
            setMainMessage("Nombre maximal de modules atteint (40)",0);
        }else{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ModuleItem.fxml"));
        HBox hbox = null;
        try {
            hbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModuleItemController mic = fxmlLoader.getController();
        mic.setSuperController(this);
        mic.setData(null);
        mic.setIntituleFocus();

        modulesLayout.getChildren().add(hbox);

        Platform.runLater(() -> scrollDown());
        }

    }



    public void removeDeletedFromVue(HBox pane){
        modulesLayout.getChildren().remove(pane);
    }

    public void scrollDown(){
        scroll.setVvalue(1.0);           //1.0 means 100% at the bottom
    }

}
