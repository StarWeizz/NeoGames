package fr.lui.neogames.controllers.client;

import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.Commande;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;

public class CommandesController {
    @FXML
    private TableView<Commande.DetailCommande> tableCommandes;
    @FXML
    private TableColumn<Commande.DetailCommande, String> colNumero;
    @FXML
    private TableColumn<Commande.DetailCommande, String> colDate;
    @FXML
    private TableColumn<Commande.DetailCommande, String> colMontant;
    @FXML
    private TableColumn<Commande.DetailCommande, String> colStatut;

    private Client client;

    @FXML
    public void initialize() {
        // Configurer les colonnes du tableau
        colNumero.setCellValueFactory(cellData -> {
            int index = tableCommandes.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty("#" + index);
        });

        colDate.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return new SimpleStringProperty(cellData.getValue().getDate().format(formatter));
        });

        colMontant.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getMontant())));

        colStatut.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatut()));
    }

    public void setClient(Client client) {
        this.client = client;
        afficherCommandes();
    }

    private void afficherCommandes() {
        if (client != null) {
            tableCommandes.setItems(FXCollections.observableArrayList(client.getCommande().getCommandes()));
        }
    }
}
