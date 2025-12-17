package fr.lui.neogames.controllers.employe.vendeur;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class VenteController {

    @FXML
    private ComboBox<Client> comboClients;
    @FXML
    private Label lblClientSolde;
    @FXML
    private TableView<Jeu> tableCatalogue;
    @FXML
    private TableColumn<Jeu, String> colNomCatalogue;
    @FXML
    private TableColumn<Jeu, String> colPrixCatalogue;
    @FXML
    private TableColumn<Jeu, String> colStockCatalogue;
    @FXML
    private Spinner<Integer> spinnerQuantite;
    @FXML
    private TableView<Map.Entry<Jeu, Integer>> tablePanier;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colNomPanier;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colQuantitePanier;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colPrixUnitaire;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colTotalPanier;
    @FXML
    private Label lblTotal;

    private EmployeController employeController;
    private Client clientSelectionne;

    // Liste de clients de test
    private ObservableList<Client> clients = FXCollections.observableArrayList(
            new Client("John", "Doe"),
            new Client("Jane", "Smith"),
            new Client("Alice", "Martin", 3748.32)
    );

    @FXML
    public void initialize() {
        // Configurer le combo des clients
        comboClients.setItems(clients);
        comboClients.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });
        comboClients.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });

        comboClients.getSelectionModel().selectedItemProperty().addListener((obs, oldClient, newClient) -> {
            if (newClient != null) {
                clientSelectionne = newClient;
                lblClientSolde.setText(String.format("Solde: %.2f €", newClient.getSolde()));
                afficherPanier();
            } else {
                lblClientSolde.setText("Solde: 0.00 €");
            }
        });

        // Configurer la table du catalogue
        colNomCatalogue.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrixCatalogue.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getPrice())));
        colStockCatalogue.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        tableCatalogue.setItems(FXCollections.observableArrayList(MainApp.getMagasin().getCatalogue()));

        // Configurer la table du panier
        colNomPanier.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKey().getNom()));
        colQuantitePanier.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValue())));
        colPrixUnitaire.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getKey().getPrice())));
        colTotalPanier.setCellValueFactory(cellData -> {
            double prix = cellData.getValue().getKey().getPrice();
            int qte = cellData.getValue().getValue();
            return new SimpleStringProperty(String.format("%.2f €", prix * qte));
        });

        // Sélectionner le premier client par défaut
        if (!clients.isEmpty()) {
            comboClients.getSelectionModel().selectFirst();
        }
    }

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }

    @FXML
    private void ajouterAuPanier() {
        if (clientSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un client");
            alert.show();
            return;
        }

        Jeu jeuSelectionne = tableCatalogue.getSelectionModel().getSelectedItem();
        if (jeuSelectionne != null) {
            int quantite = spinnerQuantite.getValue();

            if (jeuSelectionne.getStock() >= quantite) {
                clientSelectionne.getPanier().ajouterJeu(jeuSelectionne, quantite);

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("'%s' x%d ajouté au panier", jeuSelectionne.getNom(), quantite));
                alert.show();

                afficherPanier();
                spinnerQuantite.getValueFactory().setValue(1);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Stock insuffisant. Stock disponible: %d", jeuSelectionne.getStock()));
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un jeu");
            alert.show();
        }
    }

    @FXML
    private void viderPanier() {
        if (clientSelectionne != null) {
            clientSelectionne.getPanier().nettoyerPanier();
            afficherPanier();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le panier a été vidé");
            alert.show();
        }
    }

    @FXML
    private void validerVente() {
        if (clientSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un client");
            alert.show();
            return;
        }

        if (clientSelectionne.getPanier().getArticles().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le panier est vide");
            alert.show();
            return;
        }

        double total = clientSelectionne.getPanier().getTotal();

        if (clientSelectionne.getSolde() >= total) {
            // Créer la commande
            clientSelectionne.getCommande().addCommande(
                    new java.util.ArrayList<>(clientSelectionne.getPanier().getArticles().keySet()));

            // Déduire le stock
            for (Map.Entry<Jeu, Integer> entry : clientSelectionne.getPanier().getArticles().entrySet()) {
                Jeu jeu = entry.getKey();
                int quantite = entry.getValue();
                jeu.setStock(jeu.getStock() - quantite);
            }

            // Déduire le solde et ajouter au chiffre d'affaires
            clientSelectionne.setSolde(clientSelectionne.getSolde() - total);
            MainApp.getMagasin().setChiffreAffaires(MainApp.getMagasin().getChiffreAffaires() + total);

            // Ajouter des points de fidélité si carte fidélité
            if (clientSelectionne.isCarteFidelite()) {
                double nouveauxPoints = total / 10;
                clientSelectionne.setPointsFidelite(clientSelectionne.getPointsFidelite() + nouveauxPoints);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    String.format("Vente réussie !\n\nMontant: %.2f €\nNouveau solde: %.2f €",
                            total, clientSelectionne.getSolde()));
            alert.show();

            // Nettoyer le panier
            clientSelectionne.getPanier().nettoyerPanier();
            afficherPanier();

            // Rafraîchir le catalogue pour montrer les nouveaux stocks
            tableCatalogue.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format("Solde insuffisant\n\nTotal: %.2f €\nSolde: %.2f €",
                            total, clientSelectionne.getSolde()));
            alert.show();
        }
    }

    private void afficherPanier() {
        if (clientSelectionne != null) {
            ObservableList<Map.Entry<Jeu, Integer>> items =
                    FXCollections.observableArrayList(clientSelectionne.getPanier().getArticles().entrySet());
            tablePanier.setItems(items);

            double total = clientSelectionne.getPanier().getTotal();
            lblTotal.setText(String.format("Total: %.2f €", total));
        }
    }
}
