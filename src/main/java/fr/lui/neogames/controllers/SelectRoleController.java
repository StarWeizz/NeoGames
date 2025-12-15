package fr.lui.neogames.controllers;

import fr.lui.neogames.MainApp;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SelectRoleController {
    public void onRoleClientButtonClick(ActionEvent actionEvent) {
        chargerVue("assets/fxml/client/main-view.fxml");
    }

    public void onRoleEmployeButtonClick(ActionEvent actionEvent) {
        chargerVue("assets/fxml/employe/main-view.fxml");
    }

    private void chargerVue(String fxmlPath) {
        try {
            MainApp.changerVue(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
