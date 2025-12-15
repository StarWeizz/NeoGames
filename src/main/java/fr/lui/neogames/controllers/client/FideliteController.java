package fr.lui.neogames.controllers.client;

import fr.lui.neogames.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FideliteController {

    @FXML
    private Label lblPoints;
    @FXML
    private Label lblStatutCarte;
    @FXML
    private Button btnCommanderCarte;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
        afficherFidelite();
    }

    private void afficherFidelite() {
        if (client != null) {
            lblPoints.setText(String.format("Vos points actuels : %.1f", client.getPointsFidelite()));
            lblStatutCarte.setText("Carte de fidélité : " + (client.isCarteFidelite() ? "Activée ✓" : "Non activée"));

            // Désactiver le bouton si le client a déjà une carte
            if (client.isCarteFidelite()) {
                btnCommanderCarte.setDisable(true);
                btnCommanderCarte.setText("Carte déjà activée");
            }
        }
    }

    @FXML
    private void commanderCarte() {
        if (client != null) {
            if (client.isCarteFidelite()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez déjà une carte de fidélité");
                alert.show();
            } else {
                client.setCarteFidelite(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Carte de fidélité commandée avec succès !");
                alert.show();
                afficherFidelite(); // Rafraîchir l'affichage
            }
        }
    }
}
