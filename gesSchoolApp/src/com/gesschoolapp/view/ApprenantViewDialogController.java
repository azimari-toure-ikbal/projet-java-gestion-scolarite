package com.gesschoolapp.view;

import com.gesschoolapp.models.student.Apprenant;
import javafx.stage.Stage;

public class ApprenantViewDialogController {
    private Stage dialogStage;
    private Apprenant apprenant;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setApprenant(Apprenant appr) {
        this.apprenant = appr;
    }
}
