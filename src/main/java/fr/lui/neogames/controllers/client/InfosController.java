package fr.lui.neogames.controllers.client;

import fr.lui.neogames.models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfosController {

    @FXML
    private Label lblNom;
    @FXML
    private Label lblPrenom;
    @FXML
    private Label lblSolde;
    @FXML
    private Label lblCarteFidelite;
    @FXML
    private Label lblPointsFidelite;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
        afficherInfos();
    }

    private void afficherInfos() {
        if (client != null) {
            lblNom.setText("Nom: " + client.getNom());
            lblPrenom.setText("Prénom: " + client.getPrenom());
            lblSolde.setText(String.format("Solde: %.2f €", client.getSolde()));
            lblCarteFidelite.setText("Carte de fidélité: " + (client.isCarteFidelite() ? "Oui" : "Non"));
            lblPointsFidelite.setText(String.format("Points de fidélité: %.1f", client.getPointsFidelite()));
        }
    }

    public void ajouterArgent(ActionEvent actionEvent) {
        client.setSolde(client.getSolde() + 50);

        afficherInfos();
    }
}
