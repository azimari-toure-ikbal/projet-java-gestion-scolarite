package com.gesschoolapp.view.admin;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.UserDAOImp;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.users.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleItemController {

    ClassGesModuleViewController superController;


    @FXML
    private Button btnDelete;

    private Module selectedModule;

    @FXML
    private HBox modulePane;

    @FXML
    private TextField intituleTF;

    @FXML
    private Label labelIntitule;

    @FXML
    private Label labelSemestre;

    @FXML
    private ChoiceBox<Integer> semestreSelect;

    @FXML
    private Label messageInfo;

    private Integer[] semestres = {1, 2};


    ModuleDAOImp mDao = new ModuleDAOImp();


    @FXML
    void actionBtnClicked(ActionEvent event) {
        if (event.getSource() == btnDelete) {

            if(modulePane.getParent().getChildrenUnmodifiable().size() == 1){
                superController.setMainMessage("Une classe doit avoir au moins un module !", 0);

            }else{

            if(selectedModule == null){
            }else{

            //ask for confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Vous êtes sur le point de supprimer le module " + selectedModule.getIntitule() + " de la classe " + selectedModule.getClasse());
            alert.setContentText("Voulez-vous continuer ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ModuleDAOImp mDao = new ModuleDAOImp();

                try {
                    mDao.delete(selectedModule.getId(), superController.superController.getCurrentUser().getFullName());
                    List<Module> list = new ArrayList<>(superController.getSelectedClass().getModules());
                    list.removeIf(module -> module.getId() == selectedModule.getId());
                    superController.getSelectedClass().setModules(list);
                    superController.removeDeletedFromVue(modulePane);
                    superController.setMainMessage("Module supprimé avec succès !", 1);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
            }
            }
        }
    }


    public ClassGesModuleViewController getSuperController() {
        return superController;
    }

    public void setSuperController(ClassGesModuleViewController superController) {
        this.superController = superController;
    }

    public void setData(Module mod) {
        semestreSelect.getItems().addAll(semestres);
        intituleTF.requestFocus();
        if (mod == null) {
            superController.scrollDown();
            intituleTF.setVisible(true);
            semestreSelect.setVisible(true);
            selectedModule = null;
        } else {
            labelIntitule.setText(mod.getIntitule());
            intituleTF.setText(mod.getIntitule());
            labelSemestre.setText(Integer.toString(mod.getSemestre()));
            semestreSelect.setValue(mod.getSemestre());
            selectedModule = mod;
        }
    }

    @FXML
    void openEdit(MouseEvent event) {
        if (event.getSource() == labelIntitule) {
            intituleTF.setVisible(true);
            intituleTF.requestFocus();
        } else {
            semestreSelect.setVisible(true);
            semestreSelect.requestFocus();
        }
    }

    public void setIntituleFocus(){
        intituleTF.requestFocus();
    }


    @FXML
    boolean closeEdit(KeyEvent event) {
        System.out.println(event.getSource());
        String intitule = intituleTF.getText();

        System.out.println(intitule);

        if (intitule.isEmpty() || intitule.length() >= 255) {
            superController.setMainMessage("L'intitulé ne peut pas être nul !", 0);
            return false;
        }

        if (semestreSelect.getValue() == null) {
            superController.setMainMessage("Veuillez choisir un semestre svp !", 0);
            return false;
        }

//        System.out.println(selectedModule);

        if (event.getSource() == intituleTF && event.getCode() == KeyCode.ENTER) {

            // Teste aussi les special chars !

            if (selectedModule == null) {

                Module newModule = new Module();
                newModule.setSemestre(semestreSelect.getValue());
                newModule.setIntitule(intituleTF.getText());
                newModule.setClasse(superController.getSelectedClass().getIntitule());
                try {
                    superController.setMainMessage("Patientez...", 1);
                    Module toAddModule = mDao.create(newModule, superController.superController.getCurrentUser().getFullName());
                    List<Module> mds = superController.getSelectedClass().getModules();
                    System.out.println("avant add : " + mds);
                    mds.add(toAddModule);
                    getSuperController().getSelectedClass().setModules(mds);
                    System.out.println("apres add : " + getSuperController().getSelectedClass().getModules());
                    selectedModule = toAddModule;
                    setData(toAddModule);
                    System.out.println(" créee");
                    intituleTF.setVisible(false);
                    semestreSelect.setVisible(false);
                    labelIntitule.setText(intituleTF.getText());
                    labelSemestre.setText(semestreSelect.getValue() + "");
                    superController.setMainMessage("Module crée avec succès", 1);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }

            } else {

                selectedModule.setIntitule(intitule);
                try {
                    superController.setMainMessage("Patientez...", 1);
                    mDao.update(selectedModule, superController.superController.getCurrentUser().getFullName());
                    labelIntitule.setText(intitule);
                    intituleTF.setVisible(false);
                    superController.setMainMessage("Intitulé du module modifié avec succès", 1);

                    return true;
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (event.getSource() == semestreSelect && event.getCode() == KeyCode.ENTER) {

            if (selectedModule == null) {

                Module newModule = new Module();
                newModule.setSemestre(semestreSelect.getValue());
                newModule.setSemestre(semestreSelect.getValue());
                newModule.setIntitule(intituleTF.getText());
                newModule.setClasse(superController.getSelectedClass().getIntitule());
                try {
                    superController.setMainMessage("Patientez...", 1);
                    Module toAddModule = mDao.create(newModule, superController.superController.getCurrentUser().getFullName());
                    System.out.println(toAddModule);
                    selectedModule = toAddModule;
                    setData(toAddModule);
                    System.out.println(" créee");
                    List<Module> mds = superController.getSelectedClass().getModules();
                    System.out.println("avant add : " + mds);
                    mds.add(toAddModule);
                    getSuperController().getSelectedClass().setModules(mds);
                    System.out.println("apres add : " + getSuperController().getSelectedClass().getModules());
                    selectedModule = toAddModule;
                    setData(toAddModule);
                    intituleTF.setVisible(false);
                    semestreSelect.setVisible(false);
                    labelIntitule.setText(intituleTF.getText());
                    labelSemestre.setText(semestreSelect.getValue() + "");
                    superController.setMainMessage("Module crée avec succès", 1);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }

            } else {

                selectedModule.setSemestre(semestreSelect.getValue());
                try {
                    superController.setMainMessage("Patientez...", 1);
                    mDao.update(selectedModule, superController.superController.getCurrentUser().getFullName());
                    labelSemestre.setText(semestreSelect.getValue() + "");
                    superController.setMainMessage("Semestre du module modifié avec succès", 1);
                    intituleTF.setVisible(false);
                    semestreSelect.setVisible(false);
                    return true;
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
        return false;
    }
}
