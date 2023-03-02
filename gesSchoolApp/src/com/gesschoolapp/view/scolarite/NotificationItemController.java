package com.gesschoolapp.view.scolarite;

import com.gesschoolapp.db.DAOClassesImpl.ActionDAOImp;
import com.gesschoolapp.models.actions.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificationItemController {

    private ScolariteUIController superController;


    @FXML
    private Label labelDateHeure;

    private Notification thisNotif;

    @FXML
    private HBox notifPane;

    @FXML
    private Label labelMessage;

    @FXML
    void onClose(MouseEvent event) {
        thisNotif.setSeen();
//        ActionDAOImp actDAO = new ActionDAOImp();
//        actDAO.
        superController.getNotifsLayout().getChildren().remove(notifPane);

        if(superController.getNotifsLayout().getChildren().size() == 0){
            superController.toggleNotificationCircle(false);
            List<Notification> emptyList = new ArrayList<>();
            superController.setListeDesNotifications(emptyList);
        }
    }

    public void setData(Notification notif){
        labelMessage.setText(notif.getMessage());
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d MMM uuuu");
        labelDateHeure.setText(notif.getDate().toLocalDate().format(formatters)+ " à " +notif.getDate().getHour()+"h:"+notif.getDate().getMinute());
        thisNotif = notif;
    }

    public ScolariteUIController getSuperController() {
        return superController;
    }

    public void setSuperController(ScolariteUIController superController) {
        this.superController = superController;
    }

}
