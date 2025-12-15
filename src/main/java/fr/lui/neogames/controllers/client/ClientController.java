package fr.lui.neogames.controllers.client;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.jeu.Jeu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ClientController {
    private Client client;

    @FXML
    public Label lblClientBalance;

    @FXML
    private Spinner<Integer> spinnerQuantite;

    @FXML
    private ComboBox<Client> comboClients;

    @FXML
    private Label lblStoreName;

    @FXML
    private StackPane centerPane;

    @FXML
    private VBox rightPanel;
    @FXML
    private Label lblDetailNom, lblDetailEditeur, lblDetailPrix, lblDetailPlateforme, lblDetailGenre;
    @FXML
    private Button btnAjouterPanier;

    private Jeu jeuSelectionne;



    private ObservableList<Client> clients = FXCollections.observableArrayList(
            new Client("John", "Doe"),
            new Client("Jane", "Smith"),
            new Client("Alice", "Martin", 3748.32)
    );

    @FXML
    public void initialize(){
        showArticles();


        lblStoreName.setText(MainApp.getMagasin().getNom());

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
                lblClientBalance.setText(String.format("Solde: %.2f €", newClient.getSolde()));
            } else {
                lblClientBalance.setText("Solde: 0.00 €");
            }
        });

        comboClients.getSelectionModel().selectedItemProperty().addListener((obs, oldClient, newClient) -> {
            if (newClient != null) {
                this.client = newClient;
            }
        });

        // Sélectionner le premier client par défaut
        if (!clients.isEmpty()) {
            comboClients.getSelectionModel().selectFirst();
        }
    }

    // Boutons menu
    @FXML
    private void showCommandes() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/commandes-view.fxml");
    }

    @FXML
    private void showSolde() {
        centerPane.getChildren().setAll(new Label("Votre solde: 150 €"));
    }


    @FXML
    private void showInfos() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/infos-view.fxml");
    }


    @FXML
    private void showPanier() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/panier-view.fxml");
    }

    @FXML
    private void onLogout() {
        System.out.println("Déconnexion...");
        // Ici tu peux gérer la fermeture de session ou revenir à l'écran de login
    }

    public void showFidelity(ActionEvent actionEvent) {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/fidelite-view.fxml");
    }

    public void showPromotions(ActionEvent actionEvent) {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/promotions-view.fxml");
    }

    public void showNouveautes(ActionEvent actionEvent) {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/nouveautes-view.fxml");
    }

    public void showArticles(ActionEvent actionEvent) {
       showArticles();
    }

    @FXML
    private void showArticles() {
        chargerVueDansCenter("/fr/lui/neogames/assets/fxml/client/articles-view.fxml");
    }

    public void afficherDetailsJeu(Jeu jeu) {
        jeuSelectionne = jeu;
        lblDetailNom.setText("Nom: " + jeu.getNom());
        lblDetailEditeur.setText("Éditeur: " + jeu.getEditeur());
        lblDetailPrix.setText("Prix: " + jeu.getPrice() + " €");
        lblDetailPlateforme.setText("Plateforme: " + jeu.getPlateforme());
        lblDetailGenre.setText("Genre: " + jeu.getGenre());
        btnAjouterPanier.setDisable(false);
    }

    public void showViewRole(ActionEvent actionEvent) {
        chargerVue("assets/fxml/hello-view.fxml");
    }

    @FXML
    private void ajouterAuPanier(ActionEvent actionEvent) {
        if (jeuSelectionne != null) {
            if (client != null) {
                int quantite = spinnerQuantite.getValue();
                client.getPanier().ajouterJeu(jeuSelectionne, quantite);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Jeu " + jeuSelectionne.getNom() + " x" + quantite + " ajouté au panier");
                alert.show();

                spinnerQuantite.getValueFactory().setValue(1);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un client");
                alert.show();
            }
        }
    }

    private void chargerVue(String fxmlPath) {
        try {
            MainApp.changerVue(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode générique pour charger une vue FXML dans le centerPane
    private void chargerVueDansCenter(String fxmlPath) {
        // Vérifier qu'un client est sélectionné
        Client clientSelectionne = comboClients.getSelectionModel().getSelectedItem();
        if (clientSelectionne == null) {
            centerPane.getChildren().setAll(new Label("Veuillez sélectionner un client"));
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent vue = loader.load();

            // Passer le client au contrôleur approprié
            Object controller = loader.getController();
            if (controller != null) {
                if (controller instanceof InfosController) {
                    ((InfosController) controller).setClient(clientSelectionne);
                } else if (controller instanceof PanierController) {
                    ((PanierController) controller).setClient(clientSelectionne);
                } else if (controller instanceof FideliteController) {
                    ((FideliteController) controller).setClient(clientSelectionne);
                } else if (controller instanceof CommandesController) {
                    ((CommandesController) controller).setClient(clientSelectionne);
                } else if (controller instanceof ArticlesController) {
                    ((ArticlesController) controller).setClientController(this);
                }
            }

            centerPane.getChildren().setAll(vue);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement de la vue: " + fxmlPath);
            alert.show();
        }
    }
}
