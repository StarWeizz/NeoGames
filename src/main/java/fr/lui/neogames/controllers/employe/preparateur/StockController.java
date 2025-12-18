package fr.lui.neogames.controllers.employe.preparateur;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StockController {

    @FXML
    private TableView<Jeu> tableStock;
    @FXML
    private TableColumn<Jeu, String> colNom;
    @FXML
    private TableColumn<Jeu, String> colEditeur;
    @FXML
    private TableColumn<Jeu, String> colPlateforme;
    @FXML
    private TableColumn<Jeu, String> colPrix;
    @FXML
    private TableColumn<Jeu, String> colStock;
    @FXML
    private TableColumn<Jeu, String> colOccasion;
    @FXML
    private Spinner<Integer> spinnerQuantite;

    private EmployeController employeController;

    @FXML
    public void initialize() {
        // Configurer les colonnes
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colEditeur.setCellValueFactory(cellData -> cellData.getValue().editeurProperty());

        colPlateforme.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlateforme().toString()));

        colPrix.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getPrice())));

        colStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        colOccasion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isOccasion() ? "Oui" : "Non"));

        rafraichir();
    }

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }

    @FXML
    private void rafraichir() {
        tableStock.setItems(FXCollections.observableArrayList(MainApp.getMagasin().getCatalogue()));
    }

    @FXML
    private void ajouterStock() {
        Jeu jeuSelectionne = tableStock.getSelectionModel().getSelectedItem();
        if (jeuSelectionne != null) {
            int quantite = spinnerQuantite.getValue();
            jeuSelectionne.setStock(jeuSelectionne.getStock() + quantite);

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format("Stock de '%s' augmenté de %d. Nouveau stock: %d",
                            jeuSelectionne.getNom(), quantite, jeuSelectionne.getStock()));
            alert.show();

            rafraichir();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un jeu");
            alert.show();
        }
    }

    @FXML
    private void retirerStock() {
        Jeu jeuSelectionne = tableStock.getSelectionModel().getSelectedItem();
        if (jeuSelectionne != null) {
            int quantite = spinnerQuantite.getValue();
            int nouveauStock = Math.max(0, jeuSelectionne.getStock() - quantite);
            jeuSelectionne.setStock(nouveauStock);

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format("Stock de '%s' réduit de %d. Nouveau stock: %d",
                            jeuSelectionne.getNom(), quantite, jeuSelectionne.getStock()));
            alert.show();

            rafraichir();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un jeu");
            alert.show();
        }
    }
}
