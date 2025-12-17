package fr.lui.neogames.controllers.employe.vendeur;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.Commande;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClientsController {

    @FXML
    private TableView<Client> tableClient;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, String> colSolde;
    @FXML
    private TableColumn<Client, String> colCarteFidelite;
    @FXML
    private TableColumn<Client, String> colPointsFidelite;
    @FXML
    private  TableColumn<Client, Integer> colNombreCommandes;
    @FXML
    private  TableColumn<Client, String> colPanierMoyen;
    @FXML
    private  TableColumn<Client, String> colMontantDepense;

    private EmployeController employeController;

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(cellData -> {
            Client nom = cellData.getValue();
            return new SimpleStringProperty(nom.getNom());
        });
        colPrenom.setCellValueFactory(cellData -> {
            Client prenom = cellData.getValue();
            return new SimpleStringProperty(prenom.getPrenom());
        });

        colSolde.setCellValueFactory(cellData -> {
            Client solde = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(solde.getSolde()));
        });

        colCarteFidelite.setCellValueFactory(cellData -> {
            Client carteFidelite = cellData.getValue();
            boolean cf = carteFidelite.isCarteFidelite();
            return new SimpleStringProperty(cf ? "Oui" : "Non");
        });

        colPointsFidelite.setCellValueFactory(cellData -> {
            Client pointsFidelite = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(pointsFidelite.getPointsFidelite()));
        });

        colNombreCommandes.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return new SimpleIntegerProperty(client.getCommande().getNbCommandes()).asObject();
        });

        colPanierMoyen.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            double montantTotal = client.getCommande().getCommandes().stream()
                    .mapToDouble(Commande.DetailCommande::getMontant)
                    .sum();
            double panierMoyen = client.getCommande().getNbCommandes() > 0
                    ? montantTotal / client.getCommande().getNbCommandes()
                    : 0.0;
            return new SimpleStringProperty(String.format("%.2f", panierMoyen));
        });

        colMontantDepense.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            double montantDepense = client.getCommande().getCommandes().stream()
                    .mapToDouble(Commande.DetailCommande::getMontant)
                    .sum();
            return new SimpleStringProperty(String.format("%.2f", montantDepense));
        });

        tableClient.setItems(FXCollections.observableArrayList(MainApp.getMagasin().getClients()));
    }

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }
}
