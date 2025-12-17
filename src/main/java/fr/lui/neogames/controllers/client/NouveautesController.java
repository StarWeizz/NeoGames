package fr.lui.neogames.controllers.client;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class NouveautesController {

    @FXML
    private TableView<Jeu> tableNouveautes;
    @FXML
    private TableColumn<Jeu, String> colNom;
    @FXML
    private TableColumn<Jeu, String> colEditeur;
    @FXML
    private TableColumn<Jeu, String> colPlateforme;
    @FXML
    private TableColumn<Jeu, String> colGenre;
    @FXML
    private TableColumn<Jeu, String> colPrix;
    @FXML
    private TableColumn<Jeu, String> colPromotion;

    private ClientController clientController;

    @FXML
    public void initialize() {
        // Configurer les colonnes
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colEditeur.setCellValueFactory(cellData -> cellData.getValue().editeurProperty());

        colPlateforme.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getPlateforme().toString()));

        colGenre.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getGenre().toString()));

        colPrix.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getPrice())));

        colPromotion.setCellValueFactory(cellData -> {
            boolean p = cellData.getValue().estEnPromotion();
            return new SimpleStringProperty(p ? "Oui" : "Non");
        });

        // Afficher tous les jeux du catalogue comme nouveautés
        List<Jeu> nouveautes = MainApp.getMagasin().getCatalogue();

        tableNouveautes.setItems(FXCollections.observableArrayList(nouveautes));

        // Gérer la sélection d'un jeu
        tableNouveautes.getSelectionModel().selectedItemProperty().addListener((obs, oldJeu, newJeu) -> {
            if (newJeu != null && clientController != null) {
                clientController.afficherDetailsJeu(newJeu);
            }
        });
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
