package fr.lui.neogames.controllers.client;

import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;

public class PanierController {

    private ClientController clientController;

    @FXML
    private TableView<Map.Entry<Jeu, Integer>> tablePanier;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colNom;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colPrixUnitaire;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colQuantite;
    @FXML
    private TableColumn<Map.Entry<Jeu, Integer>, String> colTotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblReductionFidelite;
    @FXML
    private TextField txtCodePromo;
    @FXML
    private Button btnAppliquerPromo;
    @FXML
    private Button btnPayer;

    private Client client;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @FXML
    public void initialize() {
        // Configurer les colonnes du tableau
        colNom.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKey().getNom()));

        colPrixUnitaire.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKey().getPrice() + " €"));

        colQuantite.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValue())));

        colTotal.setCellValueFactory(cellData -> {
            double prix = cellData.getValue().getKey().getPrice();
            int qte = cellData.getValue().getValue();
            return new SimpleStringProperty(String.format("%.2f €", prix * qte));
        });
    }

    public void setClient(Client client) {
        this.client = client;
        afficherPanier();
    }

    private void afficherPanier() {
        if (client != null) {
            ObservableList<Map.Entry<Jeu, Integer>> items =
                    FXCollections.observableArrayList(client.getPanier().getArticles().entrySet());
            tablePanier.setItems(items);

            // Calculer les différents totaux
            double sousTotal = client.getPanier().getTotalSansPromo();
            double totalAvecPromo = client.getPanier().getTotal();
            double reductionFidelite = client.isCarteFidelite() ? Math.min(client.getPointsFidelite(), totalAvecPromo) : 0;
            double totalFinal = totalAvecPromo - reductionFidelite;

            // Construire le texte d'affichage
            StringBuilder affichage = new StringBuilder();

            // Sous-total (barré si promo appliquée)
            if (client.getPanier().getCodePromoApplique() != null) {
                affichage.append("Sous-total : ");
                affichage.append(barrer(String.format("%.2f €", sousTotal)));
                affichage.append(" → ").append(String.format("%.2f €", totalAvecPromo));
                affichage.append("\n");
            }

            // Réduction fidélité si applicable
            if (reductionFidelite > 0) {
                affichage.append(String.format("Réduction avec points de fidélité : -%.2f €", reductionFidelite));
                affichage.append("\n");
            }

            // Total final
            affichage.append(String.format("Total : %.2f €", totalFinal));

            lblTotal.setText(affichage.toString());

            // Info sur les points disponibles
            if (client.isCarteFidelite() && client.getPointsFidelite() > 0) {
                lblReductionFidelite.setText(String.format("💰 Vous avez %.1f points de fidélité (%.2f € disponibles)",
                    client.getPointsFidelite(), client.getPointsFidelite()));
            } else {
                lblReductionFidelite.setText("");
            }
        }
    }

    private String barrer(String texte) {
        // Utiliser le caractère Unicode pour barrer le texte
        StringBuilder resultat = new StringBuilder();
        for (char c : texte.toCharArray()) {
            resultat.append(c).append('\u0336'); // Caractère de barrage
        }
        return resultat.toString();
    }

    @FXML
    private void appliquerPromo() {
        String code = txtCodePromo.getText();
        if (code != null && !code.trim().isEmpty()) {
            // Chercher le code promo dans le magasin
            fr.lui.neogames.models.CodePromo codePromo =
                fr.lui.neogames.MainApp.getMagasin().trouverCodePromo(code);

            if (codePromo != null && codePromo.estValide()) {
                // Appliquer le code promo au panier
                client.getPanier().appliquerCodePromo(codePromo);
                codePromo.utiliser();

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Code promo " + code + " appliqué avec succès !\nRéduction: " + codePromo.getReduction() + "%");
                alert.show();

                // Rafraîchir l'affichage pour montrer le nouveau total
                afficherPanier();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Code promo invalide ou expiré");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un code promo");
            alert.show();
        }
    }

    @FXML
    private void payer() {
        if (client != null) {
            double totalAvecPromo = client.getPanier().getTotal();
            double reductionFidelite = client.isCarteFidelite() ? Math.min(client.getPointsFidelite(), totalAvecPromo) : 0;
            double totalFinal = totalAvecPromo - reductionFidelite;

            if (client.getSolde() >= totalFinal) {
                // Créer une liste des jeux du panier
                List<Jeu> jeuxCommandes = new java.util.ArrayList<>(client.getPanier().getArticles().keySet());

                // Déduire le montant du solde
                client.setSolde(client.getSolde() - totalFinal);

                if (clientController != null) {
                    clientController.lblClientBalance.setText(String.format("Solde : %.2f €", client.getSolde()));
                }

                client.getCommande().addCommande(jeuxCommandes);

                // Déduire les points de fidélité utilisés
                if (reductionFidelite > 0) {
                    client.setPointsFidelite(client.getPointsFidelite() - reductionFidelite);
                }

                // Ajouter des points de fidélité pour cet achat (10€ = 1 point)
                if (client.isCarteFidelite()) {
                    double nouveauxPoints = totalFinal / 10;
                    client.setPointsFidelite(client.getPointsFidelite() + nouveauxPoints);

                    String message = String.format(
                        "Paiement réussi !\n\n" +
                        "Montant payé : %.2f €\n" +
                        "Points utilisés : -%.2f €\n" +
                        "Points gagnés : +%.1f\n\n" +
                        "Nouveau solde : %.2f €\n" +
                        "Points de fidélité : %.1f",
                        totalFinal, reductionFidelite, nouveauxPoints,
                        client.getSolde(), client.getPointsFidelite()
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("Vous venez de payer %.2f €. Nouveau solde : %.2f €",
                            totalFinal, client.getSolde()));
                    alert.show();
                }

                client.getPanier().nettoyerPanier();
                afficherPanier();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Vous n'avez pas assez d'argent");
                alert.show();
                // Rafraîchir l'affichage du panier
                afficherPanier();
            }
        }
    }

    @FXML
    private void viderPanier() {
        if (client != null) {
            client.getPanier().nettoyerPanier();
            afficherPanier();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le panier a été vidé");
            alert.show();
        }
    }
}
