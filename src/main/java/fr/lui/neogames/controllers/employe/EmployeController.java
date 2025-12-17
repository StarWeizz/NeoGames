package fr.lui.neogames.controllers.employe;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.gerant.BeneficesController;
import fr.lui.neogames.controllers.employe.preparateur.AjouterJeuController;
import fr.lui.neogames.controllers.employe.preparateur.StockController;
import fr.lui.neogames.controllers.employe.vendeur.ClientsController;
import fr.lui.neogames.controllers.employe.vendeur.VenteController;
import fr.lui.neogames.models.employe.Employe;
import fr.lui.neogames.models.employe.Gerant;
import fr.lui.neogames.models.employe.Preparateur;
import fr.lui.neogames.models.employe.Vendeur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployeController {

    @FXML
    private Label lblStoreName;
    @FXML
    private Label lblEmployeeName;
    @FXML
    private Label lblEmployeRole;
    @FXML
    private ComboBox<String> comboRole;
    @FXML
    private StackPane centerPane;

    // Menus
    @FXML
    private VBox menuVendeur;
    @FXML
    private VBox menuPreparateur;
    @FXML
    private VBox menuGerant;

    private Employe employe;
    private String roleActuel;

    // Liste d'employés de test
    private ObservableList<String> roles = FXCollections.observableArrayList(
            "Vendeur", "Préparateur", "Gérant"
    );

    @FXML
    public void initialize() {
        lblStoreName.setText(MainApp.getMagasin().getNom());

        // Configurer le combo de rôles
        comboRole.setItems(roles);

        // Écouter les changements de rôle
        comboRole.getSelectionModel().selectedItemProperty().addListener((obs, oldRole, newRole) -> {
            if (newRole != null) {
                changerRole(newRole);
            }
        });

        // Sélectionner le premier rôle par défaut
        if (!roles.isEmpty()) {
            comboRole.getSelectionModel().selectFirst();
        }
    }

    private void changerRole(String role) {
        this.roleActuel = role;

        // Cacher tous les menus
        menuVendeur.setVisible(false);
        menuVendeur.setManaged(false);
        menuPreparateur.setVisible(false);
        menuPreparateur.setManaged(false);
        menuGerant.setVisible(false);
        menuGerant.setManaged(false);

        // Afficher le menu correspondant au rôle
        switch (role) {
            case "Vendeur":
                menuVendeur.setVisible(true);
                menuVendeur.setManaged(true);
                employe = new Vendeur("Jean", "Dupont");
                lblEmployeRole.setText("Rôle: Vendeur");
                break;
            case "Préparateur":
                menuPreparateur.setVisible(true);
                menuPreparateur.setManaged(true);
                employe = new Preparateur("Marie", "Martin");
                lblEmployeRole.setText("Rôle: Préparateur");
                break;
            case "Gérant":
                menuGerant.setVisible(true);
                menuGerant.setManaged(true);
                employe = new Gerant("Pierre", "Durand");
                lblEmployeRole.setText("Rôle: Gérant");
                break;
        }

        if (employe != null) {
            lblEmployeeName.setText("Employé: " + employe.getFirstName() + " " + employe.getLastName());
        }
    }

    // ===== Actions Vendeur =====
    @FXML
    private void showVente() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/vendeur/vente-view.fxml");
    }

    @FXML
    private void showClients() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/vendeur/clients-view.fxml");
    }



    // ===== Actions Préparateur =====
    @FXML
    private void showStock() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/preparateur/stock-view.fxml");
    }

    @FXML
    private void showAjouterJeu() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/preparateur/ajouter-jeu-view.fxml");
    }

    // ===== Actions Gérant =====
    @FXML
    private void showBenefices() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/gerant/benefices-view.fxml");
    }

    @FXML
    private void showStatistiques() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/employe/gerant/statistiques-view.fxml");
    }

    @FXML
    private void showViewRole() {
        chargerVue("assets/fxml/hello-view.fxml");
    }

    // Méthode pour charger une vue dans le centerPane
    private void chargerVueDansCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent vue = loader.load();

            // Passer des données aux contrôleurs si nécessaire
            Object controller = loader.getController();
            if (controller != null) {
                if (controller instanceof VenteController) {
                    ((VenteController) controller).setEmployeController(this);
                } else if (controller instanceof StockController) {
                    ((StockController) controller).setEmployeController(this);
                } else if (controller instanceof AjouterJeuController) {
                    ((AjouterJeuController) controller).setEmployeController(this);
                } else if (controller instanceof BeneficesController) {
                    ((BeneficesController) controller).setEmployeController(this);
                } else if (controller instanceof ClientsController) {
                    ((ClientsController) controller).setEmployeController(this);
                }
            }

            centerPane.getChildren().setAll(vue);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement de la vue: " + fxmlPath);
            alert.show();
        }
    }

    private void chargerVue(String fxmlPath) {
        try {
            MainApp.changerVue(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
