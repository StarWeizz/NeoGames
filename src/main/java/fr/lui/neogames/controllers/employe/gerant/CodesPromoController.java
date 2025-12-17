package fr.lui.neogames.controllers.employe.gerant;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.models.CodePromo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CodesPromoController {

    @FXML
    private TableView<CodePromo> tableCodesPromo;
    @FXML
    private TableColumn<CodePromo, String> colCode;
    @FXML
    private TableColumn<CodePromo, String> colReduction;
    @FXML
    private TableColumn<CodePromo, String> colDateExpiration;
    @FXML
    private TableColumn<CodePromo, String> colActif;
    @FXML
    private TableColumn<CodePromo, String> colMaxUtilisation;
    @FXML
    private TableColumn<CodePromo, String> colUtilisationsRestantes;
    @FXML
    private EmployeController employeController;

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }

    public void initialize() {
        colCode.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            return new SimpleStringProperty(codePromo.getCode());
        });

        colReduction.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            return new SimpleStringProperty(String.format("-%.2f %%", codePromo.getReduction()));
        });

        colDateExpiration.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            LocalDate date = codePromo.getDateExpiration();
            String texte;
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                texte = String.format("Date : %s", date.format(formatter));
            } else {
                texte = "Aucune";
            }

            return new SimpleStringProperty(texte);
        });

        colActif.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            boolean isActif = codePromo.isActif();
            return new SimpleStringProperty(isActif ? "Oui" : "Non");
        });

        colMaxUtilisation.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            int maxUse = codePromo.getUtilisationsMax();
            String text;
            if (maxUse < 0) {
                text = "Infini";
            } else {
                text = String.valueOf(maxUse);
            }
            return new SimpleStringProperty(text);
        });

        colUtilisationsRestantes.setCellValueFactory(cellData -> {
            CodePromo codePromo = cellData.getValue();
            int remUse = codePromo.getUtilisationsRestantes();
            String text;
            if (remUse < 0) {
                text = "Infini";
            } else {
                text = String.valueOf(remUse);
            }
            return new SimpleStringProperty(text);
        });

        tableCodesPromo.setItems(FXCollections.observableArrayList(MainApp.getMagasin().getCodesPromo()));

    }
}
