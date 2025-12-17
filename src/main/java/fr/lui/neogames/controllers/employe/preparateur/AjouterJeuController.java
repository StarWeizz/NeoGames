package fr.lui.neogames.controllers.employe.preparateur;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;
import fr.lui.neogames.models.jeu.JeuConsole;
import fr.lui.neogames.models.jeu.JeuPC;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AjouterJeuController {

    @FXML
    private ComboBox<String> comboTypeJeu;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtEditeur;
    @FXML
    private TextField txtPrix;
    @FXML
    private ComboBox<String> comboGenre;
    @FXML
    private Spinner<Integer> spinnerStock;
    @FXML
    private CheckBox checkOccasion;

    // Champs PC
    @FXML
    private VBox champsPc;
    @FXML
    private TextField txtConfigMin;
    @FXML
    private TextField txtConfigRecommandee;
    @FXML
    private TextField txtPlateforme;

    // Champs Console
    @FXML
    private VBox champsConsole;
    @FXML
    private ComboBox<String> comboPlateforme;
    @FXML
    private CheckBox checkMultijoueur;
    @FXML
    private CheckBox checkLigneObligatoire;
    @FXML
    private Spinner<Integer> spinnerJoueursMax;

    private EmployeController employeController;

    @FXML
    public void initialize() {
        // Écouter les changements de type de jeu
        comboTypeJeu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                afficherChampsSpecifiques(newVal);
            }
        });
    }

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }

    private void afficherChampsSpecifiques(String type) {
        // Cacher tous les champs
        champsPc.setVisible(false);
        champsPc.setManaged(false);
        champsConsole.setVisible(false);
        champsConsole.setManaged(false);

        // Afficher les champs appropriés
        switch (type) {
            case "PC":
                champsPc.setVisible(true);
                champsPc.setManaged(true);
                break;
            case "Console":
                champsConsole.setVisible(true);
                champsConsole.setManaged(true);
                break;
        }
    }

    @FXML
    private void ajouterJeu() {
        try {
            // Validation des champs obligatoires
            if (comboTypeJeu.getValue() == null || txtNom.getText().isEmpty() ||
                    txtEditeur.getText().isEmpty() || txtPrix.getText().isEmpty() ||
                    comboGenre.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs obligatoires (*)");
                alert.show();
                return;
            }

            String type = comboTypeJeu.getValue();
            String nom = txtNom.getText();
            String editeur = txtEditeur.getText();
            double prix = Double.parseDouble(txtPrix.getText());
            Genre genre = Genre.valueOf(comboGenre.getValue());
            int stock = spinnerStock.getValue();
            boolean occasion = checkOccasion.isSelected();

            switch (type) {
                case "PC":
                    ajouterJeuPC(nom, editeur, prix, genre, stock, occasion);
                    break;
                case "Console":
                    ajouterJeuConsole(nom, editeur, prix, genre, stock, occasion);
                    break;
                case "Rétro":
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ajout de jeux rétro à implémenter");
                    alert.show();
                    break;
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le prix doit être un nombre valide");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du jeu: " + e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    private void ajouterJeuPC(String nom, String editeur, double prix, Genre genre, int stock, boolean occasion) {
        String configMin = txtConfigMin.getText().isEmpty() ? "Non spécifiée" : txtConfigMin.getText();
        String configRec = txtConfigRecommandee.getText().isEmpty() ? "Non spécifiée" : txtConfigRecommandee.getText();
        String plateforme = txtPlateforme.getText().isEmpty() ? "PC" : txtPlateforme.getText();

        JeuPC jeu = new JeuPC(nom, editeur, prix, genre, stock, occasion, configMin, configRec, plateforme);
        MainApp.getMagasin().ajouterJeu(jeu);

        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                String.format("Jeu PC '%s' ajouté avec succès au catalogue!", nom));
        alert.show();

        reinitialiser();
    }

    private void ajouterJeuConsole(String nom, String editeur, double prix, Genre genre, int stock, boolean occasion) {
        if (comboPlateforme.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une plateforme console");
            alert.show();
            return;
        }

        Plateforme plateforme = Plateforme.valueOf(comboPlateforme.getValue());
        boolean multijoueur = checkMultijoueur.isSelected();
        boolean ligneOblig = checkLigneObligatoire.isSelected();
        int joueursMax = spinnerJoueursMax.getValue();

        JeuConsole jeu = new JeuConsole(nom, editeur, prix, genre, plateforme, stock, occasion,
                multijoueur, true, ligneOblig, joueursMax);
        MainApp.getMagasin().ajouterJeu(jeu);

        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                String.format("Jeu Console '%s' ajouté avec succès au catalogue!", nom));
        alert.show();

        reinitialiser();
    }

    @FXML
    private void reinitialiser() {
        comboTypeJeu.getSelectionModel().clearSelection();
        txtNom.clear();
        txtEditeur.clear();
        txtPrix.clear();
        comboGenre.getSelectionModel().clearSelection();
        spinnerStock.getValueFactory().setValue(10);
        checkOccasion.setSelected(false);

        txtConfigMin.clear();
        txtConfigRecommandee.clear();
        txtPlateforme.clear();

        comboPlateforme.getSelectionModel().clearSelection();
        checkMultijoueur.setSelected(false);
        checkLigneObligatoire.setSelected(false);
        spinnerJoueursMax.getValueFactory().setValue(1);

        champsPc.setVisible(false);
        champsPc.setManaged(false);
        champsConsole.setVisible(false);
        champsConsole.setManaged(false);
    }
}
