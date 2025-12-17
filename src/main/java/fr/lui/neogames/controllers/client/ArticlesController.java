package fr.lui.neogames.controllers.client;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ArticlesController {

    @FXML
    private TableView<Jeu> tableArticles;
    @FXML
    private TableColumn<Jeu, String> colNom;
    @FXML
    private TableColumn<Jeu, String> colEditeur;
    @FXML
    private TableColumn<Jeu, Double> colPrix;
    @FXML
    private TableColumn<Jeu, String> colPlateforme;
    @FXML
    private TableColumn<Jeu, String> colGenre;
    @FXML
    private  TableColumn<Jeu, String> colPromotion;

    private ClientController clientController;

    @FXML
    public void initialize() {
        // Configurer les colonnes
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colEditeur.setCellValueFactory(cellData -> cellData.getValue().editeurProperty());
        colPrix.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        colPlateforme.setCellValueFactory(cellData -> {
            Plateforme p = cellData.getValue().getPlateforme();
            return new SimpleStringProperty(p != null ? p.toString() : "");
        });

        colGenre.setCellValueFactory(cellData -> {
            Genre g = cellData.getValue().getGenre();
            return new SimpleStringProperty(g != null ? g.toString() : "");
        });

        colPromotion.setCellValueFactory(cellData -> {
            boolean p = cellData.getValue().estEnPromotion();
            return new SimpleStringProperty(p ? "Oui" : "Non");
        });

        // Charger les jeux du catalogue
        tableArticles.setItems(FXCollections.observableArrayList(MainApp.getMagasin().getCatalogue()));

        // Gérer la sélection d'un jeu
        tableArticles.getSelectionModel().selectedItemProperty().addListener((obs, oldJeu, newJeu) -> {
            if (newJeu != null && clientController != null) {
                clientController.afficherDetailsJeu(newJeu);
            }
        });
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
