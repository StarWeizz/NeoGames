package fr.lui.neogames.controllers.client;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionsController {

    @FXML
    private TableView<Jeu> tablePromotions;
    @FXML
    private TableColumn<Jeu, String> colNom;
    @FXML
    private TableColumn<Jeu, String> colEditeur;
    @FXML
    private TableColumn<Jeu, String> colPrixOriginal;
    @FXML
    private TableColumn<Jeu, String> colReduction;
    @FXML
    private TableColumn<Jeu, String> colPrixPromo;

    @FXML
    public void initialize() {
        // Configurer les colonnes
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colEditeur.setCellValueFactory(cellData -> cellData.getValue().editeurProperty());

        colPrixOriginal.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getPrice())));

        colReduction.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.format("-%.0f%%", cellData.getValue().getReduction())));

        colPrixPromo.setCellValueFactory(cellData -> {
            Jeu jeu = cellData.getValue();
            double prixPromo = jeu.getPrixAvecPromo(jeu.getPrice());
            return new SimpleStringProperty(String.format("%.2f €", prixPromo));
        });

        // Filtrer et afficher uniquement les jeux en promotion
        List<Jeu> jeuxEnPromo = MainApp.getMagasin().getCatalogue().stream()
                .filter(jeu -> jeu.estEnPromotion())
                .collect(Collectors.toList());

        tablePromotions.setItems(FXCollections.observableArrayList(jeuxEnPromo));
    }
}
